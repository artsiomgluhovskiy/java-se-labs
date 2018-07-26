package org.art.java_core.stream_api;

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

/**
 * Stream API: simple code examples.
 */
public class StreamTest {

    private static final Logger LOG = LogManager.getLogger(BasicAsynchronousFileChannelTest.class);

    private static final Pattern TELEPHONE_NUMBER_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");

    private static void basicStreamTest() {

        System.out.println("Test 1. Stream API Basics");

        /* *** Stream data sources *** */

        //Get stream from array
        String[] stringArray = {"Artem", "Alex", "Joshua", "Max", "Tim", "Raul"};
        Stream<String> stringStream = Arrays.stream(stringArray);
        stringStream.forEach(System.out::println);

        //More preferable
        stringStream = Stream.of("Artem", "Alex", "Joshua", "Max", "Tim", "Raul");
        String namesString = stringStream.collect(Collectors.joining(", ", "Names: ", ""));
        System.out.println(namesString);

        //Empty stream
        Stream.empty().forEach(System.out::println);

        //Reuse of streams (stream suppliers)
        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("a", "b", "c", "d")
                        .filter(str -> str.startsWith("a"));

        boolean res1 = streamSupplier.get().anyMatch(str -> true);
        boolean res2 = streamSupplier.get().noneMatch(str -> true);
        System.out.println(res1);
        System.out.println(res2);

        //Infinite sequence of numbers
        Stream.iterate(2, x -> x + 2)
                .limit(6)
                .forEach(System.out::print);

        printEmptyLines(1);

        //Stream concatenation
        Stream.concat(
                Stream.of(2, 4, 50),
                Stream.of(1, 5, 6))
                .forEach(System.out::print);

        printEmptyLines(1);

        /* *** Intermediate operations *** */

        //Map
        Stream.of("10", "11", "13")
                .map(s -> Integer.parseInt(s, 10))
                .forEach(System.out::print);

        printEmptyLines(1);

        //Flat map
        Stream.of(3, 2, 1, 0)
                .flatMap(x -> Stream.of(0, x))
                .forEach(System.out::print);

        printEmptyLines(1);

        //Sorted
        Stream.of(120, 57, 48, 1, 2, 99)
                .sorted()
                .forEach(System.out::print);

        printEmptyLines(1);

        //Distinct
        Stream.of(2, 1, 8, 1, 3, 2)
                .distinct()
                .forEach(System.out::print);

        printEmptyLines(1);

        //Peek (avoid terminal 'forEach')
        Stream.of(0, 3, 0, 0, 5)
                .peek(x -> System.out.println("before distinct: " + x))
                .distinct()
                .peek(x -> System.out.println("after distinct" + x))
                .map(x -> x * x)
                .forEach(x -> System.out.println("after map: " + x));

        //Boxed (from primitive to object type)
        DoubleStream.of(2.3, Math.PI)
                .boxed()
                .map(Object::getClass)
                .forEach(System.out::print);

        printEmptyLines(1);

        //Limit
        IntStream.of(120, 410, 85, 32, 314, 12)
                .filter(x -> x < 300)
                .map(x -> x + 11)
                .limit(3)
                .forEach(System.out::print);

        /* *** Terminal operations *** */

        //Ordered for each (parallel stream)
        IntStream.range(0, 10000)
                .parallel()
                .filter(x -> x % 1000 == 0)
                .map(x -> x / 1000)
                .forEachOrdered(System.out::println);

        //Collect
        List<Integer> col1 = Stream.of(1, 2, 3)
                .collect(Collectors.toList());
        System.out.println(col1);

        String str = Stream.of(1, 2, 3)
                .map(String::valueOf)
                .collect(Collectors.joining("-", "<", ">"));
        System.out.println(str);

        List<String> col2 = Stream.of("a", "c", "b", "d").collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(col2);

        String[] arr = Stream.of("a", "b", "c", "d").toArray(String[]::new);
        System.out.println(Arrays.toString(arr));

        //Reduce
        int sum = Stream.of(1, 2, 3, 4, 5)
                .reduce(10, (acc, x) -> acc + x);
        System.out.println(sum);

        Optional<Integer> result = Stream.<Integer>empty()
                .reduce((acc, x) -> acc + x);
        System.out.println(result.isPresent());

        OptionalInt max = IntStream.of(3, 5, 6, 7, 10, 1, 5)
                .reduce(Math::max);
        System.out.println("Max value is: " + max.getAsInt());

        //All match
        boolean res = Stream.of(1, 2, 3, 4, 5)
                .allMatch(x -> x <= 7);
        System.out.println(res);

        //Summary statistics
        LongSummaryStatistics stats = LongStream.range(2, 16)
                .summaryStatistics();
        System.out.println(stats.getAverage());
        printEmptyLines(1);
    }

    private static void collectorsTest() {

        System.out.println("Test 2. Collectors API");

        //Custom map collector
        Map<Integer, String> map = Stream.of(50, 55, 69, 20, 19, 52)
                .collect(Collectors.toMap(
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

        System.out.println("Task 3. File walker test");

        Path startPath = new File(".\\src\\main\\java\\org\\art\\java_core").toPath();
        System.out.println(startPath);
        try (Stream<Path> pathStream = Files.walk(startPath)) {
            pathStream
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .map(f -> f.getAbsolutePath() + " " + f.length())
                    .forEachOrdered(System.out::println);
        } catch (IOException e) {
            LOG.error("IO Exception has been caught!", e);
        }
        printEmptyLines(1);
    }

    private static void simpleGrepTest() {

        System.out.println("Task 4. Simple 'grep' program simulation");

        Path start = new File("C:\\Users\\admin1\\MyDocs\\my-heap\\Music\\New folder").toPath();
        Pattern filePattern = Pattern.compile("test[a-zA-Z]*.txt");

        try (Stream<Path> pathStream = Files.walk(start)) {
            pathStream
                    .filter(Files::isRegularFile)
                    .filter(path -> filePattern.matcher(path.toString()).find())
                    .map(StreamTest::readLines)
                    .filter(line -> !line.isEmpty())
                    .forEach(System.out::println);
        } catch (IOException e) {
            LOG.error("IO Exception has been caught!", e);
        }
        printEmptyLines(1);
    }

    private static void customSpliteratorTest() {

        System.out.println("Task 5. Custom spliterator test");

        Path startFile = new File(".\\src\\main\\resources\\files\\stream_api\\file-source.txt").toPath();

        try (FileChannel fc = FileChannel.open(startFile)) {
            MappedByteBuffer bB = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            Spliterator<DispLine> ls = new LineSpliterator(bB, 0, bB.limit() - 1);
            StreamSupport.stream(ls, true)
                    .filter(dl -> TELEPHONE_NUMBER_PATTERN.matcher(dl.getLine()).find())
                    .forEachOrdered(System.out::println);
        } catch (IOException e) {
            LOG.error("IO Exception has been caught!", e);
        }
        printEmptyLines(1);
    }

    private static void getByTypeTest() {

        System.out.println("Task 6. Gets elements with a specified type");

        List<A> itemList = asList(new A("A1"), new B("B1"), new C("C1"), new A("A2"));

        itemList.stream()
                .flatMap(selectItem(B.class))
                .forEach(item -> System.out.println("Item : " + item.getClass().getSimpleName()));

        printEmptyLines(1);
    }

    private static void cartesianProductTest() {

        System.out.println("Task 7. Cartesian product test");

        List<List<String>> input = asList(
                asList("a", "b", "c"),
                asList("x", "y"),
                asList("1", "2", "3")
        );

        Supplier<Stream<String>> sup = input.stream()
                //Stream<List<String>>
                .map(listik -> (Supplier<Stream<String>>) listik::stream)
                //Stream<Supplier<Stream<String>>>
                .reduce((sup1, sup2) -> () -> sup1.get().flatMap(e1 -> sup2.get().map(e2 -> e1 + e2)))
                //Optional<Supplier<Stream<String>>>
                .orElse(() -> Stream.of(""));

        sup.get().forEach(System.out::println);

        printEmptyLines(1);
    }

    private static Function<Integer, Integer> factorialFunction;

    private static void factorialTest() {

        System.out.println("Task 8. Factorial calculation test (recursive lambda)");

        factorialFunction = (val) -> val == 0 ? 1 : val * factorialFunction.apply(val - 1);

        System.out.println("Factorial result: " + calculate(4, factorialFunction));
        printEmptyLines(1);
    }

    private static void personListTest() {

        System.out.println("Task 9. Person list test");

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

        System.out.println("Task 10. Parallel sort speed test");

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
                .collect(Collectors.toList());
        System.out.println("ArrayList has been sorted (parallel method). Required time: " + (System.nanoTime() - start2) / 1000 + " ms.");

        //Sequential sort
        long start1 = System.nanoTime();
        Collections.sort(intList1);
        System.out.println("ArrayList has been sorted (sequential method). Required time: " + (System.nanoTime() - start1) / 1000 + " ms.");

        printEmptyLines(1);
    }

    private static String readLines(Path path) {
        try {
            long matchCount = Files.readAllLines(path).stream()
                    .filter(line -> TELEPHONE_NUMBER_PATTERN.matcher(line).find())
                    .count();
            return matchCount == 0 ? "" : path + ": " + matchCount;
        } catch (IOException e) {
            LOG.error("IO Exception has been caught!", e);
            return "";
        }
    }

    private static <T, TT> Function<T, Stream<TT>> selectItem(Class<TT> clazz) {
        return e -> clazz.isInstance(e) ? Stream.of(clazz.cast(e)) : null;
    }

    private static <T, R> R calculate(T value, Function<T, R> function) {
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
        basicStreamTest();

        //Test 2
        collectorsTest();

        //Task 3
        fileWalkerTest();

        //Task 4
        simpleGrepTest();

        //Task 5
        customSpliteratorTest();

        //Task 6
        getByTypeTest();

        //Task 7
        cartesianProductTest();

        //Task 8
        factorialTest();

        //Task 9
        personListTest();

        //Task 10
        parallelSortTest();
    }
}

class A {

    private String name;

    public A(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class B extends A {

    public B(String name) {
        super(name);
    }
}

class C extends B {

    public C(String name) {
        super(name);
    }
}
