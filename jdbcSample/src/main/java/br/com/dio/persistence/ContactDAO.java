package br.com.dio.persistence;

import br.com.dio.persistence.entity.ContactEntity;
import br.com.dio.persistence.entity.EmployeeEntity;
import com.mysql.cj.jdbc.StatementImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    public void insert(final ContactEntity entity){
        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(
                        "INSERT INTO contacts (description, type, employee_id) values (?, ?, ?);"
                )
        ) {
            // Preenche os valores do prepared statement para prevenir SQL Injection
            statement.setString(1, entity.getDescription());
            statement.setString(2, entity.getType());

            // Aqui é necessário validar se o employee não é null antes de acessar getId()
            statement.setLong(3, entity.getEmployee().getId());

            // Executa o INSERT
            statement.executeUpdate();

            // Se o driver MySQL estiver sendo usado, recupera o ID gerado
            if (statement instanceof StatementImpl impl)
                entity.setId(impl.getLastInsertID());

        } catch (SQLException ex) {
            // Em produção, logar corretamente o erro ao invés de usar printStackTrace
            ex.printStackTrace();
        }
    }


    public List<ContactEntity> findByEmployeeId(final long employeeId) {
        List<ContactEntity> entities = new ArrayList<>();

        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.prepareStatement(
                        "SELECT * FROM contacts WHERE employee_id = ?"
                )
        ) {
            statement.setLong(1, employeeId);

            // Executa o SELECT e processa os resultados
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var entity = new ContactEntity();
                    entity.setId(resultSet.getLong("id"));
                    entity.setDescription(resultSet.getString("description"));
                    entity.setType(resultSet.getString("type"));

                    // Cria um EmployeeEntity vazio apenas com o ID
                    var employee = new EmployeeEntity();
                    employee.setId(resultSet.getLong("employee_id"));
                    entity.setEmployee(employee);

                    entities.add(entity);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return entities;
    }

}
