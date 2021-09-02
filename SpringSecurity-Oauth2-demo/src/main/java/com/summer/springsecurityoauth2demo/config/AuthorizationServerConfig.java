package com.summer.springsecurityoauth2demo.config;

import com.summer.springsecurityoauth2demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Summer_DM
 * @Summary TODO  配置oauth2的配置类---授权服务器的配置类
 * @Version 1.0
 * @Date 2021/8/31 上午 10:10
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    //密码模式需要
    @Autowired
    private AuthenticationManager authenticationManager;
    //密码模式需要
    @Autowired
    private UserService userService;

    //配置redis存储token
    //@Autowired
    //@Qualifier("redisTokenStore")
    //private TokenStore tokenStore;

    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 授权码模式
     * 获取授权码：http://localhost:8080/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端ID
                .withClient("client")
                //秘钥
                .secret(passwordEncoder.encode("112233"))
                //重定向地址
                //.redirectUris("http://www.baidu.com")
                .redirectUris("http://localhost:8081/login")
                //授权范围
                .scopes("all")
                //设置令牌过期时间   60s
                .accessTokenValiditySeconds(60)
                //设置刷新令牌的过期时间
                .refreshTokenValiditySeconds(60000)
                //配置自动授权
                .autoApprove(true)
                /**
                 * 授权类型
                 * authorization_code：授权码模式
                 * password:密码模式
                 * refresh_token:刷新令牌
                 */
                .authorizedGrantTypes("authorization_code","password","refresh_token");
    }

    /**
     * 密码模式
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //设置（扩展）jwt增强内容
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        chain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
        //配置redis
        //.tokenStore(tokenStore);
                //配置jwt存储token
                .tokenStore(tokenStore)
                //accessToken转成jwtToken
                .accessTokenConverter(jwtAccessTokenConverter)
                //将设置好的jwtTokenEnhancer放进token中
                .tokenEnhancer(chain);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //获取秘钥
        //必须要身份认证，单点登录必须要有的配置
        security.tokenKeyAccess("isAuthenticated()");
    }
}
