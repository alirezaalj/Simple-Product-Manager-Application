package ir.alirezaalijani.product.manager.application.ui.service.observer;

public interface Subject<T> {
    void register(String key,Observer<T> obj);
    void unregister(String key,Observer<T> obj);
    void notifyObservers();
    T getUpdate(Observer<T> obj);
    void push(T data);
}
