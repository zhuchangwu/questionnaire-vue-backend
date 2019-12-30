package com.changwu.questionnaire.security;

import com.changwu.questionnaire.bean.Role;
import com.changwu.questionnaire.bean.User;
import com.changwu.questionnaire.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Changwu
 * @Date: 2019-12-30 17:18
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username);

        // 一旦在下面填充 username password  roles 一但出现了 null , SpringSecurity都会认为数据库中是没有这个人的记录的
        // 因此会在　DaoAuthenticationProvider中 抛出 InternalAuthenticationServiceException
        return org.springframework.security.core.userdetails.User//
                .withUsername(username)//
                .password("$2a$12$bFCVWh/QGCbfHv38sIGMQ.0w6l3aOdL3kYcmV/OrKqoL66QNo4KH.")//
                .authorities(user.getRoles())//
                .accountExpired(false)// 不过期
                .accountLocked(false)// 未锁定
                .credentialsExpired(false)// 资格证书未过期
                .disabled(false)// 有效
                .build();
    }
}

