package org.art.java_core.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.java_core.io.entity.Computer;
import org.art.java_core.io.entity.Notebook;
import org.art.java_core.io.entity.Touchpad;

import java.io.*;

/**
 * Simple Serialization API code examples.
 */
public class SerializationTest {

    private static final Logger LOG = LogManager.getLogger(SerializationTest.class);

    public static void main(String[] args) {

        //Task 1
        System.out.println("Task 1. Simple serialization test");

        String filePath1 = "C:\\Users\\ahlukhouski\\IdeaProjects\\JavaLabs\\java-labs\\src\\main\\resources\\files\\io\\object-out-1.txt";

        Computer computer = new Computer("Intel Core i7", 8192, 3.6, false);
        System.out.println("Before serializing:\n" + computer);

        //Serializing into the file
        serializeToFile(computer, filePath1);

        //Deserializing from the file
        Computer computerCopy = deserializeFromFile(Computer.class, filePath1);

        System.out.println("After serializing:\n" + computerCopy);
        printEmptyLines(1);

        //Task 2
        System.out.println("Task 2. Object serialization with transient field");

        String filePath2 = "C:\\Users\\ahlukhouski\\IdeaProjects\\JavaLabs\\java-labs\\src\\main\\resources\\files\\io\\object-out-2.txt";

        Touchpad touchpad = new Touchpad("Synaptics", 2.65, 1001);
        Notebook notebook = new Notebook("Intel Core i5", 8192, touchpad);
        System.out.println("Before serializing:\n" + notebook);

        //Serializing into the file
        serializeToFile(notebook, filePath2);

        //Deserializing from the file
        Notebook notebookCopy = deserializeFromFile(Notebook.class, filePath2);

        System.out.println("After serializing:\n" + notebookCopy);
        printEmptyLines(1);
    }

    public static <T> void serializeToFile(T object, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(fileName)))) {
            out.writeObject(object);
        } catch (IOException e) {
            LOG.error("Exception has been caught during object [{}] serializing!", object, e);
        }
    }

    public static <T> T deserializeFromFile(Class<T> type, String fileName) {
        T obj = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(fileName)))) {
            obj = type.cast(in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            LOG.error("Exception has been caught during object serializing with type {}!", type, e);
        }
        return obj;
    }

    private static void printEmptyLines(int numOfLines) {
        for (int i = 0; i < numOfLines; i++) {
            System.out.println();
        }
    }
}
