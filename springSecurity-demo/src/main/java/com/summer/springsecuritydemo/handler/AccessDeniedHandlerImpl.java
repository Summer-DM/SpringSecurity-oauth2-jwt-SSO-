package com.summer.springsecuritydemo.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Summer_DM
 * @Summary TODO  自定义403处理页面
 * @Version 1.0
 * @Date 2021/8/26 上午 11:09
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //响应状态
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        //返回json格式
        httpServletResponse.setHeader("Content-Type","application/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员\"}");
        writer.flush();
        writer.close();
    }
}
