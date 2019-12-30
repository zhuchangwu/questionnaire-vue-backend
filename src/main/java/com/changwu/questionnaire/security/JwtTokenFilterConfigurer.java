package com.changwu.questionnaire.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 将我们自己的过滤器添加到 SpringSecurity UsernamePasswordAuthenticationFilter之前
 *
 * @Author: Changwu
 * @Date: 2019-12-30 18:27
 */
public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JwtTokenProvider provider;

    public JwtTokenFilterConfigurer(JwtTokenProvider provider){
        this.provider = provider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenFilter filter = new JwtTokenFilter(this.provider);
        http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);

    }

}
