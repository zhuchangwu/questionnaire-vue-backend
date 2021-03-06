package com.changwu.questionnaire.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户对问题回答的封装类
 * @Author: Changwu
 * @Date: 2019-12-31 16:52
 */
@Entity
@Table(name = "answer")
@ToString

public class Answer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    // 当前answer 对应的QuestionId
    @Column(name = "paper_id",columnDefinition = "int(11)",nullable = false)
    private Integer paperId;

    // 当前answer 对应的QuestionId
    @Column(name = "user_id",columnDefinition = "int(11)",nullable = false)
    private Integer userId;

    // 问题的类型
    // 1：单选
    // 2：多选
    // 3：简答
    @Column(name = "question_type",columnDefinition = "int(1)")
    private int questionType;

    // 问题的标题
    @Column(name = "title",columnDefinition = "varchar(128)",nullable = false)
    private String title;

    // 答题时间
    @Column(name = "create_time",columnDefinition = "date",nullable = false)
    private Date createTime;

    // 用户提交的答案
    // 如果题目是简答题, 封装成 ["answer"]
    @Column(name = "answer_option",columnDefinition = "varchar(512)",nullable = false)
    private String answerOption;

    @ManyToOne(targetEntity = Question.class)
    @JoinColumn(name = "question_id",referencedColumnName = "id")
    private Question question;

    public Answer() {
    }

    public Answer(Integer questionId, Integer paperId, Integer userId, int questionType, String title, String answerOption) {
        this.paperId = paperId;
        this.userId = userId;
        this.questionType = questionType;
        this.title = title;
        this.createTime = new Date();
        this.answerOption = answerOption;
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

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
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

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    public Question getQuestion() {
        return question;
    }
    @JsonBackReference
    public void setQuestion(Question question) {
        this.question = question;
    }
}
