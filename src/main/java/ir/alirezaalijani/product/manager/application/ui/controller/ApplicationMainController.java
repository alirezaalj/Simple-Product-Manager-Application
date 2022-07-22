package ir.alirezaalijani.product.manager.application.ui.controller;

import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import ir.alirezaalijani.product.manager.application.ui.service.ViewManager;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.FxmlContent;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.ViewLoadType;
import ir.alirezaalijani.product.manager.application.ui.util.ViewPath;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class ApplicationMainController {

    private final ViewManager viewManager;

    @FXML
    public BorderPane rootPane;

    @FXML
    private ImageView btnClose;

    public ApplicationMainController(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            ContextHolder.getInstance().registerObjectBean("RootPane",BorderPane.class,this.rootPane);
            loadMainView();
        });

    }

    @FXML
    void onClose(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void onMaxMinimize(MouseEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    void onMinimize(MouseEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.setIconified(true);
    }

    private void loadMainView() {
        FxmlContent content = viewManager.getView(ViewPath.ViewMain, ViewLoadType.STAGE);
        this.rootPane.setCenter(viewManager.pushView(content)
                        .getContentPane());
    }
}
