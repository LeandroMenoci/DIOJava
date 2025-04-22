import domain.User;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, User> users = new HashMap<>();
        users.put("joao@joao", new User("Joao", 22));
        users.put("maria@maria", new User("Maria", 25));
        users.put("monica@monica", new User("Monica", 19));
        users.put("magali@magali", new User("Magali", 33));

        System.out.println();

//        users.forEach((k,v) -> System.out.printf("Key: %s | value: %s \n", k, v));
//        users.replace("joao@joao", new User("Joao", 60));
//        System.out.println("=======================");
//        users.forEach((k,v) -> System.out.printf("Key: %s | value: %s \n", k, v));


//        System.out.println(users.remove("joao@joao", new User("Joao", 22)));


//        System.out.println(users);
//        System.out.println("================");
//        users.keySet().forEach(System.out::println);
//        System.out.println("===================");
//        users.values().forEach(System.out::println);

//        System.out.println(users.containsValue(new User("Marcos", 40)));
//        System.out.println(users.containsKey("marcos@marcos"));
//        System.out.println(users.containsValue(new User("Joao", 22)));
//        System.out.println(users.containsKey("joao@joao"));
    }
}