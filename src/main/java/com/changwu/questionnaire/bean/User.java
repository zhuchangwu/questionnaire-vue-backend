package com.changwu.questionnaire.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Changwu
 * @Date: 2019-12-27 22:48
 */
public class User {
    Integer id;
    // 账号
    @JsonIgnore
    String username;
    // 密码
    String password;
    // 名称
    String name;
    // 头像
    String avatar;
    // 用户简介
    String introduction;
    // 角色列表
    List<Role> roles;

    public User(String name, String avatar, String introduction, List<Role> roles) {
        this.name = name;
        this.avatar = avatar;
        this.introduction = introduction;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List getRoles() {
        return this.roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
}
