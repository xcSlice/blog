package com.xusi.blog.security.controller;

import com.xusi.blog.common.utils.JwtUtil;
import com.xusi.blog.security.config.CustomMather;
import com.xusi.blog.security.entity.BlogUser;
import com.xusi.blog.security.service.impl.BlogUserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-10 14:59
 **/
@Controller
public class ShiroController {
    @Resource
    BlogUserServiceImpl userService;
    @Resource
    private JwtUtil jwtUtil;
//    @Resource
//    private CustomMather customMather;

    private static final Logger log = LoggerFactory.getLogger("shiro controller");

    @GetMapping("/test")
    public String index(){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("root","root");
        subject.login(token);
        System.out.println(subject);
        subject.isAuthenticated();
//
        return "/index";
    }

    @GetMapping("/register")
    public void register(){
        BlogUser user = new BlogUser();
        user.setUserName("root");
        String pwd = new Md5Hash("root").toString();
        log.info(pwd); //63a9f0ea7bb98050796b649e85481845
        user.setUserPw(pwd);
        if (userService.isExistByName("root")) {
            log.info("用户已存在");
            return;
        }
        userService.save(user);
        log.info("reg success");
    }

    @PostMapping("/login")
    public ResponseEntity login(String username,String password){
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
//        subject.login(token);
//        System.out.println(subject);
        Md5Hash md5Hash = new Md5Hash(password);
        String md5Pw = md5Hash.toString();
        String upToken = JwtUtil.TOKEN_PREFIX + jwtUtil.createToken(username,md5Pw,false);
//
        return ResponseEntity.ok(upToken);
    }
}
