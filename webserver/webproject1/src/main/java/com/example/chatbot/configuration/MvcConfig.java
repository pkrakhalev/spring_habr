package com.example.chatbot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.example.imggen.IdenticonGenerator.IMAGE_FOLDER_PATH;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:///" + IMAGE_FOLDER_PATH);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry
//                .addResourceHandler("/resources/**")
//                .addResourceLocations("/resources/","classpath:/other-resources/");
    }
}
