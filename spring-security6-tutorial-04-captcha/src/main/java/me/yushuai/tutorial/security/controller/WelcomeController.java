package me.yushuai.tutorial.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-15
 */
@Controller
public class WelcomeController {

    @RequestMapping
    public String welcome() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        System.out.println(authentication.getName());
        return "index";
    }
}
