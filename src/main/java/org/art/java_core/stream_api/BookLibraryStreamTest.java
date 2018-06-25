package org.art.java_core.stream_api;

import org.art.java_core.stream_api.entity.Book;

import java.time.Year;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 * Some more advanced Stream API code examples.
 * Book library simulation (based on the examples of Mastering Lambdas book).
 */
public class BookLibraryStreamTest {

    private static final List<Book> LIBRARY = new ArrayList<>();

    static {

        //Initial data
        Book nails = new Book("Fundamentals of Chinese Fingernail Image",
                Arrays.asList("Li", "Fu", "Li"),
                new int[]{256},
                Book.Topic.MEDICINE,
                Year.of(2014),
                25.2);
        Book dragon = new Book("Compilers: Principles, Techniques and Tools",
                Arrays.asList("Aho", "Lam", "Sethi", "Ullman"),
                new int[]{1009},
                Book.Topic.COMPUTING,
                Year.of(2006),
                23.6);
        Book voss = new Book("Voss",
                Arrays.asList("Patrick White"),
                new int[]{478},
                Book.Topic.FICTION,
                Year.of(1957),
                19.6);
        Book lotr = new Book("Lord of the Rings",
                Arrays.asList("Tolkien"),
                new int[]{531, 416, 624},
                Book.Topic.FICTION,
                Year.of(1955),
                23.0);

        LIBRARY.add(nails);
        LIBRARY.add(dragon);
        LIBRARY.add(voss);
        LIBRARY.add(lotr);
    }

    private static void popularTopicTest() {

        System.out.println("Task 1. Finds the most popular topic in the library");

        Set<Book.Topic> popTopics = LIBRARY.stream()
                .collect(groupingBy(Book::getTopic, counting()))
                .entrySet().stream()
                .collect(groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toSet())))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue).orElse(new HashSet<>());

        System.out.println("The most popular topic is: " + popTopics);
        printEmptyLines(1);
    }

    private static void totalPageNumberTest() {

        System.out.println("Task 2. Counts the total number of pages of all books");

        //Variant 1
        int totalVolumes1 = LIBRARY.stream()
                .reduce(0, (iden, book) -> Integer.sum(iden, Arrays.stream(book.getPageCounts()).sum()),
                        Integer::sum);

        //Variant 2 (more preferable)
        int totalVolumes2 = LIBRARY.stream()
                .mapToInt(book -> Arrays.stream(book.getPageCounts()).sum())
                .sum();

        System.out.println("First variant: " + totalVolumes1 + ", second variant: " + totalVolumes2 + "\n");
        printEmptyLines(1);
    }

    private static void tallestBookFinderTest() {

        System.out.println("Task 3. Finds the tallest book in each topic");

        Comparator<Book> htComparator = Comparator.comparing(Book::getHeight);

        Map<Book.Topic, Optional<Book>> maxHeightByTopic = LIBRARY.stream()
                .collect(groupingBy(Book::getTopic, reducing(BinaryOperator.maxBy(htComparator))));

        System.out.println(maxHeightByTopic + "\n");

        LIBRARY.stream()
                .map((Book b) -> {
                    int[] volumes = b.getPageCounts();
                    return
                            IntStream.rangeClosed(1, volumes.length)
                                    .mapToObj(i -> i + ":" + volumes[i - 1])
                                    .collect(joining(", ", b.getTitle() + ": ", ""));
                })
                .forEach(System.out::println);

        printEmptyLines(1);
    }

    private static void printEmptyLines(int numOfLines) {
        for (int i = 0; i < numOfLines; i++) {
            System.out.println();
        }
    }

    public static void main(String[] args) {

        //Task 1
        popularTopicTest();

        //Task 2
        totalPageNumberTest();

        //Task 3
        tallestBookFinderTest();
    }
}
