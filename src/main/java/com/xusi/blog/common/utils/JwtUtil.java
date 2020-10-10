package com.xusi.blog.common.utils;

import com.xusi.blog.security.config.MyUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: IntelliJ IDEA
 * @description: jwt and token
 * @author: xusi
 * @create:2020-09-27 11:09
 **/
@Component
public class JwtUtil {
    public static final String TOKEN_HEADER = "authorization";
    public static final String TOKEN_PREFIX = "token ";
//    private static final String TOKEN_HEADER = "xs ";
    private static final String SLAT = "@dfsa3$%fd^xxaf";
    
    private static final String ISS = "xusi";
    private static final long EXPIRE_TIME = 60*60;
    private static final long LONG_EXPIRE_TIME = 60*60*24*7;
    

    // 加入权限
//    public String createToken(String authorities,String username, boolean isRemeberMe){
//
//    }


    public String createToken(String username, String authorities, boolean isRememberMe){
        long expiration = isRememberMe ? LONG_EXPIRE_TIME : EXPIRE_TIME;
        Map<String,Object> map = new HashMap<>();
        map.put("auth",authorities);
        map.put("username",username);
        // 生成
        return Jwts.builder()
                           .signWith(SignatureAlgorithm.HS256,SLAT)
                            // 放到后面可能会覆盖字段
                            .setClaims(map)
                           .setIssuer(ISS)
                           .setSubject(username)
                           .setIssuedAt(new Date())
                           .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                           .compact();
    }


    // 查询过期时间
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    // 获取角色名
    public String getSubject(String token){
        return getTokenBody(token).getSubject();
    }

    // 获取 token 中的数据
    public static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SLAT)
                .parseClaimsJws(token)
                .getBody();
                
    }

    // 获取用户信息
//    public

}
