package me.yushuai.tutorial.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 增加验证码信息
 *
 * @author zhoushuai@189.cn
 * @since 2023-03-15
 */
public class CaptchaAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String correctCaptcha;

    private String captcha;

    public CaptchaAuthenticationToken(Object principal, Object credentials, String correctCaptcha, String captcha) {
        super(principal, credentials);
        this.correctCaptcha = correctCaptcha;
        this.captcha = captcha;
    }

    public String getCorrectCaptcha() {
        return correctCaptcha;
    }

    public String getCaptcha() {
        return captcha;
    }
}
