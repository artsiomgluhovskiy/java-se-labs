package org.art.java_core.design.patterns.factory_method;

/**
 * Factory method (from GoF) - simple code example.
 * Watch factory simulation.
 */
public class FactoryMethod {

    public static void main(String[] args) {

        //1 variant (without parameter)
        WatchMakerIF maker1 = new RomeWatchMaker();
        WatchIF watch = maker1.createWatch();
        watch.showTime();

        //2 variant (with parameter)
        WatchMakerIF maker2 = createWatchMaker(WatchType.DIGITAL);
        WatchIF watch1 = maker2.createWatch();
        watch1.showTime();
    }

    static WatchMakerIF createWatchMaker(WatchType watchType) {
        switch (watchType) {
            case DIGITAL:
                return new DigitalWatchMaker();
            case ROME:
                return new RomeWatchMaker();
            default:
                throw new IllegalArgumentException("Unknown watch type!");
        }
    }
}

enum WatchType {

    DIGITAL, ROME
}

interface WatchIF {

    void showTime();
}

class DigitalWatch implements WatchIF {

    public void showTime() {
        System.out.println("Digital type watch: 22:23:25");
    }
}

class RomeWatch implements WatchIF {

    public void showTime() {
        System.out.println("Rome type watch: VII-XX");
    }
}

interface WatchMakerIF {

    WatchIF createWatch();
}

class DigitalWatchMaker implements WatchMakerIF {

    public WatchIF createWatch() {
        return new DigitalWatch();
    }
}

class RomeWatchMaker implements WatchMakerIF {

    public WatchIF createWatch() {
        return new RomeWatch();
    }
}