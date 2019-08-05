package org.art.java_core.stream_api;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.java_core.nio.BasicAsynchronousFileChannelTest;
import org.art.java_core.stream_api.entity.DispLine;
import org.art.java_core.stream_api.entity.Person;

import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

/**
 * Stream API: simple code examples.
 */
public class StreamTest {

    private static final Logger LOG = LogManager.getLogger(BasicAsynchronousFileChannelTest.class);

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile("test[a-zA-Z]*.txt");
    private static final Pattern TELEPHONE_NUMBER_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");

    private static final String IO_EXC_ERROR_MESSAGE = "IO Exception has been caught!";

    private static void collectorsTest() {

        System.out.println("Test 1. Collectors API");

        //Custom map collector
        Map<Integer, String> map = Stream.of(50, 55, 69, 20, 19, 52)
                .collect(toMap(
                        i -> i % 5,
                        i -> String.format("<%d>", i),
                        (a, b) -> String.join(", ", a, b)
                ));
        System.out.println(map);

        //Collect unique elements into list
        ArrayList<Integer> uniqueList = Stream.of(1, 2, 3, 1, 9, 2, 5, 2, 5, 3, 4, 8, 2)
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(LinkedHashSet::new),
                        ArrayList::new));
        System.out.println(uniqueList);

        //Collects unique elements into 2 collections
        Map<Boolean, List<Integer>> superMap = Stream.of(1, 2, 3, 1, 9, 2, 5, 2, 5, 3, 4, 8, 2)
                .collect(
                        () -> {
                            Map<Boolean, List<Integer>> suplMap = new HashMap<>();
                            suplMap.put(true, new ArrayList<>());      //repeated values
                            suplMap.put(false, new ArrayList<>());     //unique values
                            return suplMap;
                        },
                        (Map<Boolean, List<Integer>> accumMap, Integer integer) -> {
                            if (accumMap.get(Boolean.FALSE).contains(integer)) {
                                accumMap.get(Boolean.TRUE).add(integer);
                            } else {
                                accumMap.get(Boolean.FALSE).add(integer);
                            }
                        },
                        (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
                            map1.get(Boolean.TRUE).addAll(map2.get(Boolean.TRUE));
                            map1.get(Boolean.FALSE).addAll(map2.get(Boolean.FALSE));
                        }
                );
        System.out.println(superMap);
        printEmptyLines(1);
    }

    private static void fileWalkerTest() {

        System.out.println("Task 2. File walker test");

        String targetDir = "./src/main/java/org/art/java_core";
        Path startPath = Path.of(targetDir);
        System.out.println(startPath);
        try (Stream<Path> pathStream = Files.walk(startPath)) {
            pathStream
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .map(f -> f.getAbsolutePath() + " " + f.length())
                    .forEachOrdered(System.out::println);
        } catch (IOException e) {
            LOG.error(IO_EXC_ERROR_MESSAGE, e);
        }
        printEmptyLines(1);
    }

    private static void simpleGrepTest() {

        System.out.println("Task 3. Simple 'grep' program simulation");

        String targetDir = "./src/main/resources/files/stream_api";

        Path start = Path.of(targetDir);

        try (Stream<Path> pathStream = Files.walk(start)) {
            pathStream
                    .filter(Files::isRegularFile)
                    .filter(path -> FILE_NAME_PATTERN.matcher(path.toString()).find())
                    .map(Path::toAbsolutePath)
                    .forEach(System.out::println);
        } catch (IOException e) {
            LOG.error(IO_EXC_ERROR_MESSAGE, e);
        }
        printEmptyLines(1);
    }

    private static void customSpliteratorTest() {

        System.out.println("Task 4. Custom spliterator test");

        String targetFilePath = "./src/main/resources/files/stream_api/custom_data.txt";
        Path startFile = Path.of(targetFilePath);
        try (FileChannel fc = FileChannel.open(startFile)) {
            MappedByteBuffer bB = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            Spliterator<DispLine> ls = new LineSpliterator(bB, 0, bB.limit() - 1);
            StreamSupport.stream(ls, true)
                    .filter(dl -> TELEPHONE_NUMBER_PATTERN.matcher(dl.getLine()).find())
                    .forEachOrdered(System.out::println);
        } catch (IOException e) {
            LOG.error(IO_EXC_ERROR_MESSAGE, e);
        }
        printEmptyLines(1);
    }

    private static void getByTypeTest() {

        System.out.println("Task 5. Gets elements with the specified type");

        List<A> itemList = asList(new A("A1"), new B("B1"), new C("C1"), new A("A2"));
        itemList.stream()
                .flatMap(selectItem(B.class))
                .forEach(item -> System.out.println("Item : " + item.getClass().getSimpleName()));
        printEmptyLines(1);
    }

    private static void cartesianProductTest() {

        System.out.println("Task 6. Cartesian product test");

        List<List<String>> input = asList(
                asList("a", "b", "c"),
                asList("x", "y"),
                asList("1", "2", "3")
        );
        Supplier<Stream<String>> sup = input.stream()
                //Stream<List<String>>
                .map(list -> (Supplier<Stream<String>>) list::stream)
                //Stream<Supplier<Stream<String>>>
                .reduce((sup1, sup2) -> () -> sup1.get().flatMap(e1 -> sup2.get().map(e2 -> e1 + e2)))
                //Optional<Supplier<Stream<String>>>
                .orElse(() -> Stream.of(StringUtils.EMPTY));
        sup.get().forEach(System.out::println);
        printEmptyLines(1);
    }

    private static Function<Integer, Integer> factorialFunction;

    private static void factorialTest() {

        System.out.println("Task 7. Factorial calculation test (recursive lambda)");

        factorialFunction = val -> val == 0 ? 1 : val * factorialFunction.apply(val - 1);

        System.out.println("Factorial result: " + calculate(4, factorialFunction));
        printEmptyLines(1);
    }

    private static void personListTest() {

        System.out.println("Task 8. Person list test");

        List<Person> persons = Arrays.asList(
                new Person("John", "Smith", 25),
                new Person("Wiliam", "Gladis", 32),
                new Person("Abraham", "Wilgardner", 43),
                new Person("Nill", "Folkman", 29)
        );
        System.out.println("Origin list of persons:");
        persons.forEach(p -> System.out.printf("Full name: %s %s, age: %d%n", p.getFirstName(), p.getLastName(), p.getAge()));
        persons.stream()
                .filter(p -> (p.getFirstName() + p.getLastName()).toCharArray().length <= 15)
                .max(Comparator.comparingInt(Person::getAge))
                .ifPresent(p -> System.out.printf("Full name: %s %s, age: %d%n", p.getFirstName(), p.getLastName(), p.getAge()));
        printEmptyLines(1);
    }

    private static void parallelSortTest() {

        System.out.println("Task 9. Parallel sort speed test");

        ForkJoinPool commonPool = ForkJoinPool.commonPool();

        System.out.println("Parallel pool: " + commonPool.getParallelism());
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());

        int range = 10_000_000;

        List<Integer> intList1 = new ArrayList<>();

        Random rnd = new Random(System.currentTimeMillis());

        IntStream.range(0, range)
                .forEach(i -> intList1.add(rnd.nextInt(range)));

        List<Integer> intList2 = new ArrayList<>(intList1);

        //Parallel sort
        long start2 = System.nanoTime();
        intList2.parallelStream()
                .sorted()
                .collect(toList());
        System.out.println("ArrayList has been sorted (parallel method). Required time: " + (System.nanoTime() - start2) / 1000 + " ms.");

        //Sequential sort
        long start1 = System.nanoTime();
        Collections.sort(intList1);
        System.out.println("ArrayList has been sorted (sequential method). Required time: " + (System.nanoTime() - start1) / 1000 + " ms.");

        printEmptyLines(1);
    }

    private static void groupUsersTest() {

        System.out.println("Task 10. Grouping users test");

        String userDataFilePath = "./src/main/resources/files/stream_api/user_data.txt";

        Collector<UserInfo, List<String>, List<String>> userOpCollector = Collector.of(
                ArrayList::new,
                (list, item) -> list.add(item.getOperation()),
                (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });
        try (Stream<String> userLines = Files.lines(Path.of(userDataFilePath))) {
            Map<String, List<String>> userInfo = userLines
                    .map(line -> line.split(","))
                    .filter(values -> values.length > 2)
                    .map(values -> new UserInfo(values[0], values[1], values[2]))
                    .collect(groupingBy(UserInfo::getLogin, userOpCollector));
            System.out.println(userInfo);
        } catch (IOException e) {
            LOG.error(IO_EXC_ERROR_MESSAGE, e);
        }
        printEmptyLines(1);
    }

    private static <T, V> Function<T, Stream<V>> selectItem(Class<V> clazz) {
        return e -> clazz.isInstance(e) ? Stream.of(clazz.cast(e)) : null;
    }

    private static <V, R> R calculate(V value, Function<V, R> function) {
        R result;
        result = function.apply(value);
        return result;
    }

    private static void printEmptyLines(int numOfLines) {
        for (int i = 0; i < numOfLines; i++) {
            System.out.println();
        }
    }

    public static void main(String[] args) {

        //Test 1
        collectorsTest();

        //Task 2
        fileWalkerTest();

        //Task 3
        simpleGrepTest();

        //Task 4
        customSpliteratorTest();

        //Task 5
        getByTypeTest();

        //Task 6
        cartesianProductTest();

        //Task 7
        factorialTest();

        //Task 8
        personListTest();

        //Task 9
        parallelSortTest();

        //Task 10
        groupUsersTest();
    }
}

class A {

    private String name;

    A(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class B extends A {

    B(String name) {
        super(name);
    }
}

class C extends B {

    C(String name) {
        super(name);
    }
}

@Data
class UserInfo {

    private final String userId;
    private final String operation;
    private final String login;
}
