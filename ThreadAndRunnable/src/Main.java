import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger number = new AtomicInteger(0);
//    private static int number = 0;



//    private static final Queue<Integer> numbers =
//            new LinkedBlockingQueue<>(250_000);
//    private static final List<Integer> numbers = new ArrayList<>();

//    private  static void inc(int number) {
//            numbers.add(number);
//    }
//
//    private  static void show() {
//            System.out.println(numbers);
//    }

    public static void main(String[] args) {
        Runnable inc = () -> {
            for (int i = 0; i < 100_000; i++) {
                number.incrementAndGet();
            }
        };

        Runnable dec = () -> {
            for (int i = 0; i > -100_000; i--) {
                number.decrementAndGet();
            }
        };

        Runnable show = () -> {
            for (int i = 0; i < 250_000; i++) {
                System.out.println(number);;
            }
        };


        var execInc = new Thread(inc);
        execInc.start();
        var execDec = new Thread(dec);
        execDec.start();
//        execDec.join(Duration.ofSeconds(3));
        var execShow = new Thread(show);
        execShow.start();

        System.out.println(execInc.getName());
        System.out.println(execDec.getName());
        System.out.println(execShow.getName());

//        new Thread(inc).start();
//        new Thread(dec).start();
//        new Thread(show).start();
    }
}