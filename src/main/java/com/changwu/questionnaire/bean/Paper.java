package com.changwu.questionnaire.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 问卷的实体类
 *
 * @Author: Changwu
 * @Date: 2019-12-31 16:47
 */
@Entity
@Table(name = "paper")
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 外键, 创建问卷的用户ID
    @Column(name = "user_id",columnDefinition = "int(11)", nullable = false)
    private Integer userId;

    // 问卷的标题
    @Column(name = "title",columnDefinition = "varchar(128)",nullable = false)
    private String title;

    // 创建时间
    @Column(name = "create_time",columnDefinition = "date",nullable = false)
    private Date createTime;

    // 问卷的状态
    // 0：未发布
    // 1：已发布
    // 2：已结束
    // 3：已删除
    @Column(name = "status",columnDefinition = "int(1) default 0")
    private int status;

    // 截止日期
    @Column(name = "end_time",columnDefinition = "date",nullable = false)
    private Date endTime;

    // 问卷与用户 多对一
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id",referencedColumnName = "id", insertable = false , updatable = false)
    private User user;

    // 问卷与问题 一对多
    @OneToMany(mappedBy = "paper",cascade=CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    @Transient
    private String userName;

    public Paper() {

    }

    public Paper(Integer userId, String title, Date endTime) {
        this.userId = userId;
        this.title = title;
        this.createTime = new Date();
        this.endTime = endTime;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonBackReference
    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }
    @JsonBackReference
    public void setUser(User user) {
        this.user = user;
    }
}
