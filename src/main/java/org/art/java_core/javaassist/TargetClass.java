package org.art.java_core.javaassist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TargetClass {

    public static void main(String[] args) throws IOException {
        fetch("https://www.google.com");
        fetch("https://www.yahoo.com");
    }

    private static void fetch(String address) throws IOException {
        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String inputLing;
            StringBuilder sb = new StringBuilder();
            while((inputLing = in.readLine()) != null) {
                sb.append(inputLing);
            }
            System.out.println("Content size: " + sb.length());
        }
    }
}
