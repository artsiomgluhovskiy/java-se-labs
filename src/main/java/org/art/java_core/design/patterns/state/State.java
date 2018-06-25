package org.art.java_core.design.patterns.state;

/**
 * State pattern (from GoF) - simple code examples:
 *  1) Radio station simulation (with external state triggering logic by);
 *  2) Human activity simulation (with internal state triggering logic).
 */
public class State {

    public static void main(String[] args) {

        StationIF dfm = new RadioDFM();
        Radio radio = new Radio();
        radio.setStation(dfm);
        for (int i = 0; i < 10; i++) {
            radio.play();
            radio.nextStation();
        }

        Human human = new Human();
        human.setState(new Work());
        for (int i = 0; i < 10; i++) {
            human.doSomething();
        }
    }
}

/* Radio station simulation */

interface StationIF {
    void play();
}

class Radio7 implements StationIF {

    @Override
    public void play() {
        System.out.println("Radio7 is playing...");
    }
}

class RadioDFM implements StationIF {

    @Override
    public void play() {
        System.out.println("RadioDFM is playing...");
    }
}

class RadioTimesFM implements StationIF {

    @Override
    public void play() {
        System.out.println("RadioTimesFM is playing...");
    }
}

/*Context with current state, that can be triggered
  from outside world*/
class Radio {

    private StationIF station;

    void setStation(StationIF station) {
        this.station = station;
    }

    void nextStation() {
        if (station instanceof Radio7) {
            setStation(new RadioDFM());
        } else if (station instanceof RadioDFM) {
            setStation(new RadioTimesFM());
        } else if (station instanceof RadioTimesFM) {
            setStation(new Radio7());
        }
    }

    void play() {
        station.play();
    }
}

/* Human activity simulation */

/*Context with current state with 'self triggering'*/
class Human {

    private ActivityIF state;

    void setState(ActivityIF s) {
        this.state = s;
    }

    void doSomething() {
        state.doSomething(this);
    }
}

interface ActivityIF {
    void doSomething(Human context);
}

class Work implements ActivityIF {

    @Override
    public void doSomething(Human context) {
        System.out.println("Working activity...");
        context.setState(new WeekEnd());
    }
}

class WeekEnd implements ActivityIF {

    private int count = 0;

    @Override
    public void doSomething(Human context) {
        if (count < 3) {
            System.out.println("Relax activity...");
            count++;
        } else {
            context.setState(new Work());
        }
    }
}