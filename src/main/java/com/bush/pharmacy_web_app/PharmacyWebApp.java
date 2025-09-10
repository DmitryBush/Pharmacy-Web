package com.bush.pharmacy_web_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@EnableScheduling
@SpringBootApplication
public class PharmacyWebApp {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyWebApp.class, args);
	}

}
