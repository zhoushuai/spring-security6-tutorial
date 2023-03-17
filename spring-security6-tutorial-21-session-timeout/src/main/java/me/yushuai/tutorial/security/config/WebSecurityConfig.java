package me.yushuai.tutorial.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-14
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> {
                    authorize.anyRequest().authenticated();
                })
                .formLogin(formLogin -> formLogin.successHandler((request, response, authentication) -> response.sendRedirect("/")))
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        // 会话过期url
//                        .and().invalidSessionStrategy((request, response) -> response.sendRedirect("/session-expire"))
                );

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        final UserDetails user = User.withUsername("user").password("123456").authorities("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
