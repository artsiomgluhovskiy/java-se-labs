package org.art.java_core.lab_additional.external_process_invocation;

import java.io.IOException;

public class Launch {
    public static void main(String[] args) throws IOException {

//        String command = "ping www.codejava.net";
//        try {
//            Process process = Runtime.getRuntime().exec(command);
//
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            reader.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String command = "calc.exe";
        Process process = Runtime.getRuntime().exec(command);

//        String commandArray[] = {"cmd", "/c", "dir", "C:\\Program Files"};
//        Process process = Runtime.getRuntime().exec(commandArray);
    }
}
