package me.yushuai.tutorial.security.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-14
 */
public class CleanCookieLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication.isAuthenticated()) {

            final Cookie[] cookies = request.getCookies();
            if (Objects.isNull(cookies)) {
                return;
            }

            //清理请求中携带的所有cookie
            for (final Cookie cookie : cookies) {
                cookie.setValue(null);
                String contextPath = request.getContextPath();
                String cookiePath = StringUtils.hasText(contextPath) ? contextPath : "/";
                cookie.setPath(cookiePath);
                cookie.setMaxAge(0);
                cookie.setSecure(request.isSecure());
                response.addCookie(cookie);
            }
        }
    }
}
