package com.summer.springsecuritydemo.config;

import com.summer.springsecuritydemo.handler.AccessDeniedHandlerImpl;
import com.summer.springsecuritydemo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Author Summer_DM
 * @Summary TODO  springSecurity配置类
 * @Version 1.0
 * @Date 2021/8/25 下午 04:36
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //说明前端是表单提交
        http.formLogin()
                //自定义入参，这里是对应登录页面login.html的form标签中用户名和密码的name属性
                //.usernameParameter("username123")
                //.passwordParameter("password123")
                //自定义登录页面
                //.loginPage("/login.htmL")
                .loginPage("/showLogin")
                //必须和表单提交的接口名一样， 这样才会去执行自定义的登录逻辑UserDetailsServiceImpl
                .loginProcessingUrl("/login")
                //登录成功后跳转的页面，post请求
                .successForwardUrl("/toMain")
                //自定义登录成功处理器，这种跳转适用于前后端分离的vue架构，现阶段流行的
                //.successHandler(new AuthenticationSuccessHandlerImpl("/main.html"))
                //登录失败后跳转的页面，post请求, 这种跳转适用于前后端不分离的jsp架构
                .failureForwardUrl("/toError");
                //自定义登录失败处理器，这种跳转适用于前后端分离的vue架构，现阶段流行的
                //.failureHandler(new AuthenticationFailureHandlerImpl("/error.html"));
        //授权（类似防火墙），他是有顺序的，从上面往下面匹配
        http.authorizeRequests()
                //执行/login.html时，放行，不需要认证
                //.antMatchers("/login.html").permitAll()
                //这种写法和上面直接.permitAll()，是一样的
                //.antMatchers("/login.html").access("permitAll")
                .antMatchers("/showLogin").access("permitAll")
                //执行/error.htmL时，放行，不需要认证,放行了error.html登录失败页面
                .antMatchers( "/error.html").permitAll()
                //放行所有静态资源，就是在static目录下面的所有css、js、images目录内容
                //.antMatchers("/css/**","/js/**","/images/**").permitAll()
                //放行静态资源下，所有以.jpg为后缀的文件
                //.antMatchers("/**/*.jpg").permitAll()
                //放行静态资源下，所有以.jpg为后缀的文件，利用正则表达式
                //.regexMatchers(".*[.]jpg").permitAll()
                //表名请求方法，例：只放行post请求
                //.regexMatchers(HttpMethod.POST,"/demo").permitAll()
                //1、执行/demol时，放行，不需要认证,用来测试前置路径，这种方法不常用
                //.regexMatchers("/ttt/demo").permitAll()
                //2、常用的是这种方法，mvc匹配
                //.mvcMatchers("/demo").servletPath("/ttt").permitAll()
                //3、这是第三种方法
                //.antMatchers("/ttt/demo").permitAll()
                //权限控制,严格区分大小写,此处这个admin是对应UserDetailsServiceImpl下面给的权限用户名
                //.antMatchers("/main1.html").hasAuthority("admin")
                //多个权限
                //.antMatchers("/main1.html").hasAnyAuthority("admin","normal")
                //角色控制，严格区分大小写,这里不可以写角色的前缀ROLE_，角色设置在UserDetailsServiceImpl
                //.antMatchers("/main1.html").hasRole("abc")
                //这个写法和上面一样
                //.antMatchers("/main1.html").access("hasRole('abc')")
                //多个角色
                //.antMatchers("/main1.html").hasAnyRole("abC","abc")
                //基于IP地址
                //.antMatchers("/main1.html").hasIpAddress("127.0.0.1")
                //说明所有的请求都必须认证才可以访问，必须先去登录，并且必须放在最后面调用
                .anyRequest().authenticated();
                //自定义的access方法
                //.anyRequest().access("@accessServiceImpl.hasPermission(request,authentication)");
        //异常处理
        http.exceptionHandling()
                //自定义403异常，即权限不够异常
                .accessDeniedHandler(accessDeniedHandler);
                //未认证的处理方案：为了防止用户未登录就去访问需要认证的页面，这里自定义一个返回结果
                //.authenticationEntryPoint((req, resp, authException) -> {
                //            resp.setContentType("application/json;charset=utf-8");
                //            PrintWriter out = resp.getWriter();
                //            out.write("尚未登录，请先登录");
                //            out.flush();
                //            out.close();
                //        });

        //记住我
        http.rememberMe()
                //设置数据源
                .tokenRepository(persistentTokenRepository)
                //.rememberMeParameter()
                //设置超时时间（即，60s之内可以不用登录，直接访问）
                .tokenValiditySeconds(60)
                //自定义登录逻辑
                .userDetailsService(userDetailsServiceImpl);

        //退出登录
        http.logout()
                //自定义退出后跳转的URL,一般不建议更改
                //.logoutUrl("/user/logout")
                //退出成功后跳转的页面
                .logoutSuccessUrl("/login.html");

        //关闭csrf防护(类似登录的防火墙)
        //http.csrf().disable();
    }

    /**
     * 交给spring容器去管理
     * 密码加密、配置、二次加密的类
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表，第一次启动时开启，第二次及以后都需要注释掉,因为表已经存在了，不注释掉会报错
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
