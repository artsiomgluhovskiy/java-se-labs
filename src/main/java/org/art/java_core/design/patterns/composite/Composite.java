package org.art.java_core.design.patterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite pattern (from GoF) - simple code example.
 * ShapeIF composition drawing simulation.
 */
public class Composite {
    public static void main(String[] args) {

        ShapeIF square = new Square();
        ShapeIF triangle = new Triangle();
        ShapeIF circle = new Circle();

        CompositeShape composite = new CompositeShape();
        composite.addComponent(square);
        composite.addComponent(triangle);
        composite.addComponent(circle);

        composite.draw();
    }
}

interface ShapeIF {

    void draw();
}

class Square implements ShapeIF {

    @Override
    public void draw() {
        System.out.println("Square");
    }
}

class Triangle implements ShapeIF {

    @Override
    public void draw() {
        System.out.println("Triangle");
    }
}

class Circle implements ShapeIF {

    @Override
    public void draw() {
        System.out.println("Circle");
    }
}

class CompositeShape implements ShapeIF {

    private List<ShapeIF> components = new ArrayList<>();

    public void addComponent(ShapeIF component) {
        components.add(component);
    }

    public void removeComponent(ShapeIF component) {
        components.remove(component);
    }

    @Override
    public void draw() {
        components.forEach(ShapeIF::draw);
    }
}