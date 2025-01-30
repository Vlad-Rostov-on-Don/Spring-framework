package com.example.app;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

@Component
public class CacheProxy {
    private final Source cacheSours;

    public CacheProxy(Source cacheSours) {
        this.cacheSours = cacheSours;
    }

    public <T> T cache(T target) {
        Class<?> clazz = target.getClass();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                new CachingHandler(target, cacheSours));
    }

    private static class CachingHandler implements InvocationHandler {
        private final Object target;
        private final Source cache;

        public CachingHandler(Object target, Source cache) {
            this.target = target;
            this.cache = cache;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Cacheable.class)) {
                String key = method.getName() + args[0];
                if (cache.contains(key)) {
                    return cache.load(key, List.class);
                } else {
                    Object result = method.invoke(target, args);
                    cache.save(key, result);
                    return result;
                }
            }
            return method.invoke(target, args);
        }
    }
}
