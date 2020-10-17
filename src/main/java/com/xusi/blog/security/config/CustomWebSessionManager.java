package com.xusi.blog.security.config;



import com.xusi.blog.common.utils.JwtUtil;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @program: IntelliJ IDEA
 * @description: 自定义的session管理器
 * @author: xusi
 * @create:2020-10-17 11:09
 **/
public class CustomWebSessionManager extends DefaultWebSessionManager {
    public CustomWebSessionManager(){
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {

        String id = WebUtils.toHttp(request).getHeader(JwtUtil.TOKEN_HEADER);
        if(!StringUtils.isEmpty(id) && id.startsWith(JwtUtil.TOKEN_PREFIX)){
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,"Stateless request");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE);
            return id;
        } else {
            return super.getSessionId(request, response);
        }
    }
}
