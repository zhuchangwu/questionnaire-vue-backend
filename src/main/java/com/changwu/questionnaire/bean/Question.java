package com.changwu.questionnaire.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 问题的实体类
 *
 * @Author: Changwu
 * @Date: 2019-12-31 16:38
 */
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 创建日期
    @Column(name = "create_time",columnDefinition = "date" ,nullable = false)
    private Date createTime;

    // 问题类型
    // 1：单选
    // 2：多选
    // 3：简答
    @Column(name = "question_type",columnDefinition = "int(1)",nullable = false)
    private Integer questionType;

    // 问题题目
    @Column(name = "question_title",columnDefinition = "varchar(128)",nullable = false)
    private String questionTitle;

    // 问题所有选项 [option1,option2,option3...]
    @Column(name = "question_option",columnDefinition = "varchar(512)",nullable = false)
    private String questionOption;

    // 问题的标准答案
    @Column(name = "question_answer",columnDefinition = "varchar(512)")
    private String questionAnswer;

    // 是否必填
    // false 不必须
    // ture 必须
    @Column(name = "is_required",columnDefinition = "varchar(5) default ''")
    private String isRequired;

    // 维护 问题与问卷多对一的关系
    @ManyToOne(targetEntity = Paper.class)
    @JoinColumn(name = "paper_id",referencedColumnName = "id")
    private Paper paper;

    // 维护 问题与回答一对多的关系
    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Answer> anwers = new HashSet<>();

    public Question() {

    }

    public Question(Integer paperId, Integer questionType, String questionTitle, String questionOption, String questionAnswer) {
        this.createTime = new Date();
        this.questionType = questionType;
        this.questionTitle = questionTitle;
        this.questionOption = questionOption;
        this.questionAnswer = questionAnswer;
    }

    public Question(Integer paperId, Integer questionType, String questionTitle, String t, String answer, String required) {
        this.createTime = new Date();
        this.questionType = questionType;
        this.questionTitle = questionTitle;
        this.questionOption = t;
        this.questionAnswer = answer;
        this.isRequired=required;
    }


    public Paper getPaper() {
        return paper;
    }

    @JsonBackReference
    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Set<Answer> getAnwers() {
        return anwers;
    }

    @JsonBackReference
    public void setAnwers(Set<Answer> anwers) {
        this.anwers = anwers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(String questionOption) {
        this.questionOption = questionOption;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }
}
