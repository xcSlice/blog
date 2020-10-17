package com.xusi.blog.security.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: IntelliJ IDEA
 * @description: 前端携带的token验证
 * @author: xusi
 * @create:2020-10-17 12:11
 **/
public class CustomToken implements AuthenticationToken {
    private String token;

    public CustomToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
