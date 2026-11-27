package com.barberia.backend.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigCors {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica para todas las rutas de tu API
                        .allowedOrigins("http://127.0.0.1:62933") // El origen de tu Live Server
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos (incluyendo OPTIONS para el preflight)
                        .allowedHeaders("*"); // Permite todos los encabezados (como Content-Type)
            }
        };
    }
}
