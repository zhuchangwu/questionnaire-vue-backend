package com.changwu.questionnaire.security;


import com.changwu.questionnaire.typeEnum.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

   // @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMillseconds = 86400000; // 30Day86400000

    @Autowired
    private MyUserDetailService myUserDetailService;

    @PostConstruct
    protected void init() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }

    public String createToken(String username, List<Role> roleList,Integer userId){
        // subject为用户名
        Claims claims = Jwts.claims().setSubject(username); // 往里面添加一个键值对, key=auth 值为user的拥有的所有的角色的集合
        claims.setId(String.valueOf(userId));
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

    // 从用户的token中解析出用户的Authentication信息, 封装进 UsernamePasswordAuthenticationToken中
    public Authentication getAuthentication(String token) {
        // 根据用户名查询出从数据库中查询出用户的信息
      UserDetails details =  myUserDetailService.loadUserByUsername(getUsername(token));
      return new UsernamePasswordAuthenticationToken(details,"",details.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getId();
    }

    public String resolve(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authrication");
        if (bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
            // 如果解析失败了, 将会把异常扔出去, 在MyAuthentication中进行统一的处理
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
    }


}
