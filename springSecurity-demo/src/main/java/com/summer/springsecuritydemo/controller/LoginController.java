package com.summer.springsecuritydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Summer_DM
 * @Summary TODO  登录控制器
 * @Version 1.0
 * @Date 2021/8/25 下午 04:26
 **/
@Controller
public class LoginController {

    /**
     * 登录
     * @return
     */
    //@RequestMapping("/login")
    //public String login(){
    //    return "redirect:main.html";
    //}

    /**
     * 登录成功后跳转页面
     * @return
     */
    //@Secured("ROLE_abc1")
    //这里可以写成abc或者ROLE_abc，但是在配置类中是不允许以ROLE_开头的
    @PreAuthorize("hasRole('abc')") //在调用接口之前起用注解去判断权限  比较常用
    //@PostAuthorize("hasRole('abc')") //在调用接口之后起用注解去判断权限 一般不用，，，这里可以写成abc或者ROLE_abc
    @RequestMapping("/toMain")
    public String main(){
        return "redirect:main.html";
    }
    /**
     * 登录失败后跳转页面
     * @return
     */
    @RequestMapping("/toError")
    public String error(){
        return "redirect:error.html";
    }
    /**
     * 用来测试，WebSecurityConfig中授权，只放行get请求的资源
     * @return
     */
    @RequestMapping("/demo")
    public String demo(){
        return "demo";
    }
    /**
     * 用来测试csrf的页面跳转
     * @return
     */
    @RequestMapping("/showLogin")
    public String showLogin(){
        return "login";
    }

}
