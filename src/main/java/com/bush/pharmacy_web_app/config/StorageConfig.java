package com.bush.pharmacy_web_app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Getter
@Setter
@Component
public class StorageConfig {
    private String directory;
}
