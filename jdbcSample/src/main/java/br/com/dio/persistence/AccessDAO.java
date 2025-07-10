package br.com.dio.persistence;

import java.sql.SQLException;

public class AccessDAO {

    public boolean insert(final long employeeId, final long moduleId) {
        if (employeeId <= 0 || moduleId <= 0) {
            throw new IllegalArgumentException("IDs devem ser maiores que zero");
        }

        String sql = "INSERT INTO accesses (employee_id, module_id) VALUES (?, ?)";
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, employeeId);
            statement.setLong(2, moduleId);
            return statement.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.printf("Erro ao inserir acesso: %s%n", ex.getMessage());
            return false;
        }
    }
}

