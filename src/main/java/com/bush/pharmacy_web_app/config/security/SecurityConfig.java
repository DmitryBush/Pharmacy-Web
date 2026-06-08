package com.bush.pharmacy_web_app.config.security;

import com.bush.pharmacy_web_app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
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

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    private final Environment environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(setUpCsrfProtection())
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/login", "/register", "/catalog/**", "/", "/cart", "/error",
                                "product/**", "news/**", "order/**", "api/*/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/css/admin/**", "/js/admin/**")
                            .hasAnyRole("OPERATOR", "ADMIN", "ROOT")
                        .requestMatchers(HttpMethod.GET, "/css/**", "/js/**", "favicon.ico").permitAll()
                        .requestMatchers("/admin/dashboard", "/admin/orders/**", "/admin/warehouse/**")
                            .hasAnyRole("ADMIN", "OPERATOR", "ROOT")
                        .requestMatchers("/admin/product", "/admin/categories").hasAnyRole("ADMIN", "ROOT")
                        .requestMatchers(HttpMethod.GET, "/api/*/admin/**")
                            .hasAnyRole("ADMIN", "OPERATOR", "ROOT")
                        .requestMatchers("/api/*/admin/**")
                            .hasAnyRole("ADMIN", "ROOT")
                        .requestMatchers("/api/*/management/**")
                            .hasAnyRole("ADMIN", "OPERATOR", "ROOT")
                        .requestMatchers("/api/*/carts/**").authenticated()
                        .requestMatchers("/api/*/orders/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exceptionConfigurer -> exceptionConfigurer
                        .authenticationEntryPoint(authenticationExceptionHandler))
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

    public Customizer<CsrfConfigurer<HttpSecurity>> setUpCsrfProtection() {
        Set<String> profilesSet = Set.of(environment.getActiveProfiles());
        if (profilesSet.contains("dev") || profilesSet.contains("load")) {
            return configurer -> configurer
                    .ignoringRequestMatchers("/api/**", "/login/**", "/register/**", "/logout/**");
        }
        return Customizer.withDefaults();
    }
}
