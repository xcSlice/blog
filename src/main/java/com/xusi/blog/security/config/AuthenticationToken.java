package com.xusi.blog.security.config;

import com.xusi.blog.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @program: IntelliJ IDEA
 * @description: 自定义token
 * @author: xusi
 * @create:2020-10-03 15:27
 **/
public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

    @Resource
    private JwtUtil jwtUtil;
    private String token;


    public AuthenticationToken( Object principal, Object credentials,String token) {
        super(principal, credentials);
        this.token = token;
    }

    public AuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String token) {
        super(principal, credentials, authorities);
        this.token = token;
    }

    public String getToken() {
        return token;
    }


}
