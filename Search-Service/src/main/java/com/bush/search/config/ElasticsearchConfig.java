package com.bush.search.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.bush.search.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {
    @Value("${spring.elsaticsearch.url}")
    private String url;
    @Value("${spring.elsaticsearch.username}")
    private String username;
    @Value("${spring.elsaticsearch.password}")
    private String password;

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(url)
                .withBasicAuth(username, password)
                .build();
    }
}
