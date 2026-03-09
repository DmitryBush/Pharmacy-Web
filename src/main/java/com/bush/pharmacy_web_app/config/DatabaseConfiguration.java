package com.bush.pharmacy_web_app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@EntityScan(basePackages = "com.bush.pharmacy_web_app.model.entity")
@EnableJpaRepositories(basePackages = "com.bush.pharmacy_web_app.repository")
public class DatabaseConfiguration {

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryBean.getObject()));
    }
}
