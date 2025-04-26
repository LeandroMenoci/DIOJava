import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();

        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime localDateTime = localDate.atTime(localTime);
//        System.out.println(formatter.format(localDateTime));
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        OffsetDateTime offsetDateTimeUTC =
                offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC);
//        offsetDateTime = offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC);
        System.out.println(offsetDateTime.isEqual(offsetDateTimeUTC));



//        System.out.println(Duration.between(localDateTime,
//                LocalDateTime.now()).toMillis());


//        Date date = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        ZoneId zoneId = calendar.getTimeZone().toZoneId();
//        LocalDateTime localDateTime1 =
//                LocalDateTime.ofInstant(calendar.toInstant(), zoneId);
//        System.out.println(localDateTime);



//        Date date = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(-4)));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        System.out.println(calendar);



//        System.out.println(localTime.withHour(2));
//        System.out.println(formatter.format(localTime));



//        System.out.println(localDate.getMonthValue());
//        System.out.println(localDate.getYear());
//        System.out.println(localDate.plusYears(20));
//        System.out.println(localDate.plus(50, ChronoUnit.DAYS));


//        var strDate = "22/12/2014";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        System.out.println(localDate.parse(strDate, formatter));
//        System.out.println(formatter.format(localDate));
    }
}