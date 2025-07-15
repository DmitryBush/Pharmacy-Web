package com.bush.pharmacy_web_app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/login", "/register", "/catalog/**", "/", "/cart", "/error")
                            .permitAll()
                        .requestMatchers(HttpMethod.GET, "/css/admin/**", "/js/admin/**")
                            .hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET,"/css/**", "/js/**").permitAll()
                        .requestMatchers("/admin/dashboard", "/admin/orders/**", "/admin/warehouse/**")
                            .hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers("/admin/product", "/admin/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/*/admin/**")
                            .hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers("/api/*/admin/**")
                            .hasRole("ADMIN")
                        .requestMatchers("/api/*/management/**")
                            .hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login")
                        .defaultSuccessUrl("/").permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID"))
                .build();
    }
}
