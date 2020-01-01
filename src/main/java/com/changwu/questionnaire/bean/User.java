package com.changwu.questionnaire.bean;

import com.changwu.questionnaire.typeEnum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Changwu
 * @Date: 2019-12-27 22:48
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 账号
    @JsonIgnore
    @Size(min = 6, max = 255, message = "Mininum username length: 6 character")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    // 密码
    @Size(min = 8, message = "Mininum password length: 8 character")
    @Column(name = "password", nullable = false, columnDefinition = "varchar(64)") //
    private String password;


    // 名称
    @Column(name = "name", columnDefinition = "varchar(64) default ''")
    private String name;

    // 联系方式
    @Column(name = "phone", unique = true, columnDefinition = "varchar(12)")
    private String phone;

    // 联系方式
    @Column(name = "email", columnDefinition = "varchar(64) default ''")
    private String email;

    // 状态
    // 0: 正常
    // 1: 禁用
    @Column(name = "status", columnDefinition = "int(1) default 0")
    private int status;

    // 头像
    @Column(name = "avatar", columnDefinition = "varchar(128) default ''")
    private String avatar;

    // 角色列表
    @Column(name = "roles", columnDefinition = "varchar(64) default ''")
    private String rolesDto;

    @Transient
    private List<Role> roles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Paper> papers = new HashSet<>();


    public User() {

    }

    public User(String name, String avatar, List<Role> roles) {
        this.name = name;
        this.avatar = avatar;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Paper> getPapers() {
        return papers;
    }

    public void setPapers(Set<Paper> papers) {
        this.papers = papers;
    }

    public String getRolesDto() {
        return rolesDto;
    }

    public void setRolesDto(String rolesDto) {
        this.rolesDto = rolesDto;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}