package ir.alirezaalijani.product.manager.application.ui.util.alert.alertController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoAlertController {

    private String title;
    private String message;
    private boolean result=true;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXButton btnOk;

    @FXML
    private JFXTextArea txtMessage;

    @FXML
    public void onEnterFormAction(KeyEvent KV) {
        if (KV.getCode().equals(KeyCode.ENTER)) {
            result=true;
            Stage stage=(Stage) btnOk.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void initialize() {
        Platform.runLater(()->{
            Toolkit.getDefaultToolkit().beep();
            lblTitle.setText(this.title);
            txtMessage.setText(this.message);
        });

        btnOk.setOnAction(event ->{
            result=true;
            Stage stage=(Stage) btnOk.getScene().getWindow();
            stage.close();
        } );
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
