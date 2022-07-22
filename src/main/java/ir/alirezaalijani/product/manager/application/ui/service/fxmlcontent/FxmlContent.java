package ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface FxmlContent {
    FXMLLoader getLoader();
    Stage getStage();
    Pane getContentPane();
    Control getContentControl();
}
