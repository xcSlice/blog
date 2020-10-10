package com.xusi.blog.security.config;

import com.xusi.blog.security.config.realm.DaoRealm;
import com.xusi.blog.security.config.realm.RedisRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: IntelliJ IDEA
 * @description: shiro配置
 * @author: xusi
 * @create:2020-10-10 14:29
 **/
@Configuration
public class ShiroConfig {

    //    创建过滤器
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
//        创建一个过滤器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        创建过滤规则
        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("/**", "authc");
        filterMap.put("/login", "anon");
        filterMap.put("/index","anon");
        filterMap.put("/test/**","anon");
        filterMap.put("/","anon");
        filterMap.put("/register", "anon");
        filterMap.put("/loginAndRegister/blog-user/register", "anon");
        filterMap.put("/loginAndRegister/blog-user/login", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");

        return shiroFilterFactoryBean;
    }

    //    创建 SecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("daoRealm") DaoRealm daoRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(daoRealm);
        return securityManager;
    }

    //    创建自定义的 realm
    @Bean(name = "daoRealm")
    public DaoRealm getRealm() {
        DaoRealm daoRealm = new DaoRealm();
//        开启缓存管理
//        daoRealm.setCacheManager(new RedisCacheManager());
//        daoRealm.setCachingEnabled(true);
////        开启全局授权缓存
//        daoRealm.setAuthorizationCachingEnabled(true);
////        开启全局验证缓存
//        daoRealm.setAuthenticationCachingEnabled(true);
        return daoRealm;
    }


}