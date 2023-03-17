package me.yushuai.tutorial.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-10
 */
@Data
@Entity
@Table(name = "SECURITY_USER")
public class User {

    @Id
    private Long id;

    private String username;

    private String password;

}
