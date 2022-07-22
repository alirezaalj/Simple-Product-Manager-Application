package ir.alirezaalijani.product.manager.application.ui.util.alert;

import ir.alirezaalijani.product.manager.application.ui.util.ViewPath;
import ir.alirezaalijani.product.manager.application.ui.util.alert.alertController.InfoAlertController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InfoAlert {
    private boolean result=false;

    private InfoAlert(){

    }
    private static class Helper{
        private static final InfoAlert instance=new InfoAlert();
    }
    public static InfoAlert getInstance(){
        return Helper.instance;
    }

    public boolean show(String title,String message){
        InfoAlertController controller = null;
        try{
            FXMLLoader loader = new FXMLLoader(ViewPath.INFO_ALERT);
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            controller=loader.getController();
            controller.setTitle(title);
            controller.setMessage("  "+message);
            stage.setTitle("Info Alert");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();

        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (controller!=null)
            result=controller.isResult();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean showW(String title,String message){
        InfoAlertController controller = null;
        try{
            FXMLLoader loader = new FXMLLoader(ViewPath.INFO_ALERT_W);
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            controller=loader.getController();
            controller.setTitle(title);
            controller.setMessage("  "+message);
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (controller!=null)
            result=controller.isResult();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
