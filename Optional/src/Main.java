
import domain.User;
import domain.UserV2;

import java.util.Optional;

import static domain.SexEnum.FEMALE;
import static domain.SexEnum.MALE;

public class Main {
    public static void main(String[] args) {
        Optional<User> optional = Optional.ofNullable(null);
//        Optional<User> optional = Optional.of(new User("Joao", 19, MALE));

        Optional<UserV2> newUser =optional.map(user -> new UserV2(user.name(),
                user.age(),
                user.sex()));
        System.out.println(newUser.orElseThrow());

//        System.out.println(optional.orElse(defaultUser()));
//        System.out.println(optional.orElseGet(Main::defaultUser));


//        System.out.println(optional.orElse(new User("Maria", 22, FEMALE)));
        // valor default caso esteja vazio


//        int newAge = 22;
//        optional.ifPresentOrElse(
//                user -> {
//                    System.out.printf("Usuario %s \n", user);
//                    user = new User("Joao", newAge, MALE);
//                    System.out.printf("Usuario %s \n", user);
//
//                },
//                () -> System.out.println("Sem usuario")
//        );

//        Optional<User> optional = Optional.of(new User("Joao", 19, MALE));
//        optional.ifPresent(System.out::println);
//        Optional<User> optional = Optional.ofNullable(null);
//        Optional<User> optional = Optional.of(new User("Joao", 19, MALE));
//        System.out.println(optional.get());
    }

    public static User defaultUser(){
        System.out.println("Buscando valor default");
        return new User("Maria", 22, FEMALE);
    }
}