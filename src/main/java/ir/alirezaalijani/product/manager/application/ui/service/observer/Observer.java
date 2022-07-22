package ir.alirezaalijani.product.manager.application.ui.service.observer;

public interface Observer<T> {
    void update();
    T getUpdate();
    void setSubject(Subject<T> sub);
}
