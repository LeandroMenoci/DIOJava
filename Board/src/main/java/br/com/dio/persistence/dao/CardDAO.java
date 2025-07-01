package br.com.dio.persistence.dao;

import br.com.dio.persistence.dto.CardDetails;
import lombok.AllArgsConstructor;

import java.sql.Connection;
@AllArgsConstructor
public class CardDAO {
    private final Connection connection;

    public CardDetails findById(Long id) {
        return null;
    }
}
