package com.xusi.blog.security.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-10 17:16
 **/
public class CustomMather extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        return super.doCredentialsMatch(token, info);
    }

    @Override
    protected boolean equals(Object tokenCredentials, Object accountCredentials) {
        return super.equals(tokenCredentials, accountCredentials);
    }
}
