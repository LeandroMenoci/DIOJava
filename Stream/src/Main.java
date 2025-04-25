import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        List<Integer> values1 = List.of(3, 6, 9, 12);
        List<Integer> values2 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        var newValues = values2.stream()
                .filter(values1::contains)
                .peek(n -> System.out.printf("Filter %s \n", n))
                .map(n -> values1.stream().reduce(n, (n1, n2) -> n1 - n2))
                .peek(n -> System.out.printf("Map %s \n", n))
                .collect(Collectors.toSet());

        System.out.println(newValues);




//        var value = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9,3,2,4,5)
//                .map(n -> n % 2 == 0)
//                .map(Object::toString)
//                .toList();
//                .distinct()
//                .toList();
//                .average();
//
//        System.out.println(value);



//        var value = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
//                .reduce(0, Integer::sum);
//
//        System.out.println(value);



//        var value = Stream.of("Maria", "Joao", "Marcio", "Luana", "Leandro",
//                        "Magalia")
//                .reduce("", (a,b) -> a  + b + ";");
//                .replaceFirst(";", "");
//
//        System.out.println(value);




//        var value = Stream.of("Maria", "Joao", "Marcio", "Luana", "Leandro",
//                        "Magalia")
//                .parallel()
//                .filter(n -> n.endsWith("z"))
//                .findAny();
//                .findFirst();
//                .allMatch(n -> n.contains("J"));
//                .noneMatch(n -> n.contains("a"));
//                .anyMatch(n -> n.contains("a"));
//
//        System.out.println(value);




//        List<String> debugValues = new ArrayList<>();
//        var value =Stream.of("Maria", "Joao", "Marcio", "Luana", "Leandro",
//                "Magali")
//                .peek(System.out::println)
//                .peek(debugValues::add)
//                .filter(name -> name.endsWith("o"))
//                .toList();
//        System.out.println(debugValues);
//        System.out.println(value);




//        var value1 =
//                Stream.generate(() -> new Random().nextInt())
//                        .limit(5)
//                        .toArray(Integer[]::new);
//
//        for (var v : value1) {
//            System.out.println(v);
//        }
//
//        System.out.println("=================");
//
//        var value2 = IntStream.generate(() -> new Random().nextInt())
//                .limit(5)
//                .toArray();
//
//        for (var v : value2) {
//            System.out.println(v);
//        }
    }
}
