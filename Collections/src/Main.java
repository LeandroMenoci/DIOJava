import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
//        List<User> users = new ArrayList<>();
//        User user = new User(1, "Joao");
//        users.add(user);
//        users.add(new User(2, "Maria"));
//        users.add(new User(3, "Leo"));
//        System.out.println(users.contains(user));
//        System.out.println(users.size());
//        System.out.println(users.getFirst());
//        System.out.println(users.getLast());

//        System.out.println("========================");
//        System.out.println(users.contains(new User(1, "Joao")));
//        System.out.println(new User(1, "Joao"));

//        System.out.println("================");
//        System.out.println(users);
//        System.out.println(users.remove(new User(8, "Leo")));
//        System.out.println(users.remove(1));
//        System.out.println(users);
//        users.clear();
//        System.out.println(users);
        var linkedStart = OffsetDateTime.now();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 100_000_000; i++) {
            linkedList.add(i);
        }
        System.out.println(Duration.between(linkedStart, OffsetDateTime.now()).toMillis());

        var arrayStart = OffsetDateTime.now();
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 100_000_000; i++) {
            arrayList.add(i);
        }
        System.out.println(Duration.between(arrayStart, OffsetDateTime.now()).toMillis());

        var vectorStart = OffsetDateTime.now();
        List<Integer> vector = new Vector<>();
        for (int i = 0; i < 100_000_000; i++) {
            vector.add(i);
        }
        System.out.println(Duration.between(
                vectorStart, OffsetDateTime.now()).toMillis());



    }
}