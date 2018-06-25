package org.art.java_core.lab_additional.metaspace_out_of_memory;

import java.io.*;

public class ClassLoaderUtil {

    public static byte[] loadByteCode(String filename) throws IOException {

        File file = new File(filename);
        byte[] result = new byte[(int)file.length()];
        InputStream is = new FileInputStream(file);
        is.read(result, 0, result.length);
        is.close();
        return result;
    }

    public static void writeToFile(byte[] byteCode, String filename) throws IOException {
        File file = new File(filename);
        ByteArrayInputStream in = new ByteArrayInputStream(byteCode);
        FileOutputStream out = new FileOutputStream(file);
        byte[] result = new byte[byteCode.length];
        in.read(result, 0, result.length);
        out.write(result);
        in.close();
        out.close();
    }
}
