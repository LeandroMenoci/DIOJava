package br.com.dio.service;

import br.com.dio.persistence.dao.CardDAO;
import br.com.dio.dto.CardDetailsDTO;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class CardQueryService {

    private final Connection connection;

    public Optional<CardDetailsDTO> findById(Long id) {
        try {
            var dao = new CardDAO(connection);
            return dao.findById(id);
        } catch (SQLException e) {
            // Pode logar, encapsular ou lançar uma exceção customizada
            throw new RuntimeException("Erro ao buscar o card de id " + id, e);
        }
    }
}
