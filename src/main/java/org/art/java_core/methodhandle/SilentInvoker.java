package org.art.java_core.methodhandle;

import java.lang.invoke.MethodType;
import java.util.concurrent.Callable;

/**
 * Wraps action with {@link Callable} functional type,
 * which throws checked {@link Exception}, and provides "silent"
 * method invocation avoiding compilation error (e.g. during
 * data processing in {@link java.util.stream.Stream}).
 */
@FunctionalInterface
public interface SilentInvoker {

    MethodType MY_SIGNATURE = MethodType.methodType(Object.class, Callable.class);

    /**
     * Invokes action silently.
     *
     * @param action to invoke
     * @param <V> return type of the action result
     * @return action result
     */
    <V> V invoke(Callable<V> action);
}
