package ir.alirezaalijani.product.manager.application;

import ir.alirezaalijani.product.manager.application.config.ContextHolder;
import ir.alirezaalijani.product.manager.application.ui.util.ResourcePath;
import ir.alirezaalijani.product.manager.application.ui.util.ViewPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import ir.alirezaalijani.product.manager.application.ApplicationFx.StageReadyEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {


    private final Resource resource;
    private final ApplicationContext applicationContext;

    public StageInitializer(@Value("classpath:/static") Resource resource,
                            ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        ContextHolder.getInstance().setApplicationContext(applicationContext);
        this.resource=resource;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            ResourcePath.path=resource.getURL().toString();

            if (ResourcePath.path!=null){
                FXMLLoader fxmlLoader = new FXMLLoader(new URL(ResourcePath.path+"/ApplicationMain.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Parent parent = fxmlLoader.load();
                Stage stage = event.getStage();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle("Application");
                stage.setMaximized(false);
                stage.getIcons().add(new Image(ViewPath.imgApplicationIcon));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setOnCloseRequest(e -> stage.close());
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
