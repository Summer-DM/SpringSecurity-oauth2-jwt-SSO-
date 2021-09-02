package com.summer.springsecurityoauth2demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author Summer_DM
 * @Summary TODO  sprignSecurity配置类
 * @Version 1.0
 * @Date 2021/8/31 上午 09:58
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //放行登录、注销、oauth所有端点路径
                //剩下的所有请求路径必须先认证
                .antMatchers("/oauth/**","login/**","/logout/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //放行表单操作认证
                .formLogin()
                .permitAll()
                .and()
                //关闭csrf
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 使用密码模式时需要
     * @return
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
