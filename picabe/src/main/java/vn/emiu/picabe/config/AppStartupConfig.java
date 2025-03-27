package vn.emiu.picabe.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppStartupConfig {

    @Bean
    public CommandLineRunner printSwaggerInfo() {
        return args -> System.out.println("Swagger UI available at: http://localhost:8090/swagger-ui/index.html");
    }
}