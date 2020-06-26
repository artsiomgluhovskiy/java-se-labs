package org.art.java_core.methodhandle;

import java.lang.invoke.*;
import java.util.concurrent.Callable;

/**
 * Utility class, which provides method for "silent" invocation
 * (without throwing checked exception) avoiding compilation error.
 * Can be useful in processing data with {@link java.util.stream.Stream}.
 */
public class InvocationUtils {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private static final SilentInvoker SILENT_INVOKER;

    static {
        try {
            MethodHandle implMethod = LOOKUP.findVirtual(Callable.class, "call", MethodType.methodType(Object.class));
            CallSite callSite = LambdaMetafactory.metafactory(
                    LOOKUP,
                    "invoke",
                    MethodType.methodType(SilentInvoker.class),
                    SilentInvoker.MY_SIGNATURE,
                    implMethod,
                    SilentInvoker.MY_SIGNATURE);
            SILENT_INVOKER = (SilentInvoker) callSite.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException("Cannot init invoker!");
        }
    }

    private InvocationUtils() {
        throw new UnsupportedOperationException("Utility class!");
    }

    public static <V> V callUnchecked(Callable<V> action) {
        return SILENT_INVOKER.invoke(action);
    }
}
