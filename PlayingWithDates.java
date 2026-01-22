import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/***
 * This Class will be a good tool to learn to deal with dates and timestamps in Java
 */
public class PlayingWithDates {
    public static void main(String[] args) {
        //Dates Example - java.time packages
        LocalDate currentDate = LocalDate.now();
        System.out.println("Today's Date is "+currentDate+" , and 5 months ago was "+currentDate.minusMonths(5));
        //Equivalent of extract in SQL
        System.out.println("Extracting from the Date, year : "+currentDate.getYear()+" , day of the week "+currentDate.getDayOfWeek());

        //Equivalent of To_Date function in Java
        LocalDate stringToDate = LocalDate.parse("2023-11-09", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("Parsed Date is "+stringToDate+" , adding 63 days to that date, it will be : "+stringToDate.plusDays(63));

        //TimeStamp example
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        System.out.println("Current Time is "+currentTimeStamp+" , 37 minutes into the future, it will be "+currentTimeStamp.plusMinutes(37));
    }
}
