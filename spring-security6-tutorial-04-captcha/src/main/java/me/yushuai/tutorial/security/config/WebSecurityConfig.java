package me.yushuai.tutorial.security.config;

import me.yushuai.tutorial.security.authentication.CaptchaAuthenticationFilter;
import me.yushuai.tutorial.security.authentication.CaptchaAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 * 如下配置访问/login可以登录，登录成功后依然包403无权限访问的错误，经排查发现CaptchaFilter下游的Filter拿到的
 * Authentication 对象还是匿名Authentication对象。调试CaptchaAuthenticationFilter可以确认确实把认证成功
 * Authentication对象存如了SecurityContext中了，为什么下游Filter不能拿到正确Authentication。
 * <p>
 * 因为CaptchaAuthenticationFilter的父类是AbstractAuthenticationProcessingFilter它内部存储SecurityContext默认使用的是
 * RequestAttributeSecurityContextRepository， 这是一个不支持跨请求的Repository如果需要跨请求需要使用HttpSessionSecurityContextRepository替换它。
 *
 * @author zhoushuai@189.cn
 * @since 2023-03-15
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain webSecurity(HttpSecurity http, CaptchaAuthenticationFilter captchaAuthenticationFilter) throws Exception {
        http
                .addFilterAt(captchaAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizer -> authorizer
                        .requestMatchers("/captcha", "/login", "/logout").permitAll()
                        .anyRequest().authenticated()
                )

                //CaptchaAuthenticationFilter不处理（GET /login）它最终会交由AuthorizationFilter处理并抛出异常，再后面会交给
                // ExceptionTranslationFilter来处理异常，这里我们需要把异常转换为HTTP错误。
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/login"));
                })
                .csrf().disable()
                .cors().disable()
                .logout();
        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    AuthenticationProvider captchaAuthenticationProvider() {
        // 自定义AuthenticationProvider 应该怎么注册
        final CaptchaAuthenticationProvider captchaAuthenticationProvider = new CaptchaAuthenticationProvider();
        captchaAuthenticationProvider.setUserDetailsService(this.userDetailsService());
        return captchaAuthenticationProvider;
    }

    @Bean
    UserDetailsService userDetailsService() {
        final UserDetails user = User.withDefaultPasswordEncoder().username("user")
                .password("123456")
                .authorities("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
