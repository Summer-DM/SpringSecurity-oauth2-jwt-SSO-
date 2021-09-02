package com.summer.ssoclient01.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Summer_DM
 * @Summary TODO  获取服务器资源--接口
 * @Version 1.0
 * @Date 2021/9/1 下午 04:45
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 测试获取token
     * @param authentication
     * @return
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication){
        return authentication.getPrincipal();
    }

}
