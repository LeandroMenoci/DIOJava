package br.com.dio;

import br.com.dio.persistence.EmployeeAuditDAO;
import br.com.dio.persistence.EmployeeDAO;
import br.com.dio.persistence.EmployeeParamDAO;
import br.com.dio.persistence.entity.EmployeeEntity;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Main {

    private final static EmployeeParamDAO employeeDAO = new EmployeeParamDAO();
    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();

    public static void main(String[] args) {

        var flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost/jdbcSample?useTimezone=true&serverTimezone=UTC", "root", "123456")
                .load();
        flyway.migrate();

        var insert = new EmployeeEntity();
        insert.setName("Mateus");
        insert.setSalary(new BigDecimal("4400"));
        insert.setBirthday(OffsetDateTime.now().minusYears(31));
        System.out.println(insert);
        employeeDAO.insertWithProcedure(insert);
        System.out.println(insert);

//        employeeDAO.findAll().forEach(System.out::println);

//        System.out.println(employeeDAO.findById(4));

        var employee = new EmployeeEntity();
        employee.setId(insert.getId());
        employee.setName("Gabriele");
        employee.setSalary(new BigDecimal("3500"));
        employee.setBirthday(OffsetDateTime.now().minusYears(28).minusDays(13));
        employeeDAO.update(employee);
        System.out.println(employee);

//        employeeDAO.delete(insert.getId());

        employeeAuditDAO.findAll().forEach(System.out::println);



    }
}