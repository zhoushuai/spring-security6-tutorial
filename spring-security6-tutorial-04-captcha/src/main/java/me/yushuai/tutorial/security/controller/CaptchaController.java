package me.yushuai.tutorial.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.yushuai.tutorial.security.authentication.CaptchaAuthenticationFilter;
import me.yushuai.tutorial.security.util.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zhoushuai@189.cn
 * @since 2023-03-15
 */
@Controller
public class CaptchaController {

    /**
     * 获取验证码图片
     */
    @RequestMapping("/captcha")
    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) {

        try (OutputStream os = response.getOutputStream()) {
            int width = 200;
            int height = 69;
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //生成对应宽高的初始图片
            String randomText = VerifyCode.drawRandomText(width, height, verifyImg);
            //单独的一个类方法，出于代码复用考虑，进行了封装。
            // 功能是生成验证码字符并加上噪点，干扰线，返回值为验证码字符
            request.getSession().setAttribute(CaptchaAuthenticationFilter.CAPTCHA_SESSION_ATTR, randomText);
            //必须设置响应内容类型为图片，否则前台不识别
            response.setContentType("image/png");
            //输出图片流
            ImageIO.write(verifyImg, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
