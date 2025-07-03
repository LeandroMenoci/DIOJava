package br.com.dio.persistence.dao;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;

import static br.com.dio.persistence.converter.OffsetDateTimeConverter.toTimestamp;

@AllArgsConstructor
public class BlockDAO {
    private final Connection connection;

    public void block(final Long cardId, final String reason) throws SQLException {
        // Comando SQL para inserir um novo registro de bloqueio
        var sql = "INSERT INTO BLOCKS (blocked_at, block_reason, card_id) VALUES (?, ?, ?)";

        try (var statement = connection.prepareStatement(sql)) {
            var i = 1;
            // Define o horário atual como o momento do bloqueio
            statement.setTimestamp(i++, toTimestamp(OffsetDateTime.now()));
            // Define o motivo do bloqueio
            statement.setString(i++, reason);
            // Define o ID do card a ser bloqueado
            statement.setLong(i, cardId);

            // Executa a inserção
            statement.executeUpdate();
        }
    }

    public void unblock(final Long cardId, final String reason) throws SQLException {
        var sql = "UPDATE BLOCKS SET unblocked_at = ?, unblock_reason = ? WHERE card_id = ? AND unblocked_at IS NULL";
        try (var statement = connection.prepareStatement(sql)) {
            var i = 1;
            // Define o horário atual como o momento do desbloqueio
            statement.setTimestamp(i++, toTimestamp(OffsetDateTime.now()));
            // Define o motivo do desbloqueio
            statement.setString(i++, reason);
            // Define o ID do card a ser desbloqueado
            statement.setLong(i, cardId);

            // Executa a atualização
            statement.executeUpdate();
        }
    }


}
