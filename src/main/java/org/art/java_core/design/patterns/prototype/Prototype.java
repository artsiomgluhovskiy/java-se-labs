package org.art.java_core.design.patterns.prototype;

/**
 * Prototype pattern (from GoF) - simple code example.
 * Human prototyping simulation.
 */
public class Prototype {

    public static void main(String[] args) {

        Human original = new Human(34, "Potter");
        Human copy = original.copy();
        System.out.println(original);
        System.out.println(copy);

        HumanFactory factory = new HumanFactory(copy);
        Human newHuman = factory.makeCopy();
        System.out.println(newHuman);

        factory.setPrototype(new Human(23, "Harry"));
        Human newHuman2 = factory.makeCopy();
        System.out.println(newHuman2);
    }
}

interface Copyable {

    Object copy();
}

class Human implements Copyable {

    private int age;
    private String name;

    Human(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public Human copy() {
        return new Human(age, name);
    }

    @Override
    public String toString() {
        return "Human{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

class HumanFactory {

    private Human human;

    HumanFactory(Human human) {
        this.human = human;
    }

    void setPrototype(Human human) {
        this.human = human;
    }

    Human makeCopy() {
        return human.copy();
    }
}
