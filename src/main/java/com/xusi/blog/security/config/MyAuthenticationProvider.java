package com.xusi.blog.security.config;

import com.xusi.blog.common.utils.JwtUtil;
import com.xusi.blog.common.utils.PasswordUtil;
import com.xusi.blog.common.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.annotation.Resource;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-09-19 20:26
 **/
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


    @Resource
    private MyUserDetailsService myUserDetailsService;
    private static final Logger log = LoggerFactory.getLogger(MyAuthenticationProvider.class);

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
        String token = authenticationToken.getToken();
        Claims claims = JwtUtil.getTokenBody(token);
//        Claims token = JwtUtil.getTokenBody(authenticationToken.getToken());
        // token 格式是否正确
        if(token == null || token.startsWith(JwtUtil.TOKEN_PREFIX)){
            throw new BadCredentialsException("token 格式有误");
        }
        token = token.replace(JwtUtil.TOKEN_PREFIX,"");
        // expiration
        if(JwtUtil.isExpiration(token)){
            throw new BadCredentialsException("token 已过期");
        }

        String username = claims.getSubject();
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());


        return auth;

    }

//    是否启用?
    @Override
    public boolean supports(Class<?> authentication) {
        return (AuthenticationToken.class.isAssignableFrom(authentication));
    }

        /**
         * 授权持久化.
         */
    @Override
    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication, UserDetails user) {
        log.info("什么时候进入的");
        return super.createSuccessAuthentication(principal, authentication, user);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return null;
    }
}
