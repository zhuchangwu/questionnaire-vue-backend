package com.changwu.questionnaire.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: Changwu
 * @Date: 2019-12-30 18:32
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    // 处理异常
    MyAuthenticationEntryPoint build(){
        return new MyAuthenticationEntryPoint();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure (HttpSecurity http)throws Exception{
        // 全局禁用cookie
        http.csrf().disable();

        // 全局禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 添加路由
        http.authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/logout").permitAll()
                .anyRequest().authenticated();

        // 处理异常
        http.exceptionHandling().authenticationEntryPoint(build());

        // 将自定义的过滤器添加进Spring Security
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    }



}
