import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        List<User> users = List.of(new User("Maria", 33), new User("João",
                32), new User("Leandro", 33));

//        printStringValue(user -> String.valueOf(user.age()), users);
//        printStringValue(User::name, users);
        printStringValue(User::toString, users);

    }

    private static void printStringValue(Function<User, String> callback,
                                         List<User> users) {
        users.forEach(u -> System.out.println(callback.apply(u)));

    }
}