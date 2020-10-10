package com.xusi.blog;

import com.xusi.blog.common.utils.JwtUtil;
import com.xusi.blog.common.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class BlogApplicationTests {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;


    @Test
    void shiro(){
        Subject subject = SecurityUtils.getSubject();
        System.out.println("subject = " + subject);
    }

}
