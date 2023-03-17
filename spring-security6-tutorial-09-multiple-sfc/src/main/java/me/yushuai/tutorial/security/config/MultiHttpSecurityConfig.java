package me.yushuai.tutorial.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-10
 */
@EnableWebSecurity
@Configuration
public class MultiHttpSecurityConfig {

    /**
     * 用户api
     */
    @Bean
    @Order(1)
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher("/api/**")
                .authorizeHttpRequests(authorizationManager -> {
                    authorizationManager.requestMatchers("/login").permitAll()
                            .requestMatchers("/logout").permitAll()
                            .anyRequest().denyAll();
                })
                .csrf().disable()
                .cors().disable()
                .formLogin().disable();
        return httpSecurity.build();
    }

    /**
     * 管理员api
     */
    @Bean
    @Order(2)
    SecurityFilterChain adminSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher("/api/admin/**");
        return httpSecurity.build();
    }


}
