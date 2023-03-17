package me.yushuai.tutorial.security.config;

import me.yushuai.tutorial.security.userdetails.JpaUserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-10
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/");
        return http.build();
    }


    @Bean
    UserDetailsService userDetailsService() {
        return new JpaUserDetailServiceImpl();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
