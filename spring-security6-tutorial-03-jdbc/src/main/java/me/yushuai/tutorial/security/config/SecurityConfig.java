package me.yushuai.tutorial.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-10
 */
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    UserDetailsService userDetailsService() {
        final JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setJdbcTemplate(jdbcTemplate);
        return jdbcDao;
    }

}
