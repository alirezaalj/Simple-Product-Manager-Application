package ir.alirezaalijani.product.manager.application.ui.util.alert;

import ir.alirezaalijani.product.manager.application.ui.util.ViewPath;
import ir.alirezaalijani.product.manager.application.ui.util.alert.alertController.QuAlertAnserController;
import ir.alirezaalijani.product.manager.application.ui.util.alert.alertController.QuAlertController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QuAlert {
    private boolean result=true;

    private QuAlert(){

    }

    private static class Helper{
        private static final QuAlert instance=new QuAlert();
    }

    public static QuAlert getInstance(){
        return Helper.instance;
    }

    public boolean show(String title,String message){
        QuAlertController controller = null;
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPath.QU_ALERT);
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
             controller=loader.getController();
            controller.setTitle(title+" ?");
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

    public QuAlertResponse showQu(String title,String message){
        QuAlertAnserController controller = null;
        QuAlertResponse response=new QuAlertResponse("",false);
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPath.QU_ANSWER_ALERT);
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            controller=loader.getController();
            controller.setTitle(title+" ?");
            controller.setMessage("  "+message);
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (controller!=null){
                result=controller.isResult();
                String myMessage = controller.getMyMessage();
                response.setAnser(result);
                response.setMessage(myMessage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
