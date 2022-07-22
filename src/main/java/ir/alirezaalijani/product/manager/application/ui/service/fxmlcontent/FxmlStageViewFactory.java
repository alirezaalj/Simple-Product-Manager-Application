package ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent;

import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class FxmlStageViewFactory extends ViewContentFactory{

    @Override
    protected FxmlContent contentFactory(URL url) {
        FXMLLoader loader=new FXMLLoader(url);
        loader.setControllerFactory(ContextHolder.getInstance().getApplicationContext()::getBean);
        Stage stage = new Stage();
        Pane pane = null;
        try {
            pane = loader.load();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("loading fxml content {} fail.",url);
        }
        return new FxmlPaneView(loader,stage,pane);
    }
}
