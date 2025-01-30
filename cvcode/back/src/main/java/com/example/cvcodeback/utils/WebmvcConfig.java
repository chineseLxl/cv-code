package com.example.cvcodeback.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebmvcConfig implements WebMvcConfigurer {

    @Value("${img.path}")
    private String imgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //terrain指文件存放的绝对路径
        String terrain = imgPath.concat(File.separator);
        File file = new File(terrain);
        if (!file.exists()) {
            file.mkdirs();
        }
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+terrain);
    }
}

