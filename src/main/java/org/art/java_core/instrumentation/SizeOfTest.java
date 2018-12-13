package org.art.java_core.instrumentation;

import gnu.trove.set.hash.TIntHashSet;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.HashSet;
import java.util.Set;

public class SizeOfTest {

    public static void main(String[] args) {

        /* Instrumentation API Tests (Approximation) Is not appropriate for deep object size measurement! */

        //Java type objects (wrappers) size test
        System.out.println("Byte size: " + ObjectSizeFetcher.getObjectSize((byte) 12));
        System.out.println("Short size: " + ObjectSizeFetcher.getObjectSize((short) 12));
        System.out.println("Integer size: " + ObjectSizeFetcher.getObjectSize(1));
        System.out.println("Long size: " + ObjectSizeFetcher.getObjectSize(1L));
        String testString = "@017.net.il ododofomvmsldfs.d,mfm;sdlflmsdlfs;df;ms;dlfm;sdmf;smdf;lsmd;flms;dmfo,sdfom";
        System.out.println("String size: " + ObjectSizeFetcher.getObjectSize(testString));
        System.out.println("char[] size: " + ObjectSizeFetcher.getObjectSize(testString.toCharArray()));

        //Set<String> object size test
        String dataFilePath = "/files/sizeof_test/data.csv";
        Set<String> strings = ObjectGenerator.generateStringSetFromFile(dataFilePath);
        long objectSize;

        //Size of Set object itself
        objectSize = ObjectSizeFetcher.getObjectSize(strings);

        //Size of Set items
        for (String s : strings) {
            objectSize += ObjectSizeFetcher.getObjectSize(s);
            objectSize += ObjectSizeFetcher.getObjectSize(s.toCharArray());
        }
        System.out.printf("Set of Strings size test. Number of strings in set: %s, object size: %s%n", strings.size(), objectSize);

        /* 'jol' Size Tests */

        //Shallow size of an object
        System.out.println(ClassLayout.parseClass(TestClass.class).toPrintable());

        //Deed size of an object (brief and detailed)
        System.out.println(GraphLayout.parseInstance(new TestClass()).toFootprint());
        System.out.println(GraphLayout.parseInstance(new TestClass()).toPrintable());

        System.out.println(GraphLayout.parseInstance(strings).toFootprint());

        //HashSet<Integer> test
        Set<Integer> hashSet = ObjectGenerator.toIntHashSet(strings);
        System.out.println("HashSet<Integer> capacity: " + hashSet.size());
        System.out.println(GraphLayout.parseInstance(hashSet).toFootprint());

        // Trove collection test
        TIntHashSet tIntHashSet = ObjectGenerator.toTIntHashSet(strings);
        System.out.println(GraphLayout.parseInstance(tIntHashSet).toFootprint());

    }
}

class TestClass {
    private int val1;
    private Integer val4 = new Integer(25);
    private Set<String> strings = new HashSet<>();

    TestClass() {
        strings.add("One");
        strings.add("Two");
        strings.add("Three");
    }
}
