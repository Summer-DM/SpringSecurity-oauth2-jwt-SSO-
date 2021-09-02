##springSecurity整合Oauth2

###说明一下需要准备的模块
1、需要一个自定义的登录逻辑,去实现SpringSecurity底层的UserDetailsService--UserService     
2、需要一个自定义的User类来实现SpringSecurity底层的UserDetails--User       
3、需要一个SpringSecurity的配置类--SecurityConfig    
4、需要一个授权服务器的配置类--AuthorizationServerConfig  
5、需要一个资源服务器的配置类--ResourceServerConfig   
6、需要写一个可以访问的资源--UserController

###主要功能
1、SpringSecurity整合oauth2    
2、授权模式：授权码模式、密码模式   
3、redis存储token