package com.bush.pharmacy_web_app.config;

import com.bush.pharmacy_web_app.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Configuration parameters for Argon2
    @Value("${spring.security.encryption-config.salt-length}")
    private Integer saltLength;
    @Value("${spring.security.encryption-config.hash-length}")
    private Integer hashLength;
    @Value("${spring.security.encryption-config.parallelism}")
    private Integer parallelism;
    @Value("${spring.security.encryption-config.memory}")
    private Integer memory;
    @Value("${spring.security.encryption-config.iterations}")
    private Integer iterations;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/login", "/register", "/catalog/**", "/", "/cart", "/error",
                                "product/**", "news/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/css/admin/**", "/js/admin/**")
                            .hasAnyRole("OPERATOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/css/**", "/js/**").permitAll()
                        .requestMatchers("/admin/dashboard", "/admin/orders/**", "/admin/warehouse/**")
                            .hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers("/admin/product", "/admin/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/*/admin/**")
                            .hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers("/api/*/admin/**")
                            .hasRole("ADMIN")
                        .requestMatchers("/api/*/management/**")
                            .hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers("/api/*/carts/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(((request, response,
                                          authentication) -> {
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.sendError(HttpStatus.NO_CONTENT.value());
                            } else {
                                String targetUrl = response.getHeader("Referer");
                                if ("/login".equals(targetUrl) || Objects.isNull(targetUrl)) {
                                    targetUrl = "/";
                                }
                                response.setStatus(HttpStatus.FOUND.value());
                                response.setHeader("Location", targetUrl);
                            }
                        })
                        )
                        .failureHandler(((request, response,
                                          exception) -> {
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.sendError(HttpStatus.UNAUTHORIZED.value());
                            } else {
                                response.sendRedirect("/login?error");
                            }
                        })
                        )
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exceptionConfigurer -> exceptionConfigurer
                        .authenticationEntryPoint((request, response,
                                                   authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.sendError(HttpStatus.UNAUTHORIZED.value());
                            } else {
                                response.sendRedirect("/login");
                            }
                        }))
                .build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }
}
