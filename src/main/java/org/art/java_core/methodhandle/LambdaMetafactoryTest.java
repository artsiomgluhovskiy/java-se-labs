package org.art.java_core.methodhandle;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.art.java_core.methodhandle.InvocationUtils.callunchecked;

/**
 * Simple example, which demonstrates the usage of {@link java.lang.invoke.LambdaMetafactory} API
 * with dynamic method generation. Provides the ability to implement methods on the fly by means
 * of delegation of invocation to already implemented methods. More preferable to be used in
 * frameworks, because of better performance compared to Reflection API methods.
 */
public class LambdaMetafactoryTest {

    public static void main(String[] args) {

        List<String> strings = Arrays.asList(
                "https://github.com",
                "https://www.oracle.com",
                "https://www.netflix.com",
                "https://www.amazon.com");

        List<URL> urls = strings.stream()
                //Throws checked MalformedURLException
                .map((str) -> callunchecked(() -> new URL(str)))
                .collect(toList());

        System.out.println("Generated URLs: " + urls);
    }
}
