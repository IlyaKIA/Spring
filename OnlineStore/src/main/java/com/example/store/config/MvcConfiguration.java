package com.example.store.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("${main_catalog_address}")
    private String CATALOG;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "data";
        String staticPath = Paths.get(CATALOG, dirName).toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:///"+  staticPath + "/");
    }

}
