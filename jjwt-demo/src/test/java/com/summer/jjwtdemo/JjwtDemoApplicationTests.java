package com.summer.jjwtdemo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import jdk.nashorn.internal.parser.Token;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class JjwtDemoApplicationTests {

    /**
     * 创建token
     */
    @Test
    public void testJwt() {
        //当前时间
        long date = System.currentTimeMillis();
        //失效（过期）时间一分钟
        long exp = date + 60 * 1000;
        JwtBuilder jwtBuilder = Jwts.builder()
                //唯一ID{“Id”:"888"}
                .setId("888")
                //接受的用户{“sub”:“Rose”}
                .setSubject("Rose")
                //签发时间{“iat”:“时间”}
                .setIssuedAt(new Date())
                //签名算法，及秘钥
                .signWith(SignatureAlgorithm.HS256, "xxxx")
                //设置失效（过期）时间
                .setExpiration(new Date(exp))
                //自定义声明claims
                //俩种方法：1、直接设置key,value形式  2、通过创建map
                .claim("name","张三")
                .claim("address","北京市东城区朝阳门外大街");
                //.addClaims(map);
        //签发token
        String token = jwtBuilder.compact();
        System.out.println("基于jwt生成token： "+ token);
        System.out.println("手动解密----------------");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //盐，，，解密出来会乱码（为了安全）
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }

    /**
     * 校验解析token
     */
    @Test
    public void testParseToken(){
        //这里直接使用上面生成的token
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjMwNDc1NDM0LCJleHAiOjE2MzA0NzU0OTQsIm5hbWUiOiLlvKDkuIkiLCJhZGRyZXNzIjoi5YyX5Lqs5biC5Lic5Z-O5Yy65pyd6Ziz6Zeo5aSW5aSn6KGXIn0.YtBw3owHl0Yv8tk0a5lMtt3jOoi1gmV1E-SxJYndrDQ";
        //解析token，获取claims，即jwt荷载声明的对象
        Claims claims = (Claims) Jwts.parser()
                //秘钥，必须和上面生成token使用的是同一个秘钥
                .setSigningKey("xxxx")
                .parse(token)
                .getBody();

        System.out.println("获取唯一ID="+claims.getId());
        System.out.println("获取接受的用户sub="+claims.getSubject());
        System.out.println("获取签发时间iat="+claims.getIssuedAt());
        System.out.println("获取token过期时间，默认的="+claims.getExpiration());
        System.out.println("获取自定义的name="+claims.get("name"));
        System.out.println("获取自定义的adress="+claims.get("address"));
    }

}
