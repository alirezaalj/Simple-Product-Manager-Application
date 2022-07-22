package ir.alirezaalijani.product.manager.application.ui.util.alert.alertController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class QuAlertController {
    private String title;
    private String message;
    private boolean result=false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXButton btnNo;

    @FXML
    private JFXTextArea txtMessage;

    @FXML
    private JFXButton btnYes;

    @FXML
    void initialize() {
        Platform.runLater(()->{
            Toolkit.getDefaultToolkit().beep();
            lblTitle.setText(this.title);
            txtMessage.setText(this.message);
        });

        btnNo.setOnAction(event ->{
            result=false;
            Stage stage=(Stage) btnNo.getScene().getWindow();
            stage.close();
        } );
        btnYes.setOnAction(event ->{
            result=true;
            Stage stage=(Stage) btnYes.getScene().getWindow();
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
