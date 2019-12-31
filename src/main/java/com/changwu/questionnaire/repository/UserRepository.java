package com.changwu.questionnaire.repository;

import com.changwu.questionnaire.bean.Role;
import com.changwu.questionnaire.bean.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Changwu
 * @Date: 2019-12-30 17:19
 */
@Repository
public class UserRepository {

    public User findUserByUsername(String username) {
        List<Role> list = new ArrayList<>();
        list.add(Role.ROLE_ADMIN);
        User user = new User("昌武", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif", list);
        user.setId(1);
        return user;
    }
}
