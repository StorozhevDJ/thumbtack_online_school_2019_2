package com.lineate.threads;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Task12 {

    /**
     * Написать свой класс, аналогичный ConcurrentHashMap , используя  ReadWriteLock.
     * Будет ли эта реализация хуже ConcurrentHashMap, и если да, то почему
     */
    Map<Object, Object> hm;
    ReadWriteLock rwl = new ReentrantReadWriteLock();

    public Task12() {
        this.hm = new HashMap();
    }


    public int size() {
        rwl.readLock().lock();
        try {
            return hm.size();
        } finally {
            rwl.readLock().unlock();
        }
    }

    public boolean isEmpty() {
        rwl.readLock().lock();
        try {
            return hm.isEmpty();
        } finally {
            rwl.readLock().unlock();
        }
    }

    public Object get(Object key) {
        rwl.readLock().lock();
        try {
            return hm.get(key);
        } finally {
            rwl.readLock().unlock();
        }
    }

    public boolean containsKey(Object key) {
        rwl.readLock().lock();
        try {
            return hm.containsKey(key);
        } finally {
            rwl.readLock().unlock();
        }
    }

    public Object put(Object key, Object value) {
        rwl.writeLock().lock();
        try {
            return hm.put(key, value);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public void putAll(Map m) {
        rwl.writeLock().lock();
        try {
            hm.putAll(m);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public Object remove(Object key) {
        rwl.writeLock().lock();
        try {
            return hm.remove(key);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public void clear() {
        rwl.writeLock().lock();
        try {
            hm.clear();
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public boolean containsValue(Object value) {
        rwl.readLock().lock();
        try {
            return hm.containsValue(value);
        } finally {
            rwl.readLock().unlock();
        }
    }

    public Set keySet() {
        rwl.writeLock().lock();
        try {
            return hm.keySet();
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public Collection values() {
        rwl.readLock().lock();
        try {
            return hm.values();
        } finally {
            rwl.readLock().unlock();
        }
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        rwl.readLock().lock();
        try {
            return hm.entrySet();
        } finally {
            rwl.readLock().unlock();
        }
    }

    public Object getOrDefault(Object key, Object defaultValue) {
        rwl.readLock().lock();
        try {
            return hm.getOrDefault(key, defaultValue);
        } finally {
            rwl.readLock().unlock();
        }
    }

    public Object putIfAbsent(Object key, Object value) {
        rwl.writeLock().lock();
        try {
            return hm.putIfAbsent(key, value);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public boolean remove(Object key, Object value) {
        rwl.writeLock().lock();
        try {
            return hm.remove(key, value);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public boolean replace(Object key, Object oldValue, Object newValue) {
        rwl.writeLock().lock();
        try {
            return hm.replace(key, oldValue, newValue);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public Object replace(Object key, Object value) {
        rwl.writeLock().lock();
        try {
            return hm.replace(key, value);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public Object computeIfAbsent(Object key, Function mappingFunction) {
        rwl.writeLock().lock();
        try {
            return hm.computeIfAbsent(key, mappingFunction);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public Object computeIfPresent(Object key, BiFunction remappingFunction) {
        rwl.writeLock().lock();
        try {
            return hm.computeIfPresent(key, remappingFunction);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public Object compute(Object key, BiFunction remappingFunction) {
        rwl.writeLock().lock();
        try {
            return hm.compute(key, remappingFunction);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public Object merge(Object key, Object value, BiFunction remappingFunction) {
        rwl.writeLock().lock();
        try {
            return hm.merge(key, value, remappingFunction);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public void forEach(BiConsumer action) {
        rwl.writeLock().lock();
        try {
            hm.forEach(action);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    public void replaceAll(BiFunction function) {
        rwl.writeLock().lock();
        try {
            hm.replaceAll(function);
        } finally {
            rwl.writeLock().unlock();
        }
    }


}
