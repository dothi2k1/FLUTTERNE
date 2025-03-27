package vn.emiu.picabe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow all origins
        registry.addMapping("/**")
                .allowedOrigins("*") // Add allowed origins here
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
//                .allowCredentials(true)
                .maxAge(3600); // Cache pre-flight request for 1 hour
    }
}
