package br.com.dio.persistence;

import br.com.dio.persistence.entity.EmployeeAuditEntity;
import br.com.dio.persistence.entity.OperationEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.isNull;

public class EmployeeAuditDAO {

    public List<EmployeeAuditEntity> findAll() {
        // Lista que armazenará os registros de auditoria
        List<EmployeeAuditEntity> entities = new ArrayList<>();

        try (
                // Conexão e statement para executar a view
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement()
        ) {
            // Executa a consulta sobre a view
            statement.executeQuery("SELECT * FROM view_employee_audit");

            // Recupera o ResultSet com os dados retornados
            var resultSet = statement.getResultSet();

            // Itera sobre os registros retornados
            while (resultSet.next()) {
                entities.add(new EmployeeAuditEntity(
                        resultSet.getLong("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getString("old_name"),
                        resultSet.getBigDecimal("salary"),
                        resultSet.getBigDecimal("old_salary"),
                        getDateTimeOrNull(resultSet, "birthday"),
                        getDateTimeOrNull(resultSet, "old_birthday"),
                        OperationEnum.getByDbOperation(resultSet.getString("operation")) // converte 'I', 'U', 'D'
                ));
            }

        } catch (SQLException ex) {
            // Em caso de falha, exibe a stack trace para debug
            ex.printStackTrace();
        }

        return entities; // Retorna a lista de auditorias
    }

    public OffsetDateTime getDateTimeOrNull(final ResultSet resultSet, final String field) throws SQLException {
        return isNull(resultSet.getTimestamp(field)) ? null :
                OffsetDateTime.ofInstant(resultSet.getTimestamp(field).toInstant(), UTC);
    }

}
