package com.changwu.questionnaire.service;

import com.changwu.questionnaire.bean.User;
import com.changwu.questionnaire.repository.UserRepository;
import com.changwu.questionnaire.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: Changwu
 * @Date: 2019-12-30 19:36
 */
@Service
public class UserService {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository userRepository;
    /**
     * 登录
     *
     * @return
     */
    public String login(String username, String password) {

        // AuthenticationManager 的实现类是 ProviderManager, 目的是鉴证用户名和密码是否是正确的
        // AuthenticationManager 会将这个事件委托给 AuthenticationProvider列表  分别经过 AnonymousAuthenticationProvider -> DaoAuthenticationProvider
        // 换句话说, 如果前端传递进来的用户名和密码不正确的的, 在这里会被检查出来, 然后报异常, 由SpringSecurity的ExceptionHandler具体处理
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepository.findUserByUsername(username);

        // todo 此时不能从上下文中获取用户的信息, 获取出来的结果是  匿名用户   ?????
        // 如果是管理员, 返回    token.admin
        // 如果是普通用户, 返回  token.editor
        return jwtTokenProvider.createToken(username,user.getRoles(),user.getId());
    }

    public User findUserByUsername(String username) {
      return   userRepository.findUserByUsername(username);
    }
}
