package com.changwu.questionnaire.security;

import com.changwu.questionnaire.utils.JsonUtils;
import com.changwu.questionnaire.vo.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Changwu
 * @Date: 2019-12-30 19:06
 */

public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        // 允许跨域
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(200);

        // 用户名或密码错误
        if (e instanceof BadCredentialsException) {
            httpServletResponse.getWriter().write(mapper.writeValueAsString(JsonUtils.objectToJson(new JSONResult(50008,"用户名或密码错误"))));
        }
        if (e instanceof InsufficientAuthenticationException) {
            httpServletResponse.getWriter().write(mapper.writeValueAsString(JsonUtils.objectToJson(new JSONResult(50012,"您没有相关权限, 请联系管理员"))));
        }
        System.out.println("MyAuthenticationEntryPoint  invoke");
        System.out.println("MyAuthenticationEntryPoint  exception "+e.getClass());
    }
}
