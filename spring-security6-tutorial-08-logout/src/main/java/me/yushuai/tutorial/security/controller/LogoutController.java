package me.yushuai.tutorial.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-14
 */
@Controller
public class LogoutController {

    @RequestMapping("/logout-url")
    public String logoutUrl() {
        return "logout";
    }

}
