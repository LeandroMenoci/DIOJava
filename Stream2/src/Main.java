
import domain.Contact;
import domain.Sex;
import domain.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static domain.ContacType.EMAIL;
import static domain.ContacType.PHONE;
import static domain.Sex.FEMALE;
import static domain.Sex.MALE;

public class Main {
    public static void main(String[] args) {

        List<User> users = new ArrayList<>(generateUser());

        var values = users.stream()
                .flatMap(u -> u.contacts().stream())
                .filter(c -> c.description().contains("gmail"))
//                .filter(c -> c.type() == PHONE)
//                .sorted((Comparator.comparing(Contact::description)))
//                .map(c -> String.format("{\n'description': '%s',\n'type': " +
//                                "'%s'\n}",
//                        c.description(), c.type()))
//                .filter(u -> u.contacts().stream().anyMatch(c -> c.type() == EMAIL))
//                .filter(u -> u.contacts() == null || u.contacts().isEmpty())
                .toList();

        values.forEach(System.out::println);

    }

    private static List<User> generateUser() {
        var contacts1 = List.of(
                new Contact("(19)99944-2332", PHONE),
                new Contact("joao@gmail.com", EMAIL)
        );

        var contacts2 = List.of(
                new Contact("(41)99944-2345", PHONE)
        );

        var contacts3 = List.of(

        );

        var contacts4 = List.of(
                new Contact("cebolinha@gmail.com", EMAIL)
        );

        var contacts5 = List.of(
                new Contact("cascao@outlook.com", EMAIL),
                new Contact("cascao@gmail.com", EMAIL)
        );

        var contacts6 = List.of(
                new Contact("(21)99342-5633", PHONE),
                new Contact("(21)99788-1231", PHONE)
        );

        var user1 = new User("Joao", 26, MALE, new ArrayList<>(contacts1));
        var user2 = new User("Maria", 21, FEMALE, new ArrayList<>(contacts2));
        var user3 = new User("Monica", 43, FEMALE, new ArrayList<>());
        var user4 = new User("Cebolinha", 12, MALE,
                new ArrayList<>(contacts4));
        var user5 = new User("Cascao", 32, MALE, new ArrayList<>(contacts5));
        var user6 = new User("Magali", 33, FEMALE, new ArrayList<>(contacts6));

        return List.of(user1, user2, user3, user4, user5, user6);
    }
}
