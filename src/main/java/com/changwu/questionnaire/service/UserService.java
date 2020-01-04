package com.changwu.questionnaire.service;

import com.changwu.questionnaire.bean.User;
import com.changwu.questionnaire.dto.UserDto;
import com.changwu.questionnaire.exception.CustomException;
import com.changwu.questionnaire.repository.UserRepository;
import com.changwu.questionnaire.repository.projection.UserProjection;
import com.changwu.questionnaire.security.JwtTokenProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

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
        return jwtTokenProvider.createToken(username, user.getRoles(), user.getId());
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    // 分页查询用户的信息
    @Transactional
    public Page getUserByPage(Integer from, Integer limit, String name) {
        if (from <= 0) {
            from = 0;
        } else {
            from--;
        }
        if (limit < 10) {
            limit = 10;
        }
        Pageable pageable = PageRequest.of(from, limit);
        Page<User> page = userRepository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicateList = new ArrayList<>();
                if (name != null) {
                    Path labelname = root.get("username");
                    Predicate like = criteriaBuilder.like(labelname.as(String.class), "%" + name + "%");
                    predicateList.add(like);
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                predicateList.toArray(predicates);
                // 参数位置支持可变参数, 但是我们要尽量传递进去有效的条件
                return criteriaBuilder.and(predicates);
            }
        }, pageable);
        return page;
    }

    /**
     * 禁用user
     *
     * @param id
     */
    @Transactional
    public void forbidUser(@RequestParam Integer id) {
        Optional<User> byId = userRepository.findById(id);

        if (!byId.isPresent()) {
            throw new CustomException("用户不存在", 50008);
        }

        User user = byId.get();
        if (user.getStatus() == 1) {
            throw new CustomException("用户已经处于禁用状态", 50008);
        }
        user.setStatus(1);
        userRepository.save(user);
    }

    /**
     * 激活用户
     *
     * @param id
     */
    public void activeUser(@RequestParam Integer id) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException("用户不存在", 50008);
        }

        User user = byId.get();
        if (user.getStatus() == 0) {
            throw new CustomException("用户已经处于激活状态", 50008);
        }
        user.setStatus(0);
        userRepository.save(user);
    }

    // 回显用户的信息
    public User edit(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new CustomException("用户不存在", 50008);
        }
        return optionalUser.get();
    }


    @Transactional
    public void doEdit(UserDto user) {
        String role = user.getRolesDto().equals("管理员") ? "ROLE_ADMIN" : "ROLE_USER";
        int i = userRepository.updateUser(user.getUsername(), user.getName(), user.getPhone(), user.getEmail(), role, user.getId());
        if (i == 0) {
            throw new CustomException("修改失败", 50008);
        }
    }
}
