package com.summer.springsecuritydemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author Summer_DM
 * @Summary TODO 实现自己的登录逻辑
 * @Version 1.0
 * @Date 2021/8/25 下午 04:38
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("执行自定义的登录逻辑");
        //1.根据用户名去数据库查询用户是否存在?
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        //2.比较密码，即客户端传进来的密码和数据库密码进行比较，都是加密的，如果匹配则直接返回User
        //boolean matches = passwordEncoder.matches("前端传的"，" 数据库取的");
        //这里先不创建数据库
        String password = this.passwordEncoder.encode("123");
        //这里的list表示授予的权限，也就是用户名
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList
                ("admin,normal,ROLE_abc,/main.html,/insert,/delete"));
    }
}
