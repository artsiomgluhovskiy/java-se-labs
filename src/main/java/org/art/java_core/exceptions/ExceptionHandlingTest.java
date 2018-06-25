package org.art.java_core.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Simple exception handling code examples.
 */
public class ExceptionHandlingTest {

    private static final Logger LOG = LogManager.getLogger(ExceptionHandlingTest.class);

    private static final Random RND = new Random(System.currentTimeMillis());

    private static void npeHandleTest() {

        System.out.println("Task 1. NPE handle test");

        String testString = null;
        try {
            testString.toUpperCase();
        } catch (NullPointerException e) {
            LOG.error("NullPointerException has been caught!\n", e);
        }
        printEmptyLines(1);
    }

    private static void indexOutOfBoundsHandleTest() {

        System.out.println("Task 2. Index out of bounds test");

        int[] array = new int[10];
        try {
            array[10] = 2;
        } catch (IndexOutOfBoundsException e) {
            LOG.error("IndexOutOfBoundsException has been caught!\n", e);
        }
        printEmptyLines(1);
    }

    private static void customExceptionHandleTest() {

        System.out.println("Task 3. Custom CensorshipException handle test");

        String[] radioText = new String[3];

        radioText[0] = "Hello from radio BBC!";
        radioText[1] = "Today is a fucking beautiful day!";
        radioText[2] = "Let's talk!";

        System.out.println("Text for radio: " + Arrays.toString(radioText));

        for (int i = 0; i < radioText.length; i++) {
            try {
                censorshipCheck(radioText[i], i);
            } catch (CensorshipException e) {
                LOG.error("CensorshipException has been caught! Curse word was find in line {} and will be overwritten with '*' symbol.", i, e);
                radioText[i] = radioText[i].replace("fuck", "****");
            }
        }

        System.out.println("Text after censorship checking: " + Arrays.toString(radioText));
        printEmptyLines(1);
    }

    private static void userInfoSavingTest() {

        System.out.println("Task 4. User info saving (into a file)");

        User user1 = new User("John Couch", 1);
        User user2 = new User(null, 2);     //Incorrect user entity

        try {
            UserInfoHelper.saveUserInfo(user1);
            LOG.debug(user1 + " has been successfully saved!");
            UserInfoHelper.saveUserInfo(user2);
            LOG.debug(user2 + " has been successfully saved!");
        } catch (UserInfoSavingException e) {
            LOG.error("UserInfoSavingException has been caught", e);
        }
        printEmptyLines(1);
    }

    private static void multiCatchTest() {

        System.out.println("Task 5. Multi catch test");

        IntStream.range(1, 20).forEach((i) -> {
                    try {
                        generateRandomException();
                    } catch (FileNotFoundException | CloneNotSupportedException | SQLException | InterruptedException | EOFException e) {
                        //Catches only some checked exceptions here
                        LOG.error("Checked exception has been caught, {}!", e.getClass().getSimpleName());
                    } catch (UncheckedIOException | UnsupportedOperationException e) {
                        //Catches only some unchecked exceptions here
                        LOG.error("Unchecked exception has been caught, {}!", e.getClass().getSimpleName());
                    } catch (Exception e) {
                        //The most imprecise catch
                        LOG.error("Exception exception has been caught, {}!", e.getClass().getSimpleName());
                    }
                }
        );
        printEmptyLines(1);
    }

    private static boolean censorshipCheck(String textLine, int line) throws CensorshipException {
        if (textLine.toLowerCase().contains("fuck")) {
            LOG.error("Curse word was found in line {}", line);
            throw new CensorshipException("Curse word was found in line" + line + "!");
        }
        return true;
    }

    private static void printEmptyLines(int numOfLines) {
        for (int i = 0; i < numOfLines; i++) {
            System.out.println();
        }
    }

    private static void generateRandomException() throws Exception {
        int rndInt = RND.nextInt(8);
        switch (rndInt) {
            case 0:
                throw new FileNotFoundException();
            case 1:
                throw new InterruptedException();
            case 2:
                throw new SQLException();
            case 3:
                throw new UnsupportedOperationException();
            case 4:
                throw new CloneNotSupportedException();
            case 5:
                throw new EOFException();
            case 6:
                throw new Exception();
            default:
                throw new UncheckedIOException(new FileNotFoundException());
        }
    }

    public static void main(String[] args) {

        //Task 1
        npeHandleTest();

        //Task 2
        indexOutOfBoundsHandleTest();

        //Task 3
        customExceptionHandleTest();

        //Task 4
        userInfoSavingTest();

        //Task 5
        multiCatchTest();
    }
}
