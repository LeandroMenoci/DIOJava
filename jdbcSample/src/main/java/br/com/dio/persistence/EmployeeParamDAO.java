package br.com.dio.persistence;

import br.com.dio.persistence.entity.EmployeeEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.BIGINT;
import static java.time.ZoneOffset.UTC;

public class EmployeeParamDAO {


    public void insert(final EmployeeEntity entity) {
        String sql = "INSERT INTO employees (name, salary, birthday) VALUES (?, ?, ?)";

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            // Preenche os parâmetros com segurança (evita SQL injection)
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setBigDecimal(2, entity.getSalary());

            // Converte OffsetDateTime para java.sql.Timestamp (sem risco de formato inválido)
            var timestamp = Timestamp.from(entity.getBirthday().toInstant());
            preparedStatement.setTimestamp(3, timestamp);

            // Executa o INSERT
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.printf("Foram afetados %s registros na base de dados\n", rowsAffected);

            // Obtém o ID gerado automaticamente e atualiza a entidade
            try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insert(final List<EmployeeEntity> entities) {
        try (var connection = ConnectionUtil.getConnection()) {
            // SQL com placeholders para prepared statement (seguro contra SQL injection)
            var sql = "INSERT INTO employees (name, salary, birthday) VALUES (?, ?, ?)";

            try (var statement = connection.prepareStatement(sql)) {
                // Desabilita o autocommit para agrupar os inserts em uma transação
                connection.setAutoCommit(false);

                // Loop para adicionar todos os inserts ao batch
                for (int i = 0; i < entities.size(); i++) {
                    var entity = entities.get(i);

                    statement.setString(1, entity.getName());
                    statement.setBigDecimal(2, entity.getSalary());

                    var birthday = entity.getBirthday();

//                    // Protege contra datas inválidas para o tipo TIMESTAMP
//                    var minValidDate = LocalDate.of(1970, 1, 1);
//                    if (birthday.toLocalDate().isBefore(minValidDate)) {
//                        birthday = OffsetDateTime.of(minValidDate, LocalTime.MIN, UTC);
//                    }

                    // Converte OffsetDateTime para Timestamp (truncando milissegundos)
                    var timestamp = Timestamp.valueOf(
                            birthday.truncatedTo(ChronoUnit.SECONDS)
                                    .withOffsetSameInstant(UTC)
                                    .toLocalDateTime()
                    );
                    statement.setTimestamp(3, timestamp);

                    // Adiciona o insert atual ao lote
                    statement.addBatch();

                    // Executa o batch a cada 1000 registros ou no último item
                    if (i % 1000 == 0 || i == entities.size() - 1) {
                        statement.executeBatch();
                    }
                }

                // Confirma a transação após inserir todos os registros
                connection.commit();

            } catch (SQLException ex) {
                // Se der erro no insert, faz rollback da transação
                connection.rollback();
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            // Falha na conexão ou rollback
            ex.printStackTrace();
        }
    }



    // Insere um funcionário na tabela usando uma stored procedure
    public void insertWithProcedure(final EmployeeEntity entity) {
        try (
                // Abre conexão com o banco de dados
                var connection = ConnectionUtil.getConnection();

                // Prepara chamada à procedure 'prc_insert_employee' com 4 parâmetros
                var statement = connection.prepareCall("CALL prc_insert_employee(?, ?, ?, ?)")
        ) {
            // REGISTRA o primeiro parâmetro como saída (OUT) para retornar o ID gerado
            statement.registerOutParameter(1, BIGINT);

            // Define o segundo parâmetro (nome) como entrada (IN)
            statement.setString(2, entity.getName());

            // Define o terceiro parâmetro (salário) como entrada (IN)
            statement.setBigDecimal(3, entity.getSalary());

            // Define o quarto parâmetro (data de nascimento) como entrada (IN)
            // Conversão segura de OffsetDateTime para Timestamp usando UTC
            statement.setTimestamp(4,
                    Timestamp.valueOf(entity.getBirthday()
                            .atZoneSameInstant(UTC) // converte com segurança para UTC
                            .toLocalDateTime())
            );

            // Executa a procedure
            statement.execute();

            // Lê o valor do ID retornado pelo parâmetro de saída
            entity.setId(statement.getLong(1));

        } catch (SQLException ex) {
            // Em caso de erro, imprime a stack trace para facilitar o debug
            ex.printStackTrace();
        }
    }


    public void update(final EmployeeEntity entity) {
        // Define a SQL de atualização com placeholders (?) para uso seguro com PreparedStatement
        String sql = """
                        UPDATE employees SET 
                            name = ?, 
                            salary = ?, 
                            birthday = ? 
                        WHERE id = ?
                     """;

        try (
                // Abre a conexão com o banco e prepara a instrução SQL
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(sql)
        ) {
            // Preenche os parâmetros da query com os dados da entidade de forma segura
            preparedStatement.setString(1, entity.getName()); // 1º ? => nome
            preparedStatement.setBigDecimal(2, entity.getSalary()); // 2º ? => salário

            // Converte OffsetDateTime para Timestamp para uso com JDBC
            var timestamp = Timestamp.from(entity.getBirthday().toInstant());
            preparedStatement.setTimestamp(3, timestamp); // 3º ? => aniversário

            // Define o ID do funcionário a ser atualizado (condição WHERE)
            preparedStatement.setLong(4, entity.getId()); // 4º ? => id

            // Executa a atualização no banco de dados
            int rows = preparedStatement.executeUpdate();

            // Imprime quantos registros foram afetados (esperado: 0 ou 1)
            System.out.printf("Foram afetados %d registros na base de dados\n", rows);

        } catch (SQLException ex) {
            // Em caso de erro SQL, imprime a stack trace para depuração
            ex.printStackTrace();
        }
    }


    public void delete(final long id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(sql)
        ) {
            // Define o valor do parâmetro (id do funcionário a ser deletado)
            preparedStatement.setLong(1, id);

            // Executa o comando DELETE
            int rowsAffected = preparedStatement.executeUpdate();

            // Exibe quantos registros foram afetados (0 ou 1 esperado)
            System.out.printf("Foram excluídos %d registros da base de dados\n", rowsAffected);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public List<EmployeeEntity> findAll() {
        List<EmployeeEntity> entities = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY name";

        try (
                var connection = ConnectionUtil.getConnection();
                var statement = connection.createStatement();
                var resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()) {
                entities.add(mapResultSetToEmployee(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return entities;
    }


    public EmployeeEntity findById(final long id) {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (
                var connection = ConnectionUtil.getConnection();
                var preparedStatement = connection.prepareStatement(sql)
        ) {
            // Define o valor do parâmetro na query com segurança
            preparedStatement.setLong(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEmployee(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Retorna null se não encontrar o registro
        return null;
    }

    private EmployeeEntity mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        var entity = new EmployeeEntity();
        entity.setId(resultSet.getLong("id"));
        entity.setName(resultSet.getString("name"));
        entity.setSalary(resultSet.getBigDecimal("salary"));

        var timestamp = resultSet.getTimestamp("birthday");
        if (timestamp != null) {
            entity.setBirthday(timestamp.toInstant().atOffset(UTC));
        }

        return entity;
    }
}
