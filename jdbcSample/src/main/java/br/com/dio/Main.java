package br.com.dio;

import br.com.dio.persistence.ContactDAO;
import br.com.dio.persistence.EmployeeAuditDAO;
import br.com.dio.persistence.EmployeeDAO;
import br.com.dio.persistence.EmployeeParamDAO;
import br.com.dio.persistence.entity.ContactEntity;
import br.com.dio.persistence.entity.EmployeeEntity;
import net.datafaker.Faker;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.stream.Stream;

import static java.time.ZoneOffset.UTC;

public class Main {

    private final static EmployeeParamDAO employeeDAO = new EmployeeParamDAO();
    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();
    private final static Faker faker = new Faker(Locale.of("pt", "BR"));
    private final static ContactDAO contactDAO = new ContactDAO();

    public static void main(String[] args) {

        var flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost/jdbcSample?useTimezone=true&serverTimezone=UTC", "root", "123456")
                .load();
        flyway.migrate();

//        var insert = new EmployeeEntity();
//        insert.setName("Mateus");
//        insert.setSalary(new BigDecimal("4400"));
//        insert.setBirthday(OffsetDateTime.now().minusYears(31));
//        System.out.println(insert);
//        employeeDAO.insert(insert);
//        System.out.println(insert);

        var employee = new EmployeeEntity();
        employee.setName("Joao");
        employee.setSalary(new BigDecimal("2500"));
        employee.setBirthday(OffsetDateTime.now().minusYears(31));
        System.out.println(employee);
        employeeDAO.insert(employee);
        System.out.println(employee);
//        var contact = new ContactEntity();
//        contact.setDescription("miguel@miguel.com");
//        contact.setType("e-mail");
//        contact.setEmployee(employee);
//        contactDAO.insert(contact);

//        employeeDAO.findAll().forEach(System.out::println);

//        System.out.println(employeeDAO.findById(4));

//        var employee = new EmployeeEntity();
//        employee.setId(insert.getId());
//        employee.setName("Gabriele");
//        employee.setSalary(new BigDecimal("3500"));
//        employee.setBirthday(OffsetDateTime.now().minusYears(28).minusDays(13));
//        employeeDAO.update(employee);
//        System.out.println(employee);

//        employeeDAO.delete(insert.getId());

//        employeeAuditDAO.findAll().forEach(System.out::println);


//        var entities = Stream.generate(() -> {
//            var employee = new EmployeeEntity();
//            employee.setName(faker.name().fullName());
//            employee.setSalary(BigDecimal.valueOf(faker.number().numberBetween(1000, 9999)));
//            employee.setBirthday(OffsetDateTime.of(LocalDate.now().minusYears(faker.number().numberBetween(40,20)),
//                    LocalTime.MIN, UTC));
////            employee.setBirthday(OffsetDateTime.of(
////                    faker.date().birthdayLocalDate(),
////                    LocalTime.NOON,    // horário fixo no meio do dia para evitar horários fantasmas
////                    UTC)
////            );
//            return employee;
//        }).limit(10000).toList();
//
//        employeeDAO.insert(entities);


    }
}