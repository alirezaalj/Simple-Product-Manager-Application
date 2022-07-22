package ir.alirezaalijani.product.manager.application;

import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;

@EnableScheduling
@SpringBootApplication
public class SpringDesktopApplication implements CommandLineRunner {

	public static void main(String[] args) {
//		SpringApplication.run(SpringDesktopApplication.class, args);
		Application.launch(ApplicationFx.class,args);
	}

	@Override
	public void run(String... args){
		File dataDir=new File("data/");
		boolean res = dataDir.mkdir();
		if (!res){
			System.out.println("cannot create data dir");
		}
	}
}
