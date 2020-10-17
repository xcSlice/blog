package com.xusi.blog.security.config;

import com.xusi.blog.security.config.filter.CustomTokenFilter;
import com.xusi.blog.security.config.realm.DaoRealm;
import com.xusi.blog.security.config.realm.RedisRealm;
import com.xusi.blog.security.config.realm.TokenRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

import javax.servlet.Filter;
import java.util.*;

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
        filterMap.put("/login", "anon");
        filterMap.put("/index","anon");
        filterMap.put("/test/**","anon");
        filterMap.put("/","anon");
        filterMap.put("/register", "anon");


        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", new CustomTokenFilter());
        shiroFilterFactoryBean.setFilters(filters);
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterMap.put("/**", "jwt");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        return shiroFilterFactoryBean;
    }

    //    创建 SecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("daoRealm") DaoRealm daoRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        Collection<Realm> realms = new HashSet<>();
        realms.add(daoRealm);
        realms.add(tokenRealm());
        securityManager.setRealms(realms);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    //    创建自定义的 realm
    @Bean(name = "daoRealm")
    public DaoRealm daoRealm() {
        DaoRealm daoRealm = new DaoRealm();
        daoRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        开启缓存管理
//        daoRealm.setCacheManager(new RedisCacheManager());
//        daoRealm.setCachingEnabled(true);
////        开启全局授权缓存
//        daoRealm.setAuthorizationCachingEnabled(true);
////        开启全局验证缓存
//        daoRealm.setAuthenticationCachingEnabled(true);
        return daoRealm;
    }

    @Bean(name = "tokenRealm")
    public TokenRealm tokenRealm(){
        TokenRealm tokenRealm = new TokenRealm();
        return tokenRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        return hashedCredentialsMatcher;
    }



}