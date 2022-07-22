package ir.alirezaalijani.product.manager.application.ui.service;

import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.FxmlContent;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.ViewLoadType;

import java.net.URL;

public interface ViewManager {
    FxmlContent pushView(FxmlContent content);
    FxmlContent pushView(URL viewPath, ViewLoadType type);
    FxmlContent popView();
    FxmlContent popAndRemoveView();
    FxmlContent getView(URL viewPath, ViewLoadType type);
    FxmlContent reloadView(URL viewPath, ViewLoadType type);
    boolean removeView(URL viewPath);
    boolean clearViews();
}
