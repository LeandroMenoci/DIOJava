import java.time.Duration;
import java.time.OffsetDateTime;


public class Main {
    public static void main(String[] args) {

        var builder = new StringBuilder("123456789");
        System.out.println(builder.delete(0, 3));


//        var stringStart = OffsetDateTime.now();
//        String stringConcat = "";
//        for (int i = 0; i < 400_000; i++) {
//            stringConcat += i;
//        }
//        var stringEnd = OffsetDateTime.now();
//        System.out.printf("String: %s \n", Duration.between(stringStart,
//                stringEnd).toMillis());

//        var stringBuilderStart = OffsetDateTime.now();
//        StringBuilder builderConcat = new StringBuilder();
//        for (int i = 0; i < 400_000; i++) {
//            builderConcat.append(i);
//        }
//        var stringBuilderEnd = OffsetDateTime.now();
//        System.out.printf("StringBuilder (singlethread): %s \n",
//                Duration.between(stringBuilderStart,
//                stringBuilderEnd).toMillis());
//
//        var stringBufferStart = OffsetDateTime.now();
//        StringBuffer bufferConcat = new StringBuffer();
//        for (int i = 0; i < 400_000; i++) {
//            bufferConcat.append(i);
//        }
//        var stringBufferEnd = OffsetDateTime.now();
//        System.out.printf("StringBuffer (Multithread): %s \n", Duration.between(stringBufferStart,
//                stringBufferEnd).toMillis());
    }
}