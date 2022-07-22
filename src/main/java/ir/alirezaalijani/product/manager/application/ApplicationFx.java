package ir.alirezaalijani.product.manager.application;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;


public class ApplicationFx extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext=new SpringApplicationBuilder(SpringDesktopApplication.class).run();
    }

    @Override
    public  void stop() {
        System.out.println("Bye...");
        applicationContext.close();
    }

    @Override
    public void start(Stage stage){
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }


}
