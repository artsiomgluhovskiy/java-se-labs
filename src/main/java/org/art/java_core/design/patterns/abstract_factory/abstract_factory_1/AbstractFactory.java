package org.art.java_core.design.patterns.abstract_factory.abstract_factory_1;

/**
 * Abstract Factory pattern (from GoF) - code example.
 */
public class AbstractFactory {

    public static void main(String[] args) {

        DeviceFactory factory = getFactoryByCountry("RU");
        Mouse m = factory.getMouse();
        Keyboard k = factory.getKeyboard();
        Touchpad t = factory.getTouchpad();

        m.click();
        k.print();
        k.println();
        t.track(10, 35);
    }

    private static DeviceFactory getFactoryByCountry(String lang) {
        switch (lang) {
            case "RU":
                return new RuDeviceFactory();
            case "EN":
                return new EnDeviceFactory();
            default:
                System.out.println("Error!");
        }
        return null;
    }
}

interface Mouse {

    void click();

    void dbclick();

    void scroll(int direction);
}

interface Keyboard {

    void print();

    void println();
}

interface Touchpad {

    void track(int deltaX, int deltaY);
}

interface DeviceFactory {

    Mouse getMouse();

    Keyboard getKeyboard();

    Touchpad getTouchpad();
}

class RuMouse implements Mouse {

    public void click() {
        System.out.println("Mouse click");
    }

    public void dbclick() {
        System.out.println("Double mouse click");
    }

    public void scroll(int direction) {
        if (direction > 0) {
            System.out.println("Scroll up");
        } else if (direction < 0) {
            System.out.println("Scroll down");
        } else {
            System.out.println("No scroll");
        }
    }
}

class RuKeyboard implements Keyboard {

    public void print() {
        System.out.println("Print line");
    }

    public void println() {
        System.out.println("Print line and next line");
    }
}

class RuTouchpad implements Touchpad {

    public void track(int deltaX, int deltaY) {
        int s = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        System.out.println("Move on " + s + " px");
    }
}

class EnMouse implements Mouse {

    public void click() {
        System.out.println("EnMouse click");
    }

    public void dbclick() {
        System.out.println("EnDouble mouse click");
    }

    public void scroll(int direction) {
        if (direction > 0) {
            System.out.println("EnScroll up");
        } else if (direction < 0) {
            System.out.println("EnScroll down");
        } else {
            System.out.println("EnNo scroll");
        }
    }
}

class EnKeyboard implements Keyboard {

    public void print() {
        System.out.println("EnPrint line");
    }

    public void println() {
        System.out.println("EnPrint line and next line");
    }
}

class EnTouchpad implements Touchpad {

    public void track(int deltaX, int deltaY) {
        int s = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        System.out.println("EnMove on " + s + " px");
    }
}

class EnDeviceFactory implements DeviceFactory {

    public Mouse getMouse() {
        return new EnMouse();
    }

    public Keyboard getKeyboard() {
        return new EnKeyboard();
    }

    public Touchpad getTouchpad() {
        return new EnTouchpad();
    }
}

class RuDeviceFactory implements DeviceFactory {

    public Mouse getMouse() {
        return new RuMouse();
    }

    public Keyboard getKeyboard() {
        return new RuKeyboard();
    }

    public Touchpad getTouchpad() {
        return new RuTouchpad();
    }
}


