package br.com.dio.service;

import br.com.dio.persistence.dao.BoardColumnDAO;
import br.com.dio.persistence.dao.BoardDAO;
import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class BoardService {

    private final Connection connection;

    public BoardEntity insert(BoardEntity entity) throws SQLException {
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        try {
            // Insere o board e associa o board em cada coluna
            dao.insert(entity);

            // Associa o board na coluna
            for (BoardColumnEntity column : entity.getBoardColumns()) {
                column.setBoard(entity);
                boardColumnDAO.insert(column);
            }

            connection.commit(); // commit só após inserir tudo
        } catch (SQLException ex) {
            connection.rollback(); // rollback em caso de erro
            throw ex;
        }

        return entity;
    }

    public boolean delete(Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        try {
            if (!dao.exists(id)) {
                return false;
            }
            dao.delete(id);
            connection.commit();
            return true;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
}

