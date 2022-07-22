package ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FxmlPaneView implements FxmlContent {
    private final FXMLLoader fxmlLoader;
    private final Stage stage;
    private final Pane contentPane;

    public FxmlPaneView(FXMLLoader fxmlLoader, Stage stage, Pane content) {
        this.fxmlLoader = fxmlLoader;
        this.stage = stage;
        this.contentPane =content;
    }

    @Override
    public FXMLLoader getLoader() {
        return fxmlLoader;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public Pane getContentPane() {
        return this.contentPane;
    }

    @Override
    public Control getContentControl() {
        return null;
    }

}
