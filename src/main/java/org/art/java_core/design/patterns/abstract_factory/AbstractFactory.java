package org.art.java_core.design.patterns.abstract_factory;

/**
 * Abstract Factory pattern (from GoF) - code example.
 * Simple Device factory simulation.
 */
public class AbstractFactory {

    public static void main(String[] args) {

        //Asus devices
        DeviceFactory asusFactory = getDeviceFactory(DeviceManufacturer.ASUS);
        IMouse asusMouse = asusFactory.getMouse();
        IKeyboard asusKeyboard = asusFactory.getKeyboard();
        ITouchpad asusTouchpad = asusFactory.getTouchpad();

        asusMouse.click();
        asusKeyboard.print("Asus");
        asusKeyboard.print("!\n");
        asusKeyboard.println("Devices");
        asusTouchpad.track(20, 55);

        //Sony devices
        DeviceFactory sonyFactory = getDeviceFactory(DeviceManufacturer.SONY);
        IMouse sonyMouse = sonyFactory.getMouse();
        IKeyboard sonyKeyboard = sonyFactory.getKeyboard();
        ITouchpad sonyTouchpad = sonyFactory.getTouchpad();

        sonyMouse.scroll(10);
        sonyMouse.dbClick();
        sonyKeyboard.print("Sony");
        sonyKeyboard.print("!\n");
        sonyKeyboard.println("Devices");
        sonyTouchpad.track(10, 35);
    }

    private static DeviceFactory getDeviceFactory(DeviceManufacturer manufacturer) {
        switch (manufacturer) {
            case ASUS:
                return new AsusDeviceFactory();
            case SONY:
                return new SonyDeviceFactory();
            default:
                System.out.println("Unknown manufacturer! Default device factory - Sony");
                return new SonyDeviceFactory();
        }
    }
}

enum DeviceManufacturer {
    SONY, ASUS
}

//Mouse abstraction
interface IMouse {

    void click();

    void dbClick();

    void scroll(int direction);
}

//Keyboard abstraction
interface IKeyboard {

    void print(String str);

    void println(String str);
}

//Touchpad abstraction
interface ITouchpad {

    void track(int deltaX, int deltaY);
}

//Sony mouse implementation
class SonyMouse implements IMouse {

    public void click() {
        System.out.println("Sony Mouse: Click");
    }

    public void dbClick() {
        System.out.println("Sony Mouse: Double click");
    }

    public void scroll(int direction) {
        if (direction > 0) {
            System.out.println("Sony Mouse: Scroll up");
        } else if (direction < 0) {
            System.out.println("Sony Mouse: Scroll down");
        } else {
            System.out.println("Sony Mouse: No scroll");
        }
    }
}

//Sony mouse implementation
class SonyKeyboard implements IKeyboard {

    public void print(String str) {
        System.out.print("Sony Keyboard: Print line - " + str);
    }

    public void println(String str) {
        System.out.println("Sony Keyboard: Print line and go to the next line - " + str);
    }
}

//Sony touchpad implementation
class SonyTouchpad implements ITouchpad {

    public void track(int deltaX, int deltaY) {
        int s = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        System.out.println("Sony Touchpad: Move on " + s + " px");
    }
}

//Asus mouse implementation
class AsusMouse implements IMouse {

    public void click() {
        System.out.println("Asus Mouse: Click");
    }

    public void dbClick() {
        System.out.println("Asus Mouse: Double click");
    }

    public void scroll(int direction) {
        if (direction > 0) {
            System.out.println("Asus Mouse: Scroll up");
        } else if (direction < 0) {
            System.out.println("Asus Mouse: Scroll down");
        } else {
            System.out.println("Asus Mouse: No scroll");
        }
    }
}

//Asus keyboard implementation
class AsusKeyboard implements IKeyboard {

    public void print(String str) {
        System.out.print("Asus Keyboard: Print line - " + str);
    }

    public void println(String str) {
        System.out.println("Asus Keyboard: Print line and go to the next line - " + str);
    }
}

//Asus touchpad implementation
class AsusTouchpad implements ITouchpad {

    public void track(int deltaX, int deltaY) {
        int s = (int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        System.out.println("Asus Touchpad: Move on " + s + " px");
    }
}

//Device factory abstraction
interface DeviceFactory {

    IMouse getMouse();

    IKeyboard getKeyboard();

    ITouchpad getTouchpad();
}

//Sony device factory
class SonyDeviceFactory implements DeviceFactory {

    public IMouse getMouse() {
        return new SonyMouse();
    }

    public IKeyboard getKeyboard() {
        return new SonyKeyboard();
    }

    public ITouchpad getTouchpad() {
        return new SonyTouchpad();
    }
}

//Asus device factory
class AsusDeviceFactory implements DeviceFactory {

    public IMouse getMouse() {
        return new AsusMouse();
    }

    public IKeyboard getKeyboard() {
        return new AsusKeyboard();
    }

    public ITouchpad getTouchpad() {
        return new AsusTouchpad();
    }
}


