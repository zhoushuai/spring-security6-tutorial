package me.yushuai.tutorial.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

/**
 * 问题1. 对验证的验证逻辑为什么不直接在Filter中而不实现，而在AuthenticationProvider中实现<br>
 * <p>
 * SpringSecurity的验证逻辑应该在AuthenticationProvider中，如果部分验证逻辑定义在Filter中导致验证逻辑存在多个地方，
 * 如果需要迁移验证业务逻辑会变的很复杂。
 * <p>
 * 问题2：验证码一般都是存放在Session中而AuthenticationProvider中并没有提供获取Session的入口？
 * <p>
 * AuthenticationProvider接口中并没有提供获取Session的操作，但是Filter中可以获取Session和请求参数可以直接在可以在Filter中
 * 获取验证码的所有信息存放在Token中就可以了。
 *
 * @author zhoushuai@189.cn
 * @since 2023-03-15
 */
@Component
public class CaptchaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String DEFAULT_CAPTCHA_PARAMETER = "captcha";

    public static final String CAPTCHA_SESSION_ATTR = "captcha_session_key";

    private String captchaParameter = DEFAULT_CAPTCHA_PARAMETER;

    public CaptchaAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        super.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        super.setAuthenticationSuccessHandler((request, response, authentication) -> response.sendRedirect("/"));
        super.setAuthenticationFailureHandler((request, response, exception) -> response.sendRedirect("/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        final String username = super.obtainUsername(request);
        final String password = super.obtainPassword(request);
        final String correctCaptcha = this.obtainCaptchaFormSession(request.getSession());
        final String captcha = this.obtainCaptchaFormRequest(request);
        final CaptchaAuthenticationToken authenticationToken = new CaptchaAuthenticationToken(username, password,
                correctCaptcha, captcha);

        return super.getAuthenticationManager().authenticate(authenticationToken);
    }

    /**
     * 从Session中获取验证码
     *
     * @param session 会话信息
     * @return 返回会话中存储验证码
     */
    private String obtainCaptchaFormSession(HttpSession session) {
        return (String) session.getAttribute(CAPTCHA_SESSION_ATTR);
    }

    private String obtainCaptchaFormRequest(HttpServletRequest request) {
        return request.getParameter(this.captchaParameter);
    }
}
