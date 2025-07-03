package br.com.dio.persistence.dao;

import br.com.dio.dto.CardDetailsDTO;
import br.com.dio.persistence.entity.CardEntity;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static br.com.dio.persistence.converter.OffsetDateTimeConverter.toOffsetDateTime;

@AllArgsConstructor
public class CardDAO {
    private final Connection connection;

    /**
     * Insere um novo Card no banco de dados e retorna a entidade com o ID preenchido.
     *
     * @param entity CardEntity contendo título, descrição e a coluna associada
     * @return A mesma entidade, agora com o ID gerado pelo banco
     * @throws SQLException Caso ocorra erro na execução da query
     */
    public CardEntity insert(final CardEntity entity) throws SQLException {
        // Comando SQL para inserção dos dados na tabela CARDS
        var sql = "INSERT INTO CARDS (title, description, board_column_id) VALUES (?, ?, ?)";

        // Prepara a declaração para executar a query com segurança (evita SQL injection)
        try (var statement = connection.prepareStatement(sql)) {
            var i = 1;

            // Define os parâmetros da query usando os dados da entidade
            statement.setString(i++, entity.getTitle());
            statement.setString(i++, entity.getDescription());
            statement.setLong(i, entity.getBoardColumn().getId());

            // Executa a inserção no banco
            statement.executeUpdate();

            // Se o statement for uma instância de StatementImpl (MySQL), obtém o ID gerado
            if (statement instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
        }

        // Retorna o objeto atualizado com o ID
        return entity;
    }

    public void moveToColumn(final Long columnId, final Long cardId) throws SQLException {
        // SQL para mover o card para outra coluna
        var sql = "UPDATE CARDS SET board_column_id = ? WHERE id = ?";

        try (var statement = connection.prepareStatement(sql)) {
            var i = 1;
            statement.setLong(i++, columnId); // Nova coluna
            statement.setLong(i, cardId);     // ID do card a ser movido
            statement.executeUpdate();        // Executa o update
        }
    }

    public Optional<CardDetailsDTO> findById(Long id) throws SQLException {
        // Consulta SQL que retorna detalhes de um card, incluindo bloqueio atual e dados da coluna
        var sql =
                """
                    SELECT c.id,
                           c.title,
                           c.description,
                           b.blocked_at,
                           b.block_reason,
                           c.board_column_id,
                           bc.name,
                           (SELECT COUNT(sub_b.id) FROM BLOCKS sub_b WHERE sub_b.card_id = c.id) blocks_amount
                    FROM CARDS c
                    LEFT JOIN BLOCKS b
                    ON c.id = b.card_id
                    AND b.unblocked_at IS NULL
                    INNER JOIN BOARDS_COLUMNS bc
                    ON bc.id = c.board_column_id
                    WHERE c.id = ?
                """;

        try(var statement = connection.prepareStatement(sql)){
            // Define o ID do card como parâmetro
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            // Se encontrou o card, preenche o DTO com os dados
            if(resultSet.next()) {
                var dto = new CardDetailsDTO(
                        resultSet.getLong("c.id"),
                        resultSet.getString("c.title"),
                        resultSet.getString("c.description"),
                        resultSet.getString("b.block_reason") != null,
                        toOffsetDateTime(resultSet.getTimestamp("b.blocked_at")),
                        resultSet.getString("b.block_reason"),
                        resultSet.getInt("blocks_amount"),
                        resultSet.getLong("c.board_column_id"),
                        resultSet.getString("bc.name")
                );
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
}
