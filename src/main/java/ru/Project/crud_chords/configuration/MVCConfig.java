package ru.Project.crud_chords.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MVCConfig implements WebMvcConfigurer {
    @Value("${app.upload.dir}") // Получаем директорию загрузки
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // Указывает где искать ресурсы
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadDir + "/");
        registry.addResourceHandler("/webjars/**") // Отдельно для ресурсов Bootstrap
                .addResourceLocations("classpath:/META-INF/resources/webjars");
    }

}
