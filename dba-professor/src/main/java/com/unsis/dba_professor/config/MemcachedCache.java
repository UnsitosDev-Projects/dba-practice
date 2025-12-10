package com.unsis.dba_professor.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;

public class MemcachedCache implements Cache {
    
    private final String name;
    private final MemcachedClient memcachedClient;
    private final int expiration;
    
    public MemcachedCache(String name, MemcachedClient memcachedClient, int expiration) {
        this.name = name;
        this.memcachedClient = memcachedClient;
        this.expiration = expiration;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Object getNativeCache() {
        return this.memcachedClient;
    }
    
    @Override
    public ValueWrapper get(Object key) {
        String cacheKey = key.toString();
        Object value = memcachedClient.get(cacheKey);
        return value != null ? new SimpleValueWrapper(value) : null;
    }
    
    @Override
    public <T> T get(Object key, Class<T> type) {
        String cacheKey = key.toString();
        Object value = memcachedClient.get(cacheKey);
        if (value != null && type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
        }
        return (T) value;
    }
    
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        String cacheKey = key.toString();
        Object value = memcachedClient.get(cacheKey);
        if (value != null) {
            return (T) value;
        }
        
        try {
            T newValue = valueLoader.call();
            put(key, newValue);
            return newValue;
        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e);
        }
    }
    
    @Override
    public void put(Object key, Object value) {
        if (value != null) {
            String cacheKey = key.toString();
            memcachedClient.set(cacheKey, expiration, value);
        }
    }
    
    @Override
    public void evict(Object key) {
        String cacheKey = key.toString();
        memcachedClient.delete(cacheKey);
    }
    
    @Override
    public void clear() {
        memcachedClient.flush();
    }
}
