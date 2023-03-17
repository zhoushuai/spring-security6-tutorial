package me.yushuai.tutorial.security.config;

import me.yushuai.tutorial.security.config.web.MySecurityContextRepository;
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
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-14
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )

                //自定义存储登录用户的SecurityContextRepository
                .securityContext(securityContext -> {
                    securityContext.securityContextRepository(securityRepository());
                })
                .formLogin(form -> form.successHandler(
                        (request, response, authentication) -> response.sendRedirect("/")
                ))
                .sessionManagement(
                        // 限制用户同时登录的数量
                        session -> session.maximumSessions(1)
                                // 默认超过登录限制退出最先登录用户，开启如下配置表示阻止第二次登录
                                .maxSessionsPreventsLogin(true)
                ).formLogin().and().build();
    }

    @Bean
    SecurityContextRepository securityRepository() {
        return new MySecurityContextRepository();
    }

    @Bean
    UserDetailsService userDetailsService() {
        final UserDetails user = User.withUsername("user").password("123456").authorities("USER").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


}
