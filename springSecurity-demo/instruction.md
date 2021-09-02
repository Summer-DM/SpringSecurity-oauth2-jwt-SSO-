# SpringSecurity

这里记录学习SSO单点登录的一些实现逻辑，尤其是SpringSecurity、Oauth2的学习

###目前已实现：
1. 自定义登录逻辑----UserDetailsServiceImpl
2. 自定义登录页面----WebSecurityConfig（在这里配置）
3. 自定义登录成功或者失败跳转：----WebSecurityConfig（在这里配置）   
   ①、前后端不分离，直接写死跳转页面：适用于JSP、html等----LoginController     
   ②、前后端分离，通过写一个handler去重定向url----AuthenticationFailureHandlerImpl、AuthenticationSuccessHandlerImpl
4. 自定义登录页面中，form标签的username和password的name属性----WebSecurityConfig
