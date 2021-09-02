package com.summer.springsecuritydemo.service.impl;

import com.summer.springsecuritydemo.service.AccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @Author Summer_DM
 * @Summary TODO  service实现类   实现自己的访问控制逻辑
 * @Version 1.0
 * @Date 2021/8/27 上午 10:17
 **/
@Service
public class AccessServiceImpl implements AccessService {

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //获取主题
        Object obj = authentication.getPrincipal();
        //判断主题是否属于UserDetails
        if (obj instanceof UserDetails) {
            //获取权限
            UserDetails userDetails = (UserDetails) obj;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            //判断请求的URI是否在权限里
            System.out.println("访问控制URI："+request.getRequestURI());
            return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
        }
        return false;
    }
}
