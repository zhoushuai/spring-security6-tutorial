package me.yushuai.tutorial.security.config.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义使用SessionId存储来存储SecurityContext实例
 *
 * @author zhoushuai@189.cn
 * @since 2023-03-14
 */
public class MySecurityContextRepository implements SecurityContextRepository {

    public static final Logger LOGGER = LoggerFactory.getLogger(MySecurityContextRepository.class);

    private static final ConcurrentHashMap<String, SecurityContext> AUTHENTICATIONS = new ConcurrentHashMap<>();

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        final HttpServletRequest request = requestResponseHolder.getRequest();
        final String id = request.getSession().getId();
        final String requestURI = request.getRequestURI();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("load security context : {}, sessionId: {}", requestURI, id);
        }
        return AUTHENTICATIONS.get(id);
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        final String id = request.getSession().getId();
        AUTHENTICATIONS.put(id, context);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("save security context: {} -> {}", id, context.getAuthentication());
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        final String id = request.getSession().getId();
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("session id : {}", id);
        }
        return AUTHENTICATIONS.contains(id);
    }
}
