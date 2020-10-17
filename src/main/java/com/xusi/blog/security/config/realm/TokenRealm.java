package com.xusi.blog.security.config.realm;

import com.xusi.blog.common.utils.JwtUtil;
import com.xusi.blog.security.config.CustomToken;
import com.xusi.blog.security.entity.BlogRole;
import com.xusi.blog.security.entity.BlogUser;
import com.xusi.blog.security.entity.BlogUserToRole;
import com.xusi.blog.security.service.impl.BlogRoleServiceImpl;
import com.xusi.blog.security.service.impl.BlogUserServiceImpl;
import com.xusi.blog.security.service.impl.BlogUserToRoleServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-17 12:25
 **/

public class TokenRealm extends AuthorizingRealm {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private BlogUserServiceImpl userService;

    @Resource
    private BlogUserToRoleServiceImpl u2rService;
    @Resource
    private BlogRoleServiceImpl roleService;


    /**
     * 必须重写此方法，不然Shiro会报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        System.out.println(principals);
        // 获取 user 对应的 role
        BlogUser user = userService.getOneByName(principals.toString());
        BlogUserToRole u2r = u2rService.getOneByUserId(user.getId());
        BlogRole role = roleService.getById(u2r.getRoleId());

        // 设置权限
        info.addRole(role.getRoleName());
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String upToken = (String)token.getCredentials();
        String username = jwtUtil.getSubject(upToken);
        if (username == null) {
            throw new AuthenticationException("token无效");
        }
        if(JwtUtil.isExpiration(upToken)){
            throw new AuthenticationException("token过期");
        }
        BlogUser user = userService.getOneByName(username);
        if(user == null){
            throw new AuthenticationException("用户不存在");
        }
        // 创建 token 的时候没有存储密码
//        Claims claims = JwtUtil.getTokenBody(upToken);
//        String pw = claims.get("password").toString();
//        if(!pw.equals(user.getUserPw())){
//            throw new AuthenticationException("密码不匹配");
//        }

        return new SimpleAuthenticationInfo(upToken,token.getCredentials(),getName());
    }
}
