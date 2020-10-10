package com.xusi.blog.security.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xusi.blog.common.R;
import com.xusi.blog.common.utils.JwtUtil;
import com.xusi.blog.common.utils.PasswordUtil;
import com.xusi.blog.security.entity.BlogRole;
import com.xusi.blog.security.entity.BlogUser;
import com.xusi.blog.security.entity.BlogUserToRole;
import com.xusi.blog.security.service.impl.BlogUserServiceImpl;
import com.xusi.blog.security.service.impl.BlogUserToRoleServiceImpl;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xusi
 * @since 2020-09-13
 */
@RestController
@RequestMapping("/api/security/user")
@Api(value = "/user",tags = "博客:用户管理")
public class BlogUserController {

    @Autowired
    private BlogUserServiceImpl userService;
    @Autowired
    private BlogUserToRoleServiceImpl b2rService;


    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private PasswordUtil passwordUtil;

    private static final Logger log = LoggerFactory.getLogger(BlogUserController.class);

    @PostMapping("/registerByEntity")
    public R registerByEntity(BlogUser user){
        userService.save(user);
        BlogUserToRole b2r = new BlogUserToRole();
        b2r.setUserId(user.getId());
        b2r.setRoleId(2L);
        b2rService.save(b2r);
        return R.ok(200).message("注册成功");
    }
//
//    @PostMapping("/register")
//    public R register(String username, String password){
//        log.info("开始注册");
////        username not in database
//        if (userService.isExistByName(username)){
//            return R.error(500).message("username is exist");
//        }
////        Bcryt pw
//        String bcrytPw = passwordEncoder.encode(password);
////        wrapper user
//        BlogUser user = new BlogUser();
//        user.setUserName(username);
//        user.setUserPw(bcrytPw);
//        userService.save(user);
////      级联绑定权限,默认为user
//        log.info("userid:"+user.getId().toString());
//        BlogUserToRole b2r = new BlogUserToRole();
//        b2r.setUserId(user.getId());
//        b2r.setRoleId(2L);
//        b2rService.save(b2r);
//        return R.ok(200).message("register success");
//    }

    @PutMapping("/update")
    public R update(String username,String password,BlogUser user){
        QueryWrapper<BlogUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        wrapper.eq("user_pw",password);
        BlogUser checkUser = userService.getOne(wrapper);
        if(checkUser == null || !user.getUserName().equals(checkUser.getUserName())){
            return R.error(500).message("user no exist");
        }
        user.setUserName(username);
        userService.save(user);
        return R.ok(200);
    }
//    @PostMapping("/login")
//    public R login(String username, String password){
//
//        UsernamePasswordAuthenticationToken  authenticationToken =
//                new UsernamePasswordAuthenticationToken(username,passwordEncoder.encode(password));
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        log.info(SecurityContextHolder.getContext().getAuthentication().getDetails().toString());
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
//        Map<String, String> map = passwordUtil.getToken(userDetails);
//        // 生成token
//        String token = jwtUtil.createToken(username,map.get("authorities"),false);
//        return R.ok(200).data("data",token);
//
//    }

}

