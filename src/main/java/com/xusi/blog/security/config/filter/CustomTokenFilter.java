package com.xusi.blog.security.config.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xusi.blog.common.R;
import com.xusi.blog.common.utils.JwtUtil;
import com.xusi.blog.security.config.CustomToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: IntelliJ IDEA
 * @description: 自定义的token过滤器
 * @author: xusi
 * @create:2020-10-17 12:08
 **/
@Component
public class CustomTokenFilter extends AuthenticatingFilter {



//    @Override
//    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//
//        String token = WebUtils.toHttp(request).getHeader(JwtUtil.TOKEN_HEADER);
//        if(StringUtils.isEmpty(token) || !token.startsWith(JwtUtil.TOKEN_PREFIX)) {
//            System.out.println("token = " + token);
//            return false;
//        }
//        try {
//            CustomToken customToken = new CustomToken(token.replace(JwtUtil.TOKEN_PREFIX, ""));
//            getSubject(request,response).login(customToken);
//            System.out.println("登录成功");
//            return true;
//        } catch (AuthenticationException e){
//            System.out.println(e);
//            response.getWriter().print(ResponseEntity.status(500).body( "没有访问权限，原因是:" + e.getMessage()));
//            return false;
//
//        }
//    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String token = WebUtils.toHttp(request).getHeader(JwtUtil.TOKEN_HEADER);
        CustomToken customToken = new CustomToken(token.replace(JwtUtil.TOKEN_PREFIX, ""));
        return customToken;

    }

    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(WebUtils.toHttp(request).getMethod().equals(RequestMethod.OPTIONS.name()))
        {
            return true;
        } else {
            return false;
        }


//        try {
//            return executeLogin(request, response);
//            // return true;有一篇博客这里直接返回true是不正确的,在这里我特别指出一下
//        } catch (Exception e) {
//            // log.error("JwtFilter过滤验证失败!");
//            return false;
//        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = WebUtils.toHttp(request).getHeader(JwtUtil.TOKEN_HEADER);
        if(StringUtils.isEmpty(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

//            httpResponse.getWriter().print("error 请求有误");
            return false;
        }

        return executeLogin(request, response);
    }
    /**
     * 对跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
//        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpServletResponse.setStatus(HttpStatus.OK.value());
//            return false;
//        }
//        return super.preHandle(request, response);
//    }

}
