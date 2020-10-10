package com.xusi.blog.security.config.realm;

import com.xusi.blog.security.entity.BlogRole;
import com.xusi.blog.security.entity.BlogUser;
import com.xusi.blog.security.entity.BlogUserToRole;
import com.xusi.blog.security.service.impl.BlogRoleServiceImpl;
import com.xusi.blog.security.service.impl.BlogUserServiceImpl;
import com.xusi.blog.security.service.impl.BlogUserToRoleServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import javax.annotation.Resource;


/**
 * @program: IntelliJ IDEA
 * @description: 数据库获取
 * @author: xusi
 * @create:2020-10-10 14:30
 **/
public class DaoRealm extends AuthorizingRealm {

    @Resource
    private BlogUserServiceImpl userService;
    @Resource
    private BlogUserToRoleServiceImpl u2rService;
    @Resource
    private BlogRoleServiceImpl roleService;

    // 验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = token.getPrincipal().toString();
        char[] password = upToken.getPassword();
        // 如果用户不为空
        BlogUser user = userService.getOneByName(username);
        if(user == null) return null;
        // 传入的是数据库中的对象还是 token ？
        return new SimpleAuthenticationInfo(user.getUserName(),password,this.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        System.out.println(principals);
        // 获取 user 对应的 role
        BlogUser user = userService.getOneByName("admin");
        BlogUserToRole u2r = u2rService.getOneByUserId(user.getId());
        BlogRole role = roleService.getById(u2r.getRoleId());

        // 设置权限
        info.addRole(role.getRoleName());
        return info;
    }
}
