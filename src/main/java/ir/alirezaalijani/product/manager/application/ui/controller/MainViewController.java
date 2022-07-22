package ir.alirezaalijani.product.manager.application.ui.controller;

import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import ir.alirezaalijani.product.manager.application.ui.service.ViewManager;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.FxmlContent;
import ir.alirezaalijani.product.manager.application.ui.service.fxmlcontent.ViewLoadType;
import ir.alirezaalijani.product.manager.application.ui.util.ViewPath;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Component;

@Component
public class MainViewController {

    private ViewManager viewManager;
    private BorderPane rootPane;

    @FXML
    private ProgressIndicator mainProgress;

    @FXML
    void initialize() {
        this.rootPane= ContextHolder.getInstance().getBean("RootPane",BorderPane.class);
        this.viewManager= ContextHolder.getInstance().getBean("ViewManager",ViewManager.class);
        this.mainProgress.setVisible(false);
    }

    @FXML
    public void onProducts(MouseEvent mouseEvent) {
        // TODO: open products view to manage products
        FxmlContent content= viewManager.pushView(ViewPath.ViewProducts, ViewLoadType.PANE_PURE);
        rootPane.setCenter(content.getContentPane());
    }

    @FXML
    public void onSellProducts(MouseEvent mouseEvent) {
        // TODO: open selling view to add factor and manage factors
    }

    @FXML
    public void onReportings(MouseEvent mouseEvent) {
        // TODO: open Reporting view to sell reports
    }

    @FXML
    public void onExportings(MouseEvent mouseEvent) {
        // TODO: exporting to exel
    }

    public void onBackuping(MouseEvent mouseEvent) {
        // TODO: backup action
    }
}
