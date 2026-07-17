package com.example.royalauto.ms_comercial.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // La ruta absoluta a la carpeta donde guardaremos las fotos
        String absolutePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
        
        // Cuando el frontend pida "/api/imagenes/foto.jpg", Spring buscará en la carpeta local "uploads"
        registry.addResourceHandler("/api/imagenes/**")
                .addResourceLocations(absolutePath);
    }
}