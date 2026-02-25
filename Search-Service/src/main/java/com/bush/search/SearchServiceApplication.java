package com.bush.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SearchServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }
}
