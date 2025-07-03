package br.com.dio.service;

import br.com.dio.persistence.dao.BoardColumnDAO;
import br.com.dio.persistence.dao.BoardDAO;
import br.com.dio.dto.BoardDetailsDTO;
import br.com.dio.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;
    private final BoardDAO boardDAO = new BoardDAO(connection);
    private final BoardColumnDAO boardColumnDAO = new BoardColumnDAO(connection);

    private Optional<BoardEntity> findBoardWithColumns(Long id) throws SQLException {
        return boardDAO.findById(id)
                .map(entity -> {
                    try {
                        entity.setBoardColumns(boardColumnDAO.findByBoardId(entity.getId()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return entity;
                });
    }

    public Optional<BoardEntity> findById(Long id) throws SQLException {
        return findBoardWithColumns(id);
    }

    public Optional<BoardDetailsDTO> showBoardDetails(Long id) throws SQLException {
        return findBoardWithColumns(id)
                .map(entity -> {
                    try {
                        var columns = boardColumnDAO.findByBoardIdWithDetails(entity.getId());
                        return new BoardDetailsDTO(entity.getId(), entity.getName(), columns);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}

