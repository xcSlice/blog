package com.xusi.blog.common.utils;


import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: IntelliJ IDEA
 * @description: 密码验证
 * @author: xusi
 * @create:2020-09-19 20:41
 **/
@Component
public class PasswordUtil {
//    @Resource
//    private BCryptPasswordEncoder passwordEncoder;

    public  boolean isValidPassword(String password, String encodePassword){
//        return passwordEncoder.matches(password,encodePassword);
        return true;
    }

//    public Map<String, String> getToken(UserDetails userDetails) {
//        String password = userDetails.getPassword();
//        String username = userDetails.getUsername();
//        String authorities = userDetails.getAuthorities().toString()
//                .replace("ROLE_","")
//                .replace("[","")
//                .replace("]","");
//        Map<String,String> map = new HashMap<>();
//        map.put("username",username);
//        map.put("password",password);
//        map.put("authorities",authorities);
//
//        return map;
//
//    }
}
