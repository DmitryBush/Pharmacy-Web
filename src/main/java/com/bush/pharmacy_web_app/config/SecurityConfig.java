package com.bush.pharmacy_web_app.config;

import com.bush.pharmacy_web_app.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


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
                                "product/**", "news/**")
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
                        .requestMatchers("/api/*/carts/**").authenticated()
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
