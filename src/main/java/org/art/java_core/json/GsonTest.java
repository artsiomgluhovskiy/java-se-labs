package org.art.java_core.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.art.java_core.json.model.Interests;
import org.art.java_core.json.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class GsonTest {

    //'hobbies' property as a string
    private static final String person1 =
            "{\n" +
            "  \"firstName\": \"Lukas\",\n" +
            "  \"lastName\": \"Brandon\",\n" +
            "  \"age\": \"32\",\n" +
            "  \"interests\": {\n" +
            "    \"literature\": \"science\",\n" +
            "    \"hobbies\": \"skiing\"\n" +
            "  }\n" +
            "}";

    //'hobbies' property as a list of strings
    private static final String person2 =
            "{\n" +
            "  \"firstName\": \"Josh\",\n" +
            "  \"lastName\": \"Wilson\",\n" +
            "  \"age\": \"25\",\n" +
            "  \"interests\": {\n" +
            "    \"literature\": \"novel\",\n" +
            "    \"hobbies\": [\n" +
            "      \"football\",\n" +
            "      \"traveling\",\n" +
            "      \"swimming\"\n" +
            "    ]\n" +
            "  }\n" +
            "}";

    @Test
    @DisplayName("Deserialization test (hobbies as a string - without deserializer)")
    void test0() {
        Gson gson = new Gson();
        assertThrows(JsonSyntaxException.class, () -> gson.fromJson(person1, Person.class));
    }

    @Test
    @DisplayName("Deserialization test (hobbies as a list of strings - without deserializer)")
    void test1() {
        Gson gson = new Gson();
        Person person = gson.fromJson(person2, Person.class);
        assertAll(
                () -> assertNotNull(person),
                () -> assertEquals(person.getFirstName(), "Josh"),
                () -> assertEquals(person.getLastName(), "Wilson"),
                () -> assertSame(person.getAge(), 25),
                () -> assertNotNull(person.getInterests()),
                () -> assertEquals(person.getInterests().getLiterature(), "novel"),
                () -> assertSame(person.getInterests().getHobbies().size(), 3)
        );
    }

    @Test
    @DisplayName("Deserialization test (hobbies as a string - with deserializer)")
    void test2() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Interests.class, new InterestsModelDeserializer())
                .create();
        Person person = gson.fromJson(person1, Person.class);
        assertNotNull(person);
        assertAll(
                () -> assertNotNull(person),
                () -> assertEquals(person.getFirstName(), "Lukas"),
                () -> assertEquals(person.getLastName(), "Brandon"),
                () -> assertSame(person.getAge(), 32),
                () -> assertNotNull(person.getInterests()),
                () -> assertEquals(person.getInterests().getLiterature(), "science"),
                () -> assertSame(person.getInterests().getHobbies().size(), 1)
        );
    }

    @Test
    @DisplayName("Deserialization test (hobbies as a list of strings - with deserializer)")
    void test3() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Interests.class, new InterestsModelDeserializer())
                .create();
        Person person = gson.fromJson(person2, Person.class);
        assertNotNull(person);
        assertAll(
                () -> assertNotNull(person),
                () -> assertEquals(person.getFirstName(), "Josh"),
                () -> assertEquals(person.getLastName(), "Wilson"),
                () -> assertSame(person.getAge(), 25),
                () -> assertNotNull(person.getInterests()),
                () -> assertEquals(person.getInterests().getLiterature(), "novel"),
                () -> assertSame(person.getInterests().getHobbies().size(), 3)
        );
    }
}
