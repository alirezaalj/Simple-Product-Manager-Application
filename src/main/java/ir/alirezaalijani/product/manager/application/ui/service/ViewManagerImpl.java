package ir.alirezaalijani.product.manager.application.ui.service;

import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.FxmlContent;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.ViewContentFactoryProducer;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.ViewLoadType;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service("ViewManager")
public class ViewManagerImpl implements ViewManager {
    private final Map<String, FxmlContent> fxmlPages;
    private final Deque<FxmlContent> deque;
    public ViewManagerImpl() {
        fxmlPages=new HashMap<>();
        deque=new ArrayDeque<>();
    }

    @Override
    public FxmlContent pushView(FxmlContent content) {
        deque.push(content);
        return content;
    }

    @Override
    public FxmlContent pushView(URL viewPath, ViewLoadType type) {
        return pushView(getView(viewPath,type));
    }

    @Override
    public FxmlContent popView() {
        if (deque.size()>1){
            deque.pop();
            return deque.peek();
        }
       return deque.peek();
    }

    @Override
    public FxmlContent popAndRemoveView() {
        if (deque.size()>1){
            FxmlContent remove =deque.pop();
            removeView(remove.getLoader().getLocation());
            return deque.peek();
        }
        return deque.peek();
    }

    @Override
    public FxmlContent getView(URL viewPath, ViewLoadType type) {
        if (Objects.isNull(viewPath)) return null;
        final String key=viewPath.toString();
        FxmlContent view = fxmlPages.get(key);
        if (Objects.isNull(view)){
            view = ViewContentFactoryProducer.getFactory(type).getContent(viewPath);
            fxmlPages.put(key,view);
        }
        return view;
    }

    @Override
    public FxmlContent reloadView(URL viewPath, ViewLoadType type) {
        if (Objects.isNull(viewPath)) return null;
        final String key = viewPath.toString();
        FxmlContent view = ViewContentFactoryProducer.getFactory(type).getContent(viewPath);
        fxmlPages.put(key,view);
        return view;
    }

    @Override
    public boolean removeView(URL viewPath) {
        return fxmlPages.remove(viewPath.toString()) != null;
    }

    @Override
    public boolean clearViews() {
        fxmlPages.clear();
        return true;
    }
}
