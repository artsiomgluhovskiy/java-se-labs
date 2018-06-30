package org.art.java_core.design.patterns.bridge;

/**
 * Bridge pattern (from GoF) - code example.
 * Simple TV remote control system simulation
 * with TV models hierarchies.
 */
public class Bridge {

    public static void main(String[] args) {

        SimpleRemoteControl simpleRemote = new SimpleRemoteControl(new Sony());
        simpleRemote.on();
        simpleRemote.setChannel(5);
        simpleRemote.off();

        AdvancedRemoteControl advancedRemote = new AdvancedRemoteControl(new AdvancedPhilips());
        advancedRemote.on();
        advancedRemote.setChannel(4);
        advancedRemote.nextChannel();
        advancedRemote.previousChannel();
        advancedRemote.off();
    }
}

//'Implementator' interface
interface TV {

    void on();

    void off();

    void tuneChannel(int channel);
}

//'Advanced Implementator' interface
interface AdvancedTV extends TV {

    void nextChannel();

    void previousChannel();
}

//Concrete 'Sony' implementation
class Sony implements TV {

    private int currentChannel;

    @Override
    public void on() {
        System.out.println("Sony: 'on' command");
    }

    @Override
    public void off() {
        System.out.println("Sony: 'off' command");
    }

    @Override
    public void tuneChannel(int channel) {
        currentChannel = channel;
        System.out.println("Sony: tune channel " + currentChannel);
    }
}

//Advanced 'Sony' implementation
class AdvancedSony implements AdvancedTV {

    private int currentChannel;

    @Override
    public void on() {
        System.out.println("Advanced Sony: 'on' command");
    }

    @Override
    public void off() {
        System.out.println("Advanced Sony: 'off' command");
    }

    @Override
    public void tuneChannel(int channel) {
        currentChannel = channel;
        System.out.println("Advanced Sony: tune channel " + currentChannel);
    }

    @Override
    public void nextChannel() {
        currentChannel++;
        System.out.println("Advanced Sony: next channel " + currentChannel);
    }

    @Override
    public void previousChannel() {
        if (currentChannel > 1) {
            currentChannel--;
        }
        System.out.println("Advanced Sony: previous channel " + currentChannel);
    }
}

//Concrete 'Philips' implementation
class Philips implements TV {

    private int currentChannel;

    @Override
    public void on() {
        System.out.println("Philips: 'on' command");
    }

    @Override
    public void off() {
        System.out.println("Philips: 'off' command");
    }

    @Override
    public void tuneChannel(int channel) {
        this.currentChannel = channel;
        System.out.println("Philips: tune channel " + channel);
    }
}

//Advanced 'Philips' implementation
class AdvancedPhilips implements AdvancedTV {

    private int currentChannel;

    @Override
    public void on() {
        System.out.println("Advanced Philips: 'on' command");
    }

    @Override
    public void off() {
        System.out.println("Advanced Philips: 'off' command");
    }

    @Override
    public void tuneChannel(int channel) {
        currentChannel = channel;
        System.out.println("Advanced Philips: tune channel " + currentChannel);
    }

    @Override
    public void nextChannel() {
        currentChannel++;
        System.out.println("Advanced Philips: next channel " + currentChannel);
    }

    @Override
    public void previousChannel() {
        if (currentChannel > 1) {
            currentChannel--;
        }
        System.out.println("Advanced Philips: previous channel " + currentChannel);
    }
}

//Abstraction (can be inherited)
class SimpleRemoteControl {

    private TV tv;

    public SimpleRemoteControl(TV tv) {
        this.tv = tv;
    }

    void on() {
        tv.on();
    }

    void off() {
        tv.off();
    }

    void setChannel(int channel) {
        tv.tuneChannel(channel);
    }

    public TV getTv() {
        return tv;
    }
}

//Advanced abstraction
class AdvancedRemoteControl extends SimpleRemoteControl {

    public AdvancedRemoteControl(AdvancedTV tv) {
        super(tv);
    }

    void nextChannel() {
        ((AdvancedTV) getTv()).nextChannel();
    }

    void previousChannel() {
        ((AdvancedTV) getTv()).previousChannel();
    }
}

