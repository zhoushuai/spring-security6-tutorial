package me.yushuai.tutorial.security.dao;

import me.yushuai.tutorial.security.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-10
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
