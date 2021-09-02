package com.summer.springsecuritydemo.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Summer_DM
 * @Summary TODO  自定义权限控制的access方法，可以控制用户请求权限
 * @Version 1.0
 * @Date 2021/8/27 上午 10:14
 **/
public interface AccessService {

    /**
     * 自定义权限控制的access方法，可以控制用户请求权限
     * @param request
     * @param authentication
     * @return
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
