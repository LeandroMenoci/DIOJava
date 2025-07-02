package br.com.dio.service;

import br.com.dio.persistence.dao.CardDAO;
import br.com.dio.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

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
}
