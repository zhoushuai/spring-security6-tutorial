package me.yushuai.tutorial.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-15
 */
public class CaptchaAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        return (CaptchaAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        //1. 验证验证码是否正确
        this.verifyCaptcha((CaptchaAuthenticationToken) authentication);
        //2. 交由DaoAuthenticationProvider继续对Username&Password进行验证
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

    /**
     * 验证验证码是否正确
     */
    public void verifyCaptcha(CaptchaAuthenticationToken authenticationToken) {
        final String correctCaptcha = authenticationToken.getCorrectCaptcha();
        final String captcha = authenticationToken.getCaptcha();

        if (!StringUtils.endsWithIgnoreCase(correctCaptcha, captcha)) {
            throw new BadCaptchaException(super.messages.getMessage("BadCaptcha",
                    "the captcha is fail."));
        }
    }
}
