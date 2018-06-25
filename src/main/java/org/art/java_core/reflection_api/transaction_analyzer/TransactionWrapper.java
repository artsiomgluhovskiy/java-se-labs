package org.art.java_core.reflection_api.transaction_analyzer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Finds class methods annotated with the custom
 * 'Transaction' annotation and 'wraps' each of them
 * into a separate dummy transaction.
 */
public class TransactionWrapper {

    private static final Logger LOG = LogManager.getLogger(TransactionWrapper.class);

    private static List<Method> retrieveAnnotatedMethods(Class clazz) {
        ArrayList<Method> targetMethods = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(Transaction.class)) {
                targetMethods.add(methods[i]);
            }
        }
        return targetMethods;
    }

    public static void processTransaction(Class clazz) {
        Object newClassInstance;
        try {
            newClassInstance = clazz.newInstance();
            int methodArg = 0;
            List<Method> targetMethods = retrieveAnnotatedMethods(clazz);
            for (Method method : targetMethods) {
                Transaction transaction = method.getAnnotation(Transaction.class);
                System.out.println(transaction.startTransactionMessage());
                method.setAccessible(true);
                method.invoke(newClassInstance, methodArg++);
                System.out.println(transaction.endTransactionMessage() + "\n");
            }
        } catch (IllegalAccessException e1) {
            LOG.error("IllegalAccessException has been caught...", e1);
        } catch (InstantiationException e2) {
            LOG.error("InstantiationException has been caught...", e2);
        } catch (InvocationTargetException e3) {
            LOG.error("InvocationTargetException has been caught...", e3);
        }
    }
}
