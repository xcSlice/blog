package com.xusi.blog.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;


/**
 * @program: IntelliJ IDEA
 * @description: security配置
 * @author: xusi
 * @create:2020-09-13 16:40
 **/

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    TokenSuccessHandler tokenSuccessHandler(){
        return new TokenSuccessHandler();
    }
    @Bean
    RequestSuccessHandler requestSuccessHandler(){return new RequestSuccessHandler();}

    @Bean
    MyAuthenticationProvider myAuthenticationProvider(){
        return new MyAuthenticationProvider();
    }


    //    配置自定义的UserDetailsService
    @Bean
    public UserDetailsService myUserDetailsService(){
        return new MyUserDetailsService();
    }

//    密码加密策略
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    用户登录方式

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(myUserDetailsService()).and()
                .authenticationProvider(myAuthenticationProvider());
    }

//    拦截器配置

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//      关闭跨域
        http.cors()
                .and().csrf().disable().authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                .permitAll();
//                .and()
//                .formLogin()
//                .successForwardUrl("/index")
//                .successHandler(tokenSuccessHandler())
//                .successHandler(requestSuccessHandler())
//                .and()
//                .authorizeRequests()
//                .antMatchers("/security/**").permitAll()
//                .antMatchers(("/**")).hasAnyRole("SUPER","USER");
        http.logout();
        http.rememberMe().rememberMeParameter("rememberMe");
        http.addFilterBefore(new MyAuthenticationProcessingFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        //allow Swagger URL to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
                "/v3/**",
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html");
    }




}
