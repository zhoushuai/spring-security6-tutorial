package me.yushuai.tutorial.security.userdetails;

import me.yushuai.tutorial.security.dao.UserDao;
import me.yushuai.tutorial.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

/**
 * 查询用户信息
 *
 * @author zhoushuai@189.cn
 * @since 2023-03-10
 */
public class JpaUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userDao.findByUsername(username);
        final User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("*"))
        );
    }
}
