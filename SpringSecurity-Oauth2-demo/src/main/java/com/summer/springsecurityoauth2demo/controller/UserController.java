package com.summer.springsecurityoauth2demo.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @Author Summer_DM
 * @Summary TODO  写一个资源的demo
 * @Version 1.0
 * @Date 2021/8/31 上午 10:19
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
    /**
     * 测试解析token
     * @param authentication
     * @return
     */
    @RequestMapping("/getJwtToken")
    public Object getJwtToken(Authentication authentication, HttpServletRequest request){
        //从request的请求头中获取认证信息
        String header = request.getHeader("Authorization");
        //因为一般我们的token都在header里，格式：bearer+空格+token
        String token = header.substring(header.lastIndexOf("bearer") + 7);
        Claims claims = Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
