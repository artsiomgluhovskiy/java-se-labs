package org.art.java_core.lab_additional.metaspace_out_of_memory;

import java.io.IOException;

/**
 * Dynamic class loading util the memory limit of
 * metaspace is reached (the memory size of metaspace
 * should be limited preliminary).
 */
public class MetaspaceQuiz {

    public static void main(String[] args) throws IOException, InterruptedException {

        Class clazz = MetaspaceQuiz.class;
        CustomClassLoader loader = new CustomClassLoader();
        int amountOfLoadedClasses = 0;

        Long[] longArray = new Long[20];
        for(int i = 0; i < longArray.length; i++) {
            longArray[i] = Long.MAX_VALUE;
        }

        String newClassName1 = "MetaspaceQuiz";
        String fileName = ".\\src\\main\\java\\org\\art\\java_core\\lab_additional\\metaspace_out_of_memory\\MetaspaceQuiz.java";
        byte[] buffer = ClassLoaderUtil.loadByteCode(fileName);

        try{
            //New class name generating
            for(int i = 1; i < Integer.MAX_VALUE; i++) {
                String newClassName = "___" + String.format("%010d", i);
                byte[] newClassData = new String(buffer, "latin1").replaceAll("MetaspaceQuiz", newClassName)
                        .getBytes("latin1");
                Class<?> myNewClass = loader._defineClass(clazz.getName().replace(clazz.getSimpleName(), newClassName), newClassData, 0, newClassData.length);
                amountOfLoadedClasses++;
            }
            System.out.println(amountOfLoadedClasses);
        } catch(Error err) {
            System.out.println("Amount of loaded classes: " + amountOfLoadedClasses);
            throw err;
        }
    }
}
