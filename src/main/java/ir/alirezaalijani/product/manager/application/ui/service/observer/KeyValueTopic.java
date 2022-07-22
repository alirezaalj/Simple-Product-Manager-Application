package ir.alirezaalijani.product.manager.application.ui.service.observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KeyValueTopic<T> implements Subject<T> {

    private final Map<String,Observer<T>> observers;
    private T data;
    private boolean changed;

    public KeyValueTopic() {
        observers = new HashMap<>();
    }

    @Override
    public void register(String key ,Observer<T> obj) {

        if (!observers.containsKey(key)){
            observers.put(key,obj);
        }
    }

    @Override
    public void unregister(String key,Observer<T> obj) {
        observers.remove(key);
    }

    @Override
    public void notifyObservers() {
        Set<Observer<T>> observersLocal;
        if (!changed)
            return;
        observersLocal = new HashSet<>(this.observers.values());
        this.changed = false;
        observersLocal.parallelStream().forEach(Observer::update);
//        observersLocal.forEach(Observer::update);
    }

    @Override
    public T getUpdate(Observer<T> obj) {
        return this.data;
    }

    @Override
    public synchronized void push(T data) {
        this.data = data;
        this.changed = true;
        notifyObservers();
    }
}