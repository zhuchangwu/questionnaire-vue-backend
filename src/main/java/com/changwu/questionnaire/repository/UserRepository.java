package com.changwu.questionnaire.repository;

import com.changwu.questionnaire.bean.User;
import com.changwu.questionnaire.repository.projection.UserProjection;
import com.changwu.questionnaire.typeEnum.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Changwu
 * @Date: 2019-12-30 17:19
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {


    default User findUserByUsername(String username) {
        List<Role> list = new ArrayList<>();
        list.add(Role.ROLE_ADMIN);
        User user = new User("昌武", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif", list);
        user.setId(1);
        return user;
    }

    /**
     * 更新用户
     *
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "update user set username=?1,name=?2,phone=?3,email=?4, roles=?5  where id=?6",nativeQuery=true)
   int updateUser(String username,String name,String phone,String email,String roles,Integer id);
}
