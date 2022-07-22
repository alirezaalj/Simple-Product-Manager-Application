package ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FxmlControlView implements FxmlContent{
    private final FXMLLoader fxmlLoader;
    private final Control contentControl;

    public FxmlControlView(FXMLLoader fxmlLoader, Control contentControl) {
        this.fxmlLoader = fxmlLoader;
        this.contentControl = contentControl;
    }

    @Override
    public FXMLLoader getLoader() {
        return this.fxmlLoader;
    }

    @Override
    public Stage getStage() {
        return null;
    }

    @Override
    public Pane getContentPane() {
        return null;
    }

    @Override
    public Control getContentControl() {
        return this.contentControl;
    }
}
