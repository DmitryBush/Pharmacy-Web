package com.bush.pharmacy_web_app.config;

import com.bush.pharmacy_web_app.service.exception.StorageException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@ConfigurationProperties("storage")
@Getter
@Setter
@Component
public class StorageConfig {
    private String directory;

    @Bean
    public Path rootLocation() {
        if (directory.trim().isEmpty()) {
            throw new StorageException("Directory for saving files is not specified. Check storage configuration file");
        }
        return Paths.get(directory).normalize().toAbsolutePath();
    }
}
