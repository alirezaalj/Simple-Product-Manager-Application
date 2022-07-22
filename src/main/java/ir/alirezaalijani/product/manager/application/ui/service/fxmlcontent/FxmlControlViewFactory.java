package ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent;

import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;


@Slf4j
public class FxmlControlViewFactory extends ViewContentFactory {

    @Override
    protected FxmlContent contentFactory(URL url) {
        FXMLLoader loader=new FXMLLoader(url);
        loader.setControllerFactory(ContextHolder.getInstance().getApplicationContext()::getBean);
        Control control = null;
        try {
            control = loader.load();
            Parent root = loader.getRoot();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("loading fxml content {} fail.",url);
        }
        return new FxmlControlView(loader,control);
    }
}
