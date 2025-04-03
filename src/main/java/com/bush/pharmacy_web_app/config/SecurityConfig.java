package com.bush.pharmacy_web_app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.bush.pharmacy_web_app.repository.entity.role.RoleType.ADMIN;
import static com.bush.pharmacy_web_app.repository.entity.role.RoleType.OPERATOR;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(lamb -> lamb
                        .requestMatchers("/login", "/register", "/catalog/**", "/", "/cart", "/error").permitAll()
                        .requestMatchers("/admin/**").hasRole(ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login")
                        .defaultSuccessUrl("/api/v1/orders").permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID"))
                .build();
    }
}
