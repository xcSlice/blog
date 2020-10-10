package com.xusi.blog.security.config;


import com.xusi.blog.common.exception.TokenException;
import com.xusi.blog.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.omg.CORBA.UserException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * @program: IntelliJ IDEA
 * @description: 过滤器
 * @author: xusi
 * @create:2020-09-19 21:08
 **/
public class MyAuthenticationProcessingFilter extends GenericFilterBean {


    private AuthenticationManager authenticationManager;

    MyAuthenticationProcessingFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 如果已经验证或者authentication为空
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            chain.doFilter(request,response);
            return;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 获取 request 中的数据
        String token = ((HttpServletRequest) request).getHeader(JwtUtil.TOKEN_HEADER);
        // 如果没有 token
        if (token == null || token.equals("undefined")) {
            chain.doFilter(request, response);
            return;
        }
        token = token.replace(JwtUtil.TOKEN_PREFIX,"");
        Claims claims = JwtUtil.getTokenBody(token);
        Object username = claims.getSubject();
        Object password = claims.get("password");
        // 封装一个 authenticationToken
        authentication = new AuthenticationToken(username,password,token);
        // 使用 provider 验证
        Authentication responseAuthentication = authenticationManager.authenticate(authentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException("Unable to authenticate for provided credentials");
        }
        SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
    }


}
