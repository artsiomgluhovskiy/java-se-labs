package org.art.java_core.date_time;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Java 8 Date/Time API Tests.
 */
public class TimeTest {

    private static final Logger LOGGER = LogManager.getLogger(TimeTest.class);

    static void oldDateApiTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, 2);

        DateFormat simpleDateTimeFormat
                = new SimpleDateFormat("EEEE: dd-MM-yyyy");

        //java.util.Date
        java.util.Date utilDate = calendar.getTime();
        String formattedUtilTime = simpleDateTimeFormat.format(utilDate);
        System.out.println("'java.util.Date': " + formattedUtilTime);

        //Converting to java.sql.Date (
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String formattedSqlTime = simpleDateTimeFormat.format(sqlDate);
        System.out.println("'java.sql.Date': " + formattedSqlTime);

        DateFormat simpleFormat = new SimpleDateFormat("EEEE: dd-MM-yyyy");
        try {
            Date date = simpleFormat.parse(formattedUtilTime);
            System.out.println("Milliseconds: " + date.getTime() + '\n');
        } catch (ParseException e) {
            LOGGER.debug("Exception during the time parsing!", e);
        }
    }

    static void newLocalDateTimeCreationTest() {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime currentDateTime = LocalDateTime.of(localDate, localTime);
        System.out.println("Current date/time: " + currentDateTime);

        LocalDate oldDateTime = LocalDate.of(1800, Month.NOVEMBER, 5);
        System.out.println("Old date/time: " + oldDateTime);

        LocalDateTime parsedDateTime = LocalDateTime.parse("2017-12-05T11:30:30");
        System.out.println("Parsed date/time: " + parsedDateTime);

        ZoneId zoneId = ZoneId.getAvailableZoneIds().stream()
                .filter((zone) -> zone.startsWith("Africa"))
                .map(ZoneId::of)
                .findFirst()
                .get();
        LocalDateTime zonedDateTime = LocalDateTime.now(zoneId);
        System.out.println("Zoned date/time: " + zonedDateTime);

        //Date to LocalDateTime conversion
        Date date = Date.from(Instant.now());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDateTime convertedLocalDate = date.toInstant().atZone(defaultZoneId).toLocalDateTime();
        Date convertedDate = Date.from(convertedLocalDate.atZone(defaultZoneId).toInstant());
        System.out.println("Converted LocalDate: " + convertedLocalDate);
        System.out.println("Converted Date: " + convertedDate + '\n');
    }

    static void dateTimeModificationTest() {
        LocalDate today = LocalDate.now();
        System.out.println("Current date: " + today);
        LocalDate mod1 = today.plus(1, ChronoUnit.MONTHS);
        System.out.println("Mod 1. One month added: " + mod1);
        LocalDate mod2 = mod1.plusDays(2);
        System.out.println("Mod 2: Two days were added: " + mod2);
        LocalDate mod3 = mod2.minusWeeks(3);
        System.out.println("Mod 3: Three weeks were subtracted: " + mod3);
        LocalDate mod4 = mod3.plus(Period.of(1, 2, 0));
        System.out.println("Mod 4: One year and two months were added: " + mod4);

        LocalTime localTime = LocalTime.now();
        Instant instant = localTime.atDate(mod4).atZone(ZoneId.systemDefault()).toInstant();
        Date dateTime = Date.from(instant);
        System.out.println("Date/Time: " + dateTime + '\n');
    }

    static void zonedTimesTest() {
        ZonedDateTime londonTime = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime parisTime = ZonedDateTime.now(ZoneId.of("Europe/Paris"));
        ZonedDateTime minskTime = ZonedDateTime.now(ZoneId.of("Europe/Minsk"));
        ZonedDateTime denverTime = ZonedDateTime.now(ZoneId.of("America/Denver"));
        System.out.println("Current hour (Minsk): " + minskTime.getHour());
        System.out.println("London/Paris delta: " + (londonTime.getHour() - parisTime.getHour()));
        System.out.println("Minsk/Denver delta: " + (minskTime.getHour() - denverTime.getHour()));
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        zoneIds.forEach((zoneId) -> System.out.print(" " + zoneId + " "));
        System.out.println();
        System.out.println("Number of zones: " + zoneIds.size() + '\n');
    }

    static void formattingTest() {
        LocalDate now = LocalDate.now();
        String formattedString
                = now.format(DateTimeFormatter.ofPattern("dd--MM--yyyy"));
        System.out.println("Formatted date string: " + formattedString);

        String dateAsString = "20--06--1990";
        LocalDate parsedDate
                = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("dd--MM--yyyy"));
        System.out.println("New parsed date: " + parsedDate);
        System.out.println("Parsed year: " + parsedDate.getYear());
        System.out.println("Parsed month: " + parsedDate.getMonth());
        System.out.println("Parsed day of month: " + parsedDate.getDayOfMonth() + '\n');
    }

    static int calculateAge(String dayOfBirth) {
        LocalDate birthDate = LocalDate.parse(dayOfBirth, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        return period.getYears();
    }

    static void dateCompareTest() {
        LocalDate today = LocalDate.now();
        LocalDate future = today.plus(Period.of(1, 2, 0));
        LocalDate past = today.minus(Period.of(1, 0, 0));
        System.out.println("'Future' after 'today': " + today.isBefore(future));
        System.out.println("'Past' before 'today': " + today.isAfter(past) + '\n');
    }

    static void periodDurationTest() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayAtTime = today.atTime(13, 0, 0);
        System.out.println("Today at 13:00: " + todayAtTime);

        LocalDateTime tomorrowAtTime = today.plusDays(1).atTime(13, 0, 0);
        System.out.println("Tomorrow at 13:00: " + tomorrowAtTime);

        System.out.println("Duration between today and tomorrow (in days) is: "
                + ChronoUnit.DAYS.between(todayAtTime, tomorrowAtTime));
        Duration between = Duration.between(todayAtTime, tomorrowAtTime);
        System.out.println("Duration in seconds is: " + between.getSeconds());

        ZoneOffset zoneOffset = ZoneOffset.ofHours(1);
        OffsetDateTime dateTime = OffsetDateTime.of(2017, 1, 1, 0, 0,
                0, 0, zoneOffset);
        System.out.println("Date/time with zone offset: " + dateTime);

        LocalDate nextNewYear = LocalDate.of(2019, Month.JANUARY, 1);
        Period period = Period.between(nextNewYear, today);
        System.out.println(-period.getMonths() + " months and " + -period.getDays() + " before New Year!\n");
    }

    public static void main(String[] args) {

        System.out.println("*** Old java Date/Calendar API test ***");
        oldDateApiTest();

        System.out.println("*** New java Date/Time API LocalDateTime creation test ***");
        newLocalDateTimeCreationTest();

        System.out.println("*** Time/Date modification test ***");
        dateTimeModificationTest();

        System.out.println("*** Time zones test ***");
        zonedTimesTest();

        System.out.println("*** Simple formatting test ***");
        formattingTest();

        System.out.println("*** Age calculator (Period API) ***");
        String dateOfBirth = "24-02-1993";
        int age = calculateAge(dateOfBirth);
        System.out.println("The age for date of birth '" + dateOfBirth + "' is: " + age + '\n');

        System.out.println("*** Date comparing test ***");
        dateCompareTest();

        System.out.println("*** Period/duration test ***");
        periodDurationTest();
    }
}

