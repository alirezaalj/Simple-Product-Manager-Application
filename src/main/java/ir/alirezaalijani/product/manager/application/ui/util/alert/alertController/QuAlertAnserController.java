package ir.alirezaalijani.product.manager.application.ui.util.alert.alertController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;

public class QuAlertAnserController {
    private String title;
    private String message;
    private String myMessage;
    private boolean result=false;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXButton btnNo;

    @FXML
    private JFXTextArea txtMessage;

    @FXML
    private JFXTextField txtMyMessage;

    @FXML
    private JFXButton btnYes;


    @FXML
    void initialize() {
        Platform.runLater(()->{
            Toolkit.getDefaultToolkit().beep();
            lblTitle.setText(this.title);
            txtMessage.setText(this.message);
        });
        txtMessage.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                result=true;
                setMyMessage(txtMyMessage.getText().trim());
                Stage stage=(Stage) btnYes.getScene().getWindow();
                stage.close();
            }
        });
        btnNo.setOnAction(event ->{
            result=false;
            setMyMessage(txtMyMessage.getText().trim());
            Stage stage=(Stage) btnNo.getScene().getWindow();
            stage.close();
        } );
        btnYes.setOnAction(event ->{
            result=true;
            setMyMessage(txtMyMessage.getText().trim());
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

    public String getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }
}
