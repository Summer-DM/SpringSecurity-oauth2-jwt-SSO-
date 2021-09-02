package com.summer.springsecuritydemo.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Summer_DM
 * @Summary TODO   本来是一个处理器，用于处理登录失败后的页面跳转
 * @Version 1.0
 * @Date 2021/8/25 下午 04:57
 **/
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private String url;

    public AuthenticationFailureHandlerImpl(String url) {
        this.url = url;
    }
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //自定义登录失败的页面
        response.sendRedirect(url);

        //自定义登录失败的json返回
        //response.setStatus(00000);
        //response.setContentType("application/json;charset=utf-8");
        //PrintWriter writer = response.getWriter();
        //LoginResponseBean loginResponseBean = new LoginResponseBean();
        //if (e instanceof LockedException) {
        //    loginResponseBean.setMsg("账户被锁定，请联系管理员!");
        //} else if (e instanceof CredentialsExpiredException) {
        //    loginResponseBean.setMsg("密码过期，请联系管理员!");
        //} else if (e instanceof AccountExpiredException) {
        //    loginResponseBean.setMsg("账户过期，请联系管理员!");
        //} else if (e instanceof DisabledException) {
        //    loginResponseBean.setMsg("账户被禁用，请联系管理员!");
        //} else if (e instanceof BadCredentialsException) {
        //    loginResponseBean.setMsg("用户名或者密码输入错误，请重新输入!");
        //}
        //writer.write(new ObjectMapper().writeValueAsString(loginResponseBean));
        //writer.flush();
        //writer.close();
    }
}
