package me.yushuai.tutorial.security.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误
 *
 * @author zhoushuai@189.cn
 * @since 2023-03-15
 */
public class BadCaptchaException extends AuthenticationException {

    public BadCaptchaException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BadCaptchaException(String msg) {
        super(msg);
    }
}
