package org.art.java_core.collections;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BinaryOperator;

/**
 * Java Collections Framework API (simple tasks implementations).
 */
public class CollectionsTest {

    private static Random random = new Random(System.currentTimeMillis());

    private static void markFilterTask() {

        System.out.println("Task 1. Marks filter");

        //Initial data
        int numOfMarks = 25;

        List<Integer> markList = new ArrayList<>(numOfMarks);
        for (int i = 0; i < numOfMarks; i++) {
            markList.add(random.nextInt(9) + 2);
        }

        System.out.println("Initial list of marks:");
        System.out.println(markList);

        CustomPredicate predicate = new CustomPredicate();
//        markList.removeIf(predicate);
        markList.removeIf(i -> i < 6);

        System.out.println("Modified list of marks:");
        System.out.println(markList);
        printEmptyLines(1);
    }

    private static void maxMarkFinderTask() {

        System.out.println("Task 2. Max mark finder");

        //Initial data
        int numOfMarks = 25;

        List<Integer> markList = new ArrayList<>(numOfMarks);
        for (int i = 0; i < numOfMarks; i++) {
            markList.add(random.nextInt(9) + 2);
        }

        System.out.println("Initial list of marks:");
        System.out.println(markList);

        BinaryOperator<Integer> markCompareFunction = (mark1, mark2) -> mark1 >= mark2 ? mark1 : mark2;
        int maxMark = markList.stream().reduce(0, markCompareFunction);

        System.out.println("Max mark is: " + maxMark);
        printEmptyLines(1);
    }

    private static void reverseNamesTask() {

        System.out.println("Task 3. Names reverser (via iterator)");

        //Initial data
        String[] names = new String[]{"Maria", "Artsiom", "Andrey", "Alex", "Pasha", "Sveta"};

        List<String> namesList = Arrays.asList(names);
        List<String> reversedNamesList = new ArrayList<>(names.length);
        ListIterator<String> iter = namesList.listIterator(namesList.size());
        while (iter.hasPrevious()) {
            reversedNamesList.add(iter.previous());
        }

        System.out.println("Initial list of names:");
        System.out.println(namesList);
        System.out.println("Reversed list of names:");
        System.out.println(reversedNamesList);
        printEmptyLines(1);
    }

    private static void wordCounterTask() {

        System.out.println("Task 4. Words counter");

        //Initial data
        String words = "Hello world cool world sun very but very cool but sun sun sun that is me";

        String[] textArr = words.split(" ");
        List<String> wordsList = Arrays.asList(textArr);

        System.out.println("Initial list of words:");
        System.out.println(wordsList);

        Map<String, Integer> wordsStatistics = new HashMap<>();
        wordsList.forEach((word) -> {
            if (wordsStatistics.containsKey(word)) {
                wordsStatistics.replace(word, wordsStatistics.get(word) + 1);
            } else {
                wordsStatistics.put(word, 1);
            }
        });

        System.out.println("Word statistics:");
        System.out.println(wordsStatistics);
        printEmptyLines(1);
    }

    private static void digitsReverserTask() {

        System.out.println("Task 5. Number reverser");

        //Initial data
        int number = 4445522;

        System.out.println("Initial number: " + number);
        String strNumber = Integer.toString(number);
        char[] digits = strNumber.toCharArray();
        LinkedList<Character> charList = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (char digit : digits) {
            charList.push(digit);
        }
        for (char digit : digits) {
            sb.append(charList.pop());
        }

        System.out.println("Reversed number: " + new Integer(sb.toString()));
        printEmptyLines(1);
    }

    private static void positiveNegativeSortingTask() {

        System.out.println("Task 6. Differentiates positive and negative numbers on both sides from zero " +
                "in LINKED number list (without making the copy of the list!)");

        //Initial data
        List<Integer> list = Arrays.asList(2, -3, 5, 9, -4, 3, -6, 0, -2, 9, -31, -12, 83);

        LinkedList<Integer> linkedList = new LinkedList<>(list);

        System.out.println("Initial list: " + linkedList);

        List<Integer> orderedList = differentiate(linkedList);

        System.out.println("Differentiated list: " + orderedList);
        printEmptyLines(1);
    }

    private static void customIntStatisticSetTask() {

        System.out.println("Task 7. Custom order statistic set");

        //Initial data
        List<Integer> numList = Arrays.asList(3, 5, 1, 8, 0, 4, 2, 9, 23, 34, -2, 7, -38, 110);
        int minOrder = 5;
        int maxOrder = 4;

        OrderStatisticSet orderStatisticSet = new OrderStatisticSet(numList);

        System.out.println("List of numbers: " + numList);
        System.out.println(orderStatisticSet);

        System.out.println(maxOrder + " max number is: " + orderStatisticSet.getMax(maxOrder));
        System.out.println(minOrder + " min number is: " + orderStatisticSet.getMin(minOrder));
        printEmptyLines(1);
    }

    private static void lastNamesCheckerTask() {

        System.out.println("Task 8. Last names checker (checks if last names in the map are unique)");

        //Initial data
        Map<String, String> persons = new HashMap<>();
        persons.put("Hal", "Perkins");
        persons.put("Marty", "Stepp");
        persons.put("Stuart", "Reges");
        persons.put("Jessica", "Miller");
        persons.put("Amanda", "Camp");

        System.out.println("Persons: " + persons);
        System.out.println("Result: " + checkPersons(persons));
        printEmptyLines(1);
    }

    private static void polynomComposerTask() {

        System.out.println("Task 9. Polynom composer");

        //Initial data
        Map<Integer, Integer> polynomMap1 = new TreeMap<>();
        polynomMap1.put(2, 3);
        polynomMap1.put(1, 4);
        polynomMap1.put(0, 2);
        polynomMap1.put(5, -2);
        Map<Integer, Integer> polynomMap2 = new TreeMap<>();
        polynomMap2.put(3, 2);
        polynomMap2.put(2, 4);
        polynomMap2.put(4, 1);
        polynomMap2.put(1, 6);
        polynomMap2.put(0, 2);

        System.out.println("Polynom map 1 (pow - multiplier): " + polynomMap1);
        System.out.println("Polynom map 2 (pow - multiplier): " + polynomMap2);
        printPolynom(composePolynoms(polynomMap1, polynomMap2));
        printEmptyLines(1);
    }

    private static void customCacheTest() {

        System.out.println("Task 10. Custom cache (LinkedHashMap extension) - 'Last recently added policy'");

        //Initial data
        int cacheCapacity = 3;      //Effectively final variable

        LinkedHashMap<String, String> cache = new LinkedHashMap<String, String>() {

            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > cacheCapacity;
            }
        };

        cache.put("Key 1", "Item 1");
        cache.put("Key 2", "Item 2");
        cache.put("Key 3", "Item 3");
        cache.put("Key 4", "Item 4");

        System.out.println(cache);      //The first item is discarded!
        printEmptyLines(1);
    }

    private static void enumMapTest() {

        System.out.println("Task 11. EnumMap test (Physical phase transitions simulation)");

        System.out.printf("Transition from %s to %s is %s%n", Phase.SOLID, Phase.LIQUID,
                Phase.Transition.from(Phase.SOLID, Phase.LIQUID));
        System.out.printf("Transition from %s to %s is %s%n", Phase.GAS, Phase.LIQUID,
                Phase.Transition.from(Phase.GAS, Phase.LIQUID));
        System.out.printf("Transition from %s to %s is %s%n", Phase.GAS, Phase.SOLID,
                Phase.Transition.from(Phase.GAS, Phase.SOLID));
        printEmptyLines(1);
    }

    private static List<Integer> differentiate(LinkedList<Integer> list) {
        int iterPosition = 0;
        int iterationNum = list.size();
        Integer next;
        ListIterator<Integer> listIter = list.listIterator(iterPosition);
        for (int i = 0; i < iterationNum; i++) {
            next = listIter.next();
            iterPosition++;
            if (next > 0) {
                list.addFirst(next);
                listIter = list.listIterator(iterPosition);
                listIter.next();
                listIter.remove();
            } else if (next < 0) {
                list.addLast(next);
                iterPosition--;
                listIter = list.listIterator(iterPosition);
                listIter.next();
                listIter.remove();
            }
        }
        return list;
    }

    private static boolean checkPersons(Map<String, String> persons) {
        Set<String> set = new HashSet<>();
        for (Map.Entry entry : persons.entrySet()) {
            set.add((String) entry.getValue());
        }
        int setSize = set.size();
        return persons.size() == setSize;
    }

    private static Map<Integer, Integer> composePolynoms(Map<Integer, Integer> polynomMap1, Map<Integer, Integer> polynomMap2) {
        Map<Integer, Integer> biggerPolynom;
        Map<Integer, Integer> smallerPolynom;
        if (polynomMap1.size() > polynomMap2.size()) {
            biggerPolynom = polynomMap1;
            smallerPolynom = polynomMap2;
        } else {
            biggerPolynom = polynomMap2;
            smallerPolynom = polynomMap1;
        }
        for (Map.Entry entry : smallerPolynom.entrySet()) {
            Integer key = (Integer) entry.getKey();
            if (biggerPolynom.containsKey(key)) {
                biggerPolynom.put(key, ((Integer) entry.getValue() + biggerPolynom.get(key)));
            } else {
                biggerPolynom.put(key, (Integer) entry.getValue());
            }
        }
        return biggerPolynom;
    }

    private static void printPolynom(Map<Integer, Integer> map) {
        int mulCounter = 0;
        Map<Integer, Integer> treeMap = new TreeMap<>(Comparator.reverseOrder());
        treeMap.putAll(map);
        System.out.println("Polynom:");
        for (Map.Entry entry : treeMap.entrySet()) {
            if (Math.abs((Integer) entry.getKey()) > 1) {
                System.out.print((Math.abs((Integer) entry.getValue()) > 1 ? entry.getValue() : "") + "x^" + entry.getKey());
                mulCounter++;
            } else if ((Integer) entry.getKey() == 1) {
                System.out.print(entry.getValue() + "x");
                mulCounter++;
            } else {
                System.out.print(entry.getValue());
                mulCounter++;
            }
            if (mulCounter < map.size()) {
                System.out.print(" + ");
            }
        }

    }

    private static void printEmptyLines(int numOfLines) {
        for (int i = 0; i < numOfLines; i++) {
            System.out.println();
        }
    }

    private static void concurrentIterationTask() {

        System.out.println("Task 12. ConcurrentLinkedQueue Example with concurrent iteration (weak consistent iterator)");

        //Initial data
        Queue<String> strings = new ConcurrentLinkedQueue<>();
        strings.offer("One");
        strings.offer("Two");
        strings.offer("Three");
        strings.offer("Four");
        strings.offer("Five");

        System.out.println("Initial strings queue: " + strings);

        //Multiple iterators mutate queue at the same time
        Iterator<String> it1 = strings.iterator();
        Iterator<String> it2 = strings.iterator();

        if (it1.hasNext()) {
            it1.next();
            it1.next();
            it1.remove();
        }

        while (it1.hasNext()) {
            String str = it1.next();
            System.out.println("Iterator 1: str - " + str);
        }
        strings.offer("Six");
        while (it2.hasNext()) {
            String str = it2.next();
            System.out.println("Iterator 2: str - " + str);
        }

        System.out.println("Strings queue after modification: " + strings);
    }

    public static void main(String[] args) {

        //Task 1
        markFilterTask();

        //Task 2
        maxMarkFinderTask();

        //Task 3
        reverseNamesTask();

        //Task 4
        wordCounterTask();

        //Task 5
        digitsReverserTask();

        //Task 6
        positiveNegativeSortingTask();

        //Task 7
        customIntStatisticSetTask();

        //Task 8
        lastNamesCheckerTask();

        //Task 9
        polynomComposerTask();

        //Task 10
        customCacheTest();

        //Task 11
        enumMapTest();

        //Task 12
        concurrentIterationTask();
    }
}
