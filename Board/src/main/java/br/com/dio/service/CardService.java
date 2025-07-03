package br.com.dio.service;

import br.com.dio.dto.BoardColumnInfoDTO;
import br.com.dio.exception.CardBlockedException;
import br.com.dio.exception.CardFinishedException;
import br.com.dio.exception.EntityNotFoundException;
import br.com.dio.persistence.dao.BlockDAO;
import br.com.dio.persistence.dao.CardDAO;
import br.com.dio.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static br.com.dio.persistence.entity.BoardColumnKindEnum.CANCEL;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.FINAL;

// Gera um construtor com todos os campos via Lombok
@AllArgsConstructor
public class CardService {

    // Conexão com o banco de dados usada nas operações
    private final Connection connection;

    /**
     * Insere um novo card no banco de dados e realiza o commit da transação.
     * Em caso de erro, executa rollback.
     *
     * @param entity Card a ser inserido
     * @return Card inserido, com ID preenchido
     * @throws SQLException se ocorrer falha durante a inserção
     */
    public CardEntity insert(CardEntity entity) throws SQLException {
        try {
            // Cria uma instância do DAO e insere o card
            var dao = new CardDAO(connection);
            dao.insert(entity);

            // Finaliza a transação com sucesso
            connection.commit();
            return entity;

        } catch (SQLException ex) {
            // Reverte a transação em caso de erro
            connection.rollback();
            throw ex; // Propaga o erro para quem chamou
        }
    }

    public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);

            // Busca os dados do card pelo ID
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(() ->
                    new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId))
            );

            // Verifica se o card está bloqueado
            if (dto.blocked()) {
                throw new CardBlockedException("O card está bloqueado, é necessário desbloquear para mover".formatted(cardId));
            }

            // Encontra a coluna atual do card com base na lista de colunas do board
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalStateException("O card informado pertence a outro board")
                    );

            // Verifica se a coluna atual é do tipo FINAL
            if (currentColumn.kind().equals(FINAL)) {
                throw new CardFinishedException("O card já foi finalizado");
            }

            // Encontra a próxima coluna com base na ordem da atual + 1
            var nextColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst()
                    .orElseThrow(() ->  new IllegalStateException("O card está cancelado")); // Garante que a próxima coluna exista

            // Move o card para a nova coluna
            dao.moveToColumn(nextColumn.id(), cardId);

            // Finaliza a transação com sucesso
            connection.commit();

        } catch (SQLException ex) {
            // Reverte alterações em caso de erro no banco
            connection.rollback();
            throw ex;
        }
    }

    public void cancel(final Long cardId, final Long cancelColumnId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);

            // Busca os dados do card pelo ID
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(() ->
                    new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId))
            );

            // Verifica se o card está bloqueado
            if (dto.blocked()) {
                throw new CardBlockedException("O card está bloqueado, é necessário desbloquear para cancelar");
            }

            // Verifica se a coluna atual pertence ao board
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalStateException("O card informado pertence a outro board")
                    );
            // Verifica se a coluna atual é do tipo FINAL
            if (currentColumn.kind().equals(FINAL)) {
                throw new CardFinishedException("O card já foi finalizado");
            }

            // Verifica se a coluna de cancelamento realmente pertence ao mesmo board
            var cancelColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(cancelColumnId))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException("Coluna de cancelamento inválida ou não pertence ao board")
                    );

            // Move o card para a coluna de cancelamento
            dao.moveToColumn(cancelColumnId, cardId);

            // Finaliza a transação com sucesso
            connection.commit();

        } catch (SQLException ex) {
            // Reverte alterações em caso de erro no banco
            connection.rollback();
            throw ex;
        }
    }

    public void block(final Long id, final String reason, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);

            // Busca os dados do card
            var optional = dao.findById(id);
            var dto = optional.orElseThrow(() ->
                    new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
            );

            // Verifica se já está bloqueado
            if (dto.blocked()) {
                throw new CardBlockedException("O card %s já está bloqueado".formatted(id));
            }

            // Encontra a coluna atual do card
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalStateException("A coluna atual do card não pertence ao board informado")
                    );

            // Verifica se a coluna atual é FINAL ou CANCEL
            if (currentColumn.kind().equals(FINAL) || currentColumn.kind().equals(CANCEL)) {
                throw new IllegalStateException("O card está em uma coluna do tipo %s e não pode ser bloqueado".formatted(currentColumn.kind()));
            }

            // Executa o bloqueio
            var blockDAO = new BlockDAO(connection);
            blockDAO.block(id, reason);

            // Commit na transação
            connection.commit();

        } catch (SQLException ex) {
            // Rollback em caso de falha
            connection.rollback();
            throw ex;
        }
    }

    public void unblock(final Long id, final String reason) throws SQLException {
        try {
            var dao = new CardDAO(connection);

            // Busca os dados do card
            var optional = dao.findById(id);
            var dto = optional.orElseThrow(() ->
                    new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
            );

            // Verifica se o card está atualmente bloqueado
            if (!dto.blocked()) {
                throw new IllegalStateException("O card %s não está bloqueado".formatted(id));
            }

            // Realiza o desbloqueio via BlockDAO
            var blockDAO = new BlockDAO(connection);
            blockDAO.unblock(id, reason); // metodo para atualizar 'unblocked_at'

            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

}
