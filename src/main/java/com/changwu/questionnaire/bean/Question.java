package com.changwu.questionnaire.bean;

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

    //外键: 当前问题所属的问卷id
    @Column(name = "paper_id",columnDefinition = "int(11)",nullable = false,unique = true)
    private Integer paperId;

    // 创建日期
    @Column(name = "create_time",columnDefinition = "date" ,nullable = false)
    private Date createTime;

    // 问题类型
    // 1：单选
    // 2：多选
    // 3：简答
    @Column(name = "question_type",columnDefinition = "int(2)",nullable = false)
    private String questionType;

    // 问题题目
    @Column(name = "question_title",columnDefinition = "varchar(128)",nullable = false)
    private String questionTitle;

    // 问题所有选项 [option1,option2,option3...]
    @Column(name = "question_option",columnDefinition = "varchar(512)",nullable = false)
    private String questionOption;

    // 问题的标准答案
    @Column(name = "question_answer",columnDefinition = "varchar(512)")
    private String questionAnswer;

    // 维护 问题与问卷多对一的关系
    @ManyToOne(targetEntity = Paper.class)
    @JoinColumn(name = "paper_id",referencedColumnName = "id", insertable = false , updatable = false)
    private Paper paper;

    // 维护 问题与回答一对多的关系
    @OneToMany(mappedBy = "question")
    private Set<Answer> anwers = new HashSet<>();

    public Question() {
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Set<Answer> getAnwers() {
        return anwers;
    }

    public void setAnwers(Set<Answer> anwers) {
        this.anwers = anwers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
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
}
