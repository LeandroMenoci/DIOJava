package br.com.dio.service;

import br.com.dio.exception.CardBlockedException;
import br.com.dio.exception.CardFinishedException;
import br.com.dio.exception.EntityNotFoundException;
import br.com.dio.persistence.dao.CardDAO;
import br.com.dio.dto.BoardColumnInfoDTO;
import br.com.dio.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
                throw new CardBlockedException("O card está bloqueado, é necessário desbloquear para mover");
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
                    .orElseThrow(); // Garante que a próxima coluna exista

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
}
