package ir.alirezaalijani.product.manager.application.ui.service.observer.content;


import ir.alirezaalijani.product.manager.application.ui.service.observer.Observer;

public abstract class ObserverContentFactory<T> {
    protected abstract Observer<T> contentFactory(String observerId);
    protected abstract void remove(String observerId);

    public final Observer<T> getContent(String observerId){
        return contentFactory(observerId);
    }
}
