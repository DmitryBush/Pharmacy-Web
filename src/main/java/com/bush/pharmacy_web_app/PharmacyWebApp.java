package com.bush.pharmacy_web_app;

import com.bush.outbox.annotation.EnableOutboxService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@EnableScheduling
@EnableOutboxService
@EnableDiscoveryClient
@SpringBootApplication
public class PharmacyWebApp {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyWebApp.class, args);
	}

}
