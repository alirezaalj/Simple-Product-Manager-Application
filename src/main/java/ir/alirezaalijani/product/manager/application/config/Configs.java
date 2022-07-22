package ir.alirezaalijani.product.manager.application.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


@Configuration
public class Configs {

    @Bean("modelMapper")
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
