package org.art.java_core.javaassist.generator;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;

import javax.tools.*;
import java.io.*;
import java.util.Arrays;

public class ClassLoadingTest {

    private static final String JAVA_SOURCE_1 = "/files/javaassist/SimpleClass.java";
    private static final String JAVA_SOURCE_2 = "/files/javaassist/ComplexClass.java";

    private static int LOADED_CLASS_NUMBER = 0;

    public static void main(String[] args) {
        runClassLoadingTest(JAVA_SOURCE_1);
    }

    private static void runClassLoadingTest(String srcFile) {
        try {
            //Show java file content
            String javaClassFile1 = getJavaClassFileAsString(srcFile);
            System.out.println("Java Class File to compile:\n-->\n" + javaClassFile1);

            //Compile java target class from source
            compileJavaClassFile(srcFile);

            //Generate java classes from target class and load them all
            generateAndLoad(srcFile);
        } catch (Exception exc) {
            exc.printStackTrace();
        } catch (Error err) {
            //Trying to catch OutOfMemoryError:Metaspace
            err.printStackTrace();
            System.out.println("Number of loaded classes: " + LOADED_CLASS_NUMBER);
        }
    }

    private static String getJavaClassFileAsString(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        ClassLoadingTest.class.getResourceAsStream(filePath)))) {
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    private static void compileJavaClassFile(String filePath) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, null, null)) {
            File file = new File(ClassLoadingTest.class.getResource(filePath).toURI());
            Iterable<? extends JavaFileObject> sources = manager.getJavaFileObjectsFromFiles(Arrays.asList(file));
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sources);
            task.call();
        }
    }

    private static void generateAndLoad(String srcFilePath) throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        String classFilePath = srcFilePath.replace(".java", ".class");
        try (InputStream in = ClassLoadingTest.class.getResourceAsStream(classFilePath)) {
            //Load initial class
            CtClass clazz = classPool.makeClass(in);
            Class<?> aClass1 = clazz.toClass();
            String initClassName = clazz.getName();
            aClass1.newInstance();
//            Class<?> aClass1 = Class.forName(clazz.getName());
            LOADED_CLASS_NUMBER++;
            clazz.detach();

            //Generate and load new classes based on initial class
            int i = 0;
            while (i < 2) {
                clazz = classPool.makeClass(initClassName);
                clazz.setName(initClassName + "_" + i);
                clazz.toClass();
                String className = clazz.getName();
                System.out.println("New class name: " + className);
                for (CtConstructor constructor : clazz.getConstructors()) {
                    System.out.println(constructor.getName());
                }
                Class<?> aClass = Class.forName(clazz.getName());
                aClass.newInstance();
                LOADED_CLASS_NUMBER++;
                clazz.detach();
                i++;
            }
        }
    }
}
