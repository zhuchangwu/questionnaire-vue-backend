package com.changwu.questionnaire.security;


import com.changwu.questionnaire.bean.Role;
import com.changwu.questionnaire.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 针对请求进行具体的处理
 * 1. 生成token
 * 2. 解析验证token
 * @Author: Changwu
 * @Date: 2019-12-30 17:03
 */
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMillseconds = 36000000; // 30Day

    @Autowired
    private MyUserDetailService myUserDetailService;


    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }


    public String createToken(String username, List<Role> roleList){
        // subject为用户名
        Claims claims = Jwts.claims().setSubject(username); // 往里面添加一个键值对, key=auth 值为user的拥有的所有的角色的集合
        claims.put("auth",roleList.stream().map(item->new SimpleGrantedAuthority(item.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));
        // 当前时间
        Date now = new Date();
        // 当前过期时间
        Date validity = new Date(now.getTime() + validityInMillseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public Authentication getAuthentication(String token) {
      UserDetails details =  myUserDetailService.loadUserByUsername(getUsername(token));
      return new UsernamePasswordAuthenticationToken(details,"",details.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolve(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authrication");
        if (bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            throw  new CustomException("invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
