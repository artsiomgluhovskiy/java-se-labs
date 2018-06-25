package org.art.java_core.design.patterns.proxy;

public class ProxyPatt {
    public static void main(String[] args) {

        //RealImage leads to image loading after instantiation even without
        //display method invocation
        Image img = new RealImage("D:/images/my.jpg");
//        img.display();

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

    String file;

    public RealImage(String file) {
        this.file = file;
        load();
    }

    public void load() {
        System.out.println("File loading: " + file);
    }

    @Override
    public void display() {
        System.out.println("Image view " + file);
    }
}

class ProxyImage implements Image {

    String file;
    RealImage image;

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