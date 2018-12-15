package org.art.java_core.javaassist.generator;

import javassist.ClassPool;
import javassist.CtClass;

import javax.tools.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;

/**
 * Generates and loads classes dynamically into Metaspace until it fills up.
 *
 * Before test launching max metaspace size should be restricted:
 * e.g. -XX:MaxMetaspaceSize=128m
 */
public class ClassLoadingTest {

    private static final String JAVA_SOURCE_1 = "/files/javaassist/SimpleClass.java";
    private static final String JAVA_SOURCE_2 = "/files/javaassist/ComplexClass.java";

    private static int LOADED_CLASS_NUMBER = 0;

    public static void main(String[] args) {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        String metaSpaceSize = runtimeMxBean.getInputArguments()
                .stream()
                .filter(prop -> prop.startsWith("-XX:MaxMetaspaceSize"))
        .findFirst()
        .orElse("Not found!");
        System.out.println("Max metaspace size: " + metaSpaceSize);

//        runClassLoadingTest(JAVA_SOURCE_1);
        runClassLoadingTest(JAVA_SOURCE_2);
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
        } finally {
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
        String initClassName;
        String currentClassName;
        try (InputStream in = ClassLoadingTest.class.getResourceAsStream(classFilePath)) {
            //Load initial class
            CtClass clazz = classPool.makeClass(in);
            clazz.toClass();
            currentClassName = clazz.getName();
            initClassName = currentClassName;
            Class.forName(clazz.getName());
            clazz.defrost();
            LOADED_CLASS_NUMBER++;

            //Generate and load new classes based on initial class
            int i = 0;
            while (i < Integer.MAX_VALUE) {
                clazz = classPool.get(currentClassName);
                currentClassName = initClassName + "_" + i;
                clazz.setName(currentClassName);
                clazz.toClass();
                Class.forName(currentClassName);
                clazz.defrost();
                LOADED_CLASS_NUMBER++;
                i++;
            }
        }
    }
}
