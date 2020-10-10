package com.xusi.blog;

import com.xusi.blog.common.utils.JwtUtil;
import com.xusi.blog.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BlogApplicationTests {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testJwt(){
        String token = jwtUtil.createToken("admin","SUPER",false);
        System.out.println("token = " + token);
    }


    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode("admin");
        System.out.println("pw = " + pw);
    }


    @Test
    void testRedis(){
        redisTemplate.opsForValue().set("xusi","test");
        System.out.println(redisTemplate.opsForValue().get("xusi"));
    }

    @Test
    void redis(){

        redisUtil.set("testvalue","1234");
        System.out.println(redisUtil.get("testvalue"));
    }

    @Test
    void fastjson(){
    }

}
