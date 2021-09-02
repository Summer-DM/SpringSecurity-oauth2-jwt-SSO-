package com.summer.springsecuritydemo.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Summer_DM
 * @Summary TODO   本类是一个处理器，用于处理登录成功后的页面跳转
 * @Version 1.0
 * @Date 2021/8/25 下午 04:55
 **/
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private String url;

    public AuthenticationSuccessHandlerImpl(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //获取ip地址
        System.out.println(httpServletRequest.getRemoteAddr());
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getUsername());
        //处于安全考虑，默认输入null
        System.out.println(user.getPassword());
        System.out.println(user.getAuthorities());
        httpServletResponse.sendRedirect(url);
    }
}
