package com.summer.ssoclient01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
//开启单点登录
@EnableOAuth2Sso
public class SsoClient01Application {

    public static void main(String[] args) {
        SpringApplication.run(SsoClient01Application.class, args);
    }

}
