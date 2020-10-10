package com.xusi.blog.security.config;

import com.xusi.blog.common.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: IntelliJ IDEA
 * @description: 登录成功返回token
 * @author: xusi
 * @create:2020-10-03 22:51
 **/
public class TokenSuccessHandler  implements AuthenticationSuccessHandler {
    @Resource
    private JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(TokenSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 如果 authentication 是 UsernamePassword
        if(authentication instanceof UsernamePasswordAuthenticationToken){
            String username = authentication.getName();
//            String password = authentication.getCredentials().toString();
            String authenties = authentication.getAuthorities().toString().replace("[ROLE_","").replace("]","");
            String token = jwtUtil.createToken(username,authenties,false);
            response.addHeader(JwtUtil.TOKEN_HEADER,JwtUtil.TOKEN_PREFIX+token);
            log.info("设置token到header中成功");
//            response.sendRedirect(request.getRequestURL().toString());
//            response.sendRedirect("/index");
        }
    }
}
