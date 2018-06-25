package org.art.java_core.design.patterns.decorator;

/**
 * Decorator pattern (from GoF) - simple code example.
 * Simple text string decorator implementation.
 */
public class Decorator {

    public static void main(String[] args) {

        PrinterIF printer =
                new QuoteDecorator(
                        new LeftBracketDecorator(
                                new RightBracketDecorator(
                                        new Printer("Hello"))));
        printer.print();
    }
}

interface PrinterIF {

    void print();
}

class Printer implements PrinterIF {

    private String value;

    public Printer(String value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }
}

abstract class TextStringDecoratorBase implements PrinterIF {

    private PrinterIF component;

    public TextStringDecoratorBase(PrinterIF component) {
        this.component = component;
    }

    @Override
    public void print() {
        component.print();
    }
}

class QuoteDecorator extends TextStringDecoratorBase {

    public QuoteDecorator(PrinterIF component) {
        super(component);
    }

    @Override
    public void print() {
        System.out.print("\"");
        super.print();
        System.out.print("\"");
    }
}

class LeftBracketDecorator extends TextStringDecoratorBase {

    public LeftBracketDecorator(PrinterIF component) {
        super(component);
    }

    @Override
    public void print() {
        System.out.print("[");
        super.print();
    }
}

class RightBracketDecorator extends TextStringDecoratorBase {

    public RightBracketDecorator(PrinterIF component) {
        super(component);
    }

    @Override
    public void print() {
        super.print();
        System.out.print("]");
    }
}


