package com.changwu.questionnaire.security;

import com.changwu.questionnaire.exception.CustomException;
import com.changwu.questionnaire.utils.JsonUtils;
import com.changwu.questionnaire.vo.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤所有的请求, 并将所有的请求传递到JwtTokenProvider中进一步处理
 * 将被添加到过滤的最前面
 *
 * @Author: Changwu
 * @Date: 2019-12-30 17:02
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider provider) {
        this.jwtTokenProvider = provider;
    }

    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String method = httpServletRequest.getMethod();
        if ("OPTIONS".equals(method)) {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            httpServletResponse.setHeader("Access-Control-Allow-Headers","Origin,x-token, X-Requested-With, Content-Type, Accept, Authrication");
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.getWriter().write(JsonUtils.objectToJson(new JSONResult(200,"ok")));
            return;
        }


        String token = jwtTokenProvider.resolve(httpServletRequest);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                // 将构建好用户的权限信息添加进SpringSecurity的上下文中, 接下来可以在控制器上添加注解进行权限验证
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            System.out.println("----------------------ExpiredJwtException---------------------------");
            System.out.println(e.getClass());
            SecurityContextHolder.clearContext();
            ObjectMapper mapper = new ObjectMapper();
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setStatus(200);
            // todo  等前端写当相关的方法时再进行验证
            if (e instanceof ExpiredJwtException) {
                httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
                httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
                httpServletResponse.setHeader("Access-Control-Allow-Headers","Origin,x-token, X-Requested-With, Content-Type, Accept, Authrication");
                httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
                httpServletResponse.getWriter().write(mapper.writeValueAsString(JsonUtils.objectToJson(new JSONResult(50014,"对不起, 您的凭证已过期, 请重新登录"))));
            }
            return;
        }
        System.err.println("====================================================");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
