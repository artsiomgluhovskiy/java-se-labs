package org.art.java_core.reg_exp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpTest {

    private static final Logger LOG = LogManager.getLogger(RegExpTest.class);

    private static void emailValidationTask() {

        System.out.println("Task 1. Email validation task");

        //Initial data
        String[] emails = new String[]{
                "gluhovskiy2_5_art3@gmail.com",
                "gluhovskiy2_5_art3@gmail.com2",
                "Gluhovskiy2_5_art3@gmail.ru",
                "2gluhovskiy2_5_art3@gmail.com",
                "gluhovskiy2_5_art3_@gmail.org",
                "hello@mail.ru"
        };
        System.out.println("Emails:\n" + Arrays.toString(emails));

        Pattern pattern = Pattern.compile("\\b[a-z]\\w+@\\w+(.com|.org)\\b");
        Matcher matcher = pattern.matcher("");

        System.out.println("Valid addresses:");
        for (int i = 0; i < emails.length; i++) {
            matcher.reset(emails[i]);
            if (matcher.matches()) {
                System.out.println(emails[i]);
            }
        }
        printEmptyLines(1);
    }

    private static void hexNumbersTask() {

        System.out.println("Task 2. Hex numbers task");

        //Initial data
        String inputText = "0xFF, 0b10101010, 0567, Hello, 0xAF34, 0x23FF, 0xJG34, 0xffaa";
        System.out.println("Initial text: " + inputText);

        Pattern pattern = Pattern.compile("\\b0x(?i)[\\dabcdef]+\\b");
        Matcher matcher = pattern.matcher(inputText);

        System.out.println("HEX numbers:");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        printEmptyLines(1);
    }

    private static void htmlTagTask() {

        System.out.println("Task 3. HTML Paragraph tag task");

        //Initial data
        String inputHtml = "<p id =\"p2\">Text</p><p id =\"p2\"><input type=\"text\"></p>";
        System.out.println("Initial html: " + inputHtml);

        Pattern pattern = Pattern.compile("<p.*?>");
        Matcher matcher = pattern.matcher(inputHtml);

        String modifiedHtml = matcher.replaceAll("<p>");
        System.out.println("Modified html: " + modifiedHtml);
        printEmptyLines(1);
    }

    private static void phoneNumbersTask() {

        System.out.println("Task 4. Phone number task");

        //Initial data
        String inputText = "80294567845  +375447398956  +375297368961  80447456786  +375 29 785 84 37";
        System.out.println("Initial text: " + inputText);

        Pattern pattern = Pattern.compile("([+]375)(44|29|33)(\\d{3})(\\d{2})(\\d{2})");
        Matcher matcher = pattern.matcher(inputText);

        String modifiedText = "";
        while (matcher.find()) {
            modifiedText = matcher.replaceAll("$1($2)$3-$4-$5");
        }
        System.out.println("New string: " + modifiedText);
        printEmptyLines(1);
    }

    private static void ipTask() {

        System.out.println("Task 5");

        //Initial data
        String[] inputText = new String[]{
                "127.67.90.87",
                "127.67.90.456",
                "255.67.90.01",
                "127.672.90.87",
                "127.67.90.01",
        };
        System.out.println(Arrays.toString(inputText));

        Pattern pattern = Pattern.compile("\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
        Matcher matcher = pattern.matcher("");

        System.out.println("IPv4: ");
        for (int i = 0; i < inputText.length; i++) {
            matcher.reset(inputText[i]);
            if (matcher.matches()) {
                System.out.println(inputText[i]);
            }
        }
        printEmptyLines(1);
    }

    private static void parsingTask() {

        System.out.println("Task 6. Text parsing task");

        //Target params to parse
        int width;
        int height;
        String embedId;

        //Default values
        final int DEFAULT_WIDTH = 400;
        final int DEFAULT_HEIGHT = 400;
        final String DEFAULT_EMBED_ID = "";

        //Initial data
        String script = "Tech Fact - Reviews\n" +
                "<div id='embed_container_2D8C44BA9'>\n" +
                "  <div id='iframe_container_2D8C44BA9'></div>\n" +
                "  <p id='linkback_paragraph_2D8C44BA9'><a id='linkback_2D8C44BA9' href='https://www.techvalidate.com'>Customer Proof</a> verified by TechValidate.</p>\n" +
                "  <script type=\"text/javascript\">\n" +
                "    var protocol = ((\"https:\" == document.location.protocol) ? \"https://\" : \"http://\");\n" +
                "    document.write(unescape(\"%3Cscript src='\" + protocol + \"www.techvalidate.com/assets/embed.js' type='text/javascript'%3E%3C/script%3E\"));\n" +
                "  </script>\n" +
                "  <script type='text/javascript'>\n" +
                "    var tvAsset_2D8C44BA9 = new TVAsset();\n" +
                "    tvAsset_2D8C44BA9.initialize({width: 660, height: 614, 'style': 'transparent',  'tvid':'2D8C44BA9', 'protocol':document.location.protocol});\n" +
                "    tvAsset_2D8C44BA9.display();\n" +
                "  </script>\n" +
                "</div>";

        Pattern patWidth = Pattern.compile("width:[\\s]{0,}([\\d]*)");
        Pattern patHeight = Pattern.compile("height:[\\s]{0,}([\\d]*)");
        Pattern patId = Pattern.compile("'tvid':[\\s]{0,}'([a-fA-F\\d]{9})'");

        Matcher matcher1 = patWidth.matcher(script);
        if (matcher1.find()) {
            width = Integer.parseInt(matcher1.group(1));
        } else {
            width = DEFAULT_WIDTH;
        }

        Matcher matcher2 = patHeight.matcher(script);
        if (matcher2.find()) {
            height = Integer.parseInt(matcher2.group(1));
        } else {
            height = DEFAULT_HEIGHT;
        }

        Matcher matcher3 = patId.matcher(script);
        if (matcher3.find()) {
            embedId = matcher3.group(1);
        } else {
            embedId = DEFAULT_EMBED_ID;
        }

        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        System.out.println("Embed ID: " + embedId);
        printEmptyLines(1);
    }

    private static void findWordsInFile() {

        System.out.println("Task 7. Find words in the file staring with vowels");

        //Initial data
        String filename = "C:\\Users\\Lenovo\\IdeaProjects\\JavaLabs\\java-labs\\src\\main\\resources\\files\\reg_exp\\task7.txt";

        List<String> list = findWords(filename);

        System.out.println("Words starting with vowels:\n" + list);
        printEmptyLines(1);
    }

    private static List<String> findWords(String filePath) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b(?i:[aeiouy])\\w{0,}");
        try (Scanner scanner = new Scanner(new FileReader(filePath))) {
            System.out.print("Origin text: \n\"");
            while(scanner.hasNext()) {
                String token = scanner.next();
                System.out.print(token + " ");
                Matcher matcher = pattern.matcher(token);
                if(matcher.matches()) {
                    list.add(token);
                }
            }
            System.out.print(".\"\n");
        } catch (IOException e) {
            LOG.error("IOException has been caught!", e);
        }
        return list;
    }

    private static void printEmptyLines(int numOfLines) {
        for (int i = 0; i < numOfLines; i++) {
            System.out.println();
        }
    }

    public static void main(String[] args) {

        //Task 1
        emailValidationTask();

        //Task 2
        hexNumbersTask();

        //Task 3
        htmlTagTask();

        //Task 4
        phoneNumbersTask();

        //Task 5
        ipTask();

        //Task 6
        parsingTask();

        //Task 7
        findWordsInFile();
    }
}
