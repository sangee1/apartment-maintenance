package com.shangrila.aptmaintenance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // Forward all routes (except API) to index.html
        registry.addViewController("/{x:[\\w\\-]+}")
                .setViewName("forward:/index.html");

        registry.addViewController("/**/{x:[\\w\\-]+}")
                .setViewName("forward:/index.html");
    }
}
