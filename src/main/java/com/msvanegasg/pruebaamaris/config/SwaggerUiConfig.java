package com.msvanegasg.pruebaamaris.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerUiConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirecciones manuales con prefijo /dev para evitar errores en API Gateway
        registry.addRedirectViewController("/swagger-ui.html", "/dev/swagger-ui/index.html");
        registry.addRedirectViewController("/swagger-ui/", "/dev/swagger-ui/index.html");
        registry.addRedirectViewController("/swagger-ui", "/dev/swagger-ui/index.html");
    }
}
