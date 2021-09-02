package com.summer.springsecurityoauth2demo.service;

import com.summer.springsecurityoauth2demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author Summer_DM
 * @Summary TODO  自定义登录逻辑
 * @Version 1.0
 * @Date 2021/8/31 上午 09:57
 **/
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        return new User(username,password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
