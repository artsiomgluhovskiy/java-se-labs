package org.art.java_core.reflection_api.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Invocation Handler API (simple code example).
 */
public class ProxyTest {

    public static void main(String[] args) {

        String[] messages = new String[]{"Hello", "!", "My name", "is", "Artem", "!"};

        HelloInvoker helloInvoker = new HelloInvoker();
        System.out.println("Hello Invoker message: " + helloInvoker.invoke(messages));

        GoodByeInvoker goodByeInvoker = new GoodByeInvoker();
        System.out.println("Good Bye Invoker message: " + goodByeInvoker.invoke(messages));

        Invoker customProxy = (Invoker) Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),
                new Class[]{Invoker.class},
                new CustomInvocationHandler());
        System.out.println("Proxy Invoker message: " + customProxy.invoke(messages));
    }

    static class CustomInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            StringBuilder sb = new StringBuilder();
            String[] messages = (String[]) args[0];
            for (int i = 0; i < messages.length; i++) {
                sb.append(messages[i]).append(' ');
            }
            return sb.toString();
        }
    }
}

interface Invoker {
    String invoke(String... messages);
}

class HelloInvoker implements Invoker {

    public String invoke(String... messages) {
        return Arrays.toString(messages);
    }
}

class GoodByeInvoker implements Invoker {

    public String invoke(String... messages) {
        return Arrays.toString(messages);
    }
}
