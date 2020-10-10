package com.xusi.blog.security.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xusi.blog.common.utils.PasswordUtil;
import com.xusi.blog.common.utils.RedisUtil;
import com.xusi.blog.security.entity.BlogRole;
import com.xusi.blog.security.entity.BlogUser;
import com.xusi.blog.security.entity.BlogUserToRole;
import com.xusi.blog.security.service.impl.BlogRoleServiceImpl;
import com.xusi.blog.security.service.impl.BlogUserServiceImpl;
import com.xusi.blog.security.service.impl.BlogUserToRoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-09-13 16:42
 **/

public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private BlogUserServiceImpl userService;
    @Resource
    private BlogRoleServiceImpl roleService;
    @Resource
    private BlogUserToRoleServiceImpl b2rService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private PasswordUtil passwordUtil;

    private static final Logger log = LoggerFactory.getLogger("user获取");
    private String authorities;



        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



            log.info("开始获取:"+username+",redis中是否存在");
            String key = "user:"+username;

            UserDetails userDetails;
            Map<String,String > token = (Map<String, String>) redisUtil.get(key);
            if (token != null){
                this.authorities = token.get("authorities");
                userDetails = User.withUsername(token.get("username")).password(token.get("password")).roles(token.get("authorities")).build();
                log.info("存在:"+userDetails.toString());
                return userDetails;
            }else {
                log.info("不存在");
            }


//        get user bu username
            QueryWrapper<BlogUser> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("user_name",username);
            BlogUser user = userService.getOne(userQueryWrapper);

//        user not null
            if(user == null) {
                log.info("null");
                return null;
            }

            QueryWrapper<BlogUserToRole> blogUserToRoleQueryWrapper = new QueryWrapper<>();
            blogUserToRoleQueryWrapper.eq("user_id",user.getId());
            BlogUserToRole blogUserToRole = b2rService.getOne(blogUserToRoleQueryWrapper);

//        not null
            if(blogUserToRole == null) {log.info("b2r is null");return null;}

            QueryWrapper<BlogRole> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.eq("id",blogUserToRole.getRoleId());
            BlogRole role  = roleService.getOne(roleQueryWrapper);

//        role not null
            if (role == null) {
                log.info("role is null");
                return null;
            }

//        wrapper userdetails to return

            log.info(role.getRoleName());
            userDetails = User.withUsername(user.getUserName()).password(user.getUserPw()).roles(role.getRoleName())
                    .build();
            log.info("验证成功,注入用户信息到redis中");
            token = passwordUtil.getToken(userDetails);
            redisUtil.set(key,token);
            redisUtil.expire(key,60*60*24);
            this.authorities = token.get("authorities");
            log.info("userDetails"+token.toString());
            log.info(userDetails.getAuthorities().toString());
            return userDetails;


        }

    public String getAuthorities(){
        return this.authorities;
    }
}
