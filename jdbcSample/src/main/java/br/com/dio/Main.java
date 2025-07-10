package br.com.dio;

import br.com.dio.persistence.*;
import br.com.dio.persistence.entity.ContactEntity;
import br.com.dio.persistence.entity.EmployeeEntity;
import br.com.dio.persistence.entity.ModuleEntity;
import net.datafaker.Faker;
import org.flywaydb.core.Flyway;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Stream;

import static java.time.ZoneOffset.UTC;

public class Main {

    private final static EmployeeParamDAO employeeDAO = new EmployeeParamDAO();
    private final static EmployeeAuditDAO employeeAuditDAO = new EmployeeAuditDAO();
    private final static Faker faker = new Faker(Locale.of("pt", "BR"));
    private final static ContactDAO contactDAO = new ContactDAO();
    private final static ModuleDAO moduleDAO = new ModuleDAO();

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

//        var employee = new EmployeeEntity();
//        employee.setName("Joao");
//        employee.setSalary(new BigDecimal("2500"));
//        employee.setBirthday(OffsetDateTime.now().minusYears(31));
//        System.out.println(employee);
//        employeeDAO.insert(employee);
//        System.out.println(employee);
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


//        var employee = new EmployeeEntity();
//        employee.setName("Pedro");
//        employee.setSalary(new BigDecimal("2500"));
//        employee.setBirthday(OffsetDateTime.now().minusYears(31));
//        System.out.println(employee);
//        employeeDAO.insert(employee);
//        System.out.println(employee);
//        var contact1 = new ContactEntity();
//        contact1.setDescription("pedro@pedro.com");
//        contact1.setType("e-mail");
//        contact1.setEmployee(employee);
//        contactDAO.insert(contact1);
//        var contact2 = new ContactEntity();
//        contact2.setDescription("41999999999");
//        contact2.setType("telefone");
//        contact2.setEmployee(employee);
//        contactDAO.insert(contact2);

//        System.out.println(employeeDAO.findById(23429));
//        System.out.println(contactDAO.findByEmployeeId(23429));




//        var entities = Stream.generate(() -> {
//            var employee = new EmployeeEntity();
//            employee.setName(faker.name().fullName());
//            employee.setSalary(new BigDecimal(faker.number().digits(4)));
//            employee.setBirthday(OffsetDateTime.of(LocalDate.now().minusYears(faker.number().numberBetween(40, 20)), LocalTime.MIN, UTC));
//            employee.setModules(new ArrayList<>());
//            var moduleAmount = faker.number().numberBetween(1, 4);
//            for (int i = 0; i < moduleAmount; i++) {
//                var module = new ModuleEntity();
//                module.setId(i +1);
//                employee.getModules().add(module);
//            }
//            return employee;
//        }).limit(3).toList();
//        entities.forEach(employeeDAO::insert);



        moduleDAO.findAll().forEach(System.out::println);
    }
}