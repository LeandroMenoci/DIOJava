package br.com.dio;

import br.com.dio.persistence.EmployeeDAO;
import br.com.dio.persistence.entity.EmployeeEntity;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Main {

    private final static EmployeeDAO employeeDAO = new EmployeeDAO();

    public static void main(String[] args) {

        var flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost/jdbcSample?useTimezone=true&serverTimezone=UTC", "root", "123456")
                .load();
        flyway.migrate();

        /*var employee = new EmployeeEntity();
        employee.setName("Rosa");
        employee.setSalary(new BigDecimal("4400"));
        employee.setBirthday(OffsetDateTime.now().minusYears(31));
        System.out.println(employee);
        employeeDAO.insert(employee);
        System.out.println(employee);*/

//        employeeDAO.findAll().forEach(System.out::println);

//        System.out.println(employeeDAO.findById(4));

        /*var employee = new EmployeeEntity();
        employee.setId(3L);
        employee.setName("Gabriel");
        employee.setSalary(new BigDecimal("5500"));
        employee.setBirthday(OffsetDateTime.now().minusYears(18).minusDays(13));
        employeeDAO.update(employee);
        System.out.println(employee);*/

//        employeeDAO.delete(1);


    }
}