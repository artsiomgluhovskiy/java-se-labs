package org.art.java_core.design.patterns.proxy;

/**
 * Proxy pattern (from GoF) - simple code example.
 * Lazy/eager image loading simulation.
 */
public class Proxy {

    public static void main(String[] args) {

        //RealImage leads to image loading after instantiation even without
        Image img1 = new RealImage("D:/images/my.jpg");
        //Eager image loading
//        img1.display();

        //ProxyImage doesn't lead to image loading after instantiation
        Image img2 = new ProxyImage("D:/images/my.jpg");
        //Lazy image loading
        img2.display();

    }
}

interface Image {
    void display();
}

class RealImage implements Image {

    private String file;

    public RealImage(String file) {
        this.file = file;
        load();
    }

    private void load() {
        System.out.println("Image file loading: " + file);
    }

    @Override
    public void display() {
        System.out.println("Image view: " + file);
    }
}

class ProxyImage implements Image {

    private String file;

    private RealImage image;

    public ProxyImage(String file) {
        this.file = file;
    }

    @Override
    public void display() {
        if (image == null) {
            image = new RealImage(file);
        }
        image.display();
    }
}