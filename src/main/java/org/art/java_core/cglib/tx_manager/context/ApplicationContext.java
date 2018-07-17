package org.art.java_core.cglib.tx_manager.context;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.java_core.cglib.tx_manager.annotations.Transactional;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple application context implementation.
 * All business services should be registered in the context before
 * they are used.
 * <p>
 * App context is responsible for providing a transactional execution
 * of service methods marked by {@link Transactional} annotation.
 */
public class ApplicationContext {

    public static final Logger LOG = LogManager.getLogger(ApplicationContext.class);

    private Map<String, Object> services = new ConcurrentHashMap<>();

    private Lock lockManager = new ReentrantLock();

    public ApplicationContext() {
        LOG.debug("ApplicationContext: context initialization");
    }

    public void registerService(String serviceName, Class<?> serviceClass) {
        Method[] methods = serviceClass.getDeclaredMethods();
        Set<String> targetMethodNames = new HashSet<>();
        for (Method method : methods) {
            Transactional targetAnnotation = method.getAnnotation(Transactional.class);
            if (targetAnnotation != null) {
                targetMethodNames.add(method.getName());
            }
        }
        try {
            if (!targetMethodNames.isEmpty()) {
                //Service contains 'transactional' methods
                LOG.debug("Service: {}, transactional methods: {}", serviceClass, targetMethodNames);
                Object proxyService = processTransactional(serviceClass, targetMethodNames);
                services.put(serviceName, proxyService);
            } else {
                Object serviceInstance = serviceClass.newInstance();
                services.put(serviceName, serviceInstance);
            }
        } catch (Exception e) {
            throw new RuntimeException("Service registration failed!");
        }
    }

    private Object processTransactional(Class<?> serviceClass, Set<String> targetMethods) {
        //CGLib proxy creation
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(serviceClass);
        enhancer.setInterfaces(serviceClass.getInterfaces());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (targetMethods.contains(method.getName())) {
                Object result = null;
                try {
                    lockManager.lock();
                    LOG.debug("Begin transaction... Service: {}, method: {}", serviceClass.getName(), method.getName());
                    result = proxy.invokeSuper(obj, args);
                    LOG.debug("Commit transaction... Service: {}, method: {}", serviceClass.getName(), method.getName());
                } catch (Exception e) {
                    LOG.debug("Rollback transaction... Service: {}, method: {}", serviceClass.getName(), method.getName());
                } finally {
                    lockManager.unlock();
                }
                return result;
            } else {
                return proxy.invokeSuper(obj, args);
            }
        });
        return enhancer.create();
    }

    public <T> T getService(String serviceName, Class<T> serviceClass) {
        @SuppressWarnings("unchecked")
        T service = (T) services.get(serviceName);
        return service;
    }

    public void close() {
        LOG.debug("ApplicationContext: closing context");
        services.clear();
    }
}
