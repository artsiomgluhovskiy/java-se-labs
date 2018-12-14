package org.art.java_core.javaassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Transforms {@link sun.net.www.protocol.http.HttpURLConnection} class
 * in order to intercept requested URL before opening connection.
 */
public class SimpleClassTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.endsWith("sun/net/www/protocol/http/HttpURLConnection")) {
            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass clazz = classPool.get("sun.net.www.protocol.http.HttpURLConnection");
                for (CtConstructor constructor : clazz.getConstructors()) {
                    constructor.insertAfter("System.out.println(\"URL intercepted: \" + this.getURL());");
                }
                byte[] byteCode = clazz.toBytecode();
                clazz.detach();
                return byteCode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
