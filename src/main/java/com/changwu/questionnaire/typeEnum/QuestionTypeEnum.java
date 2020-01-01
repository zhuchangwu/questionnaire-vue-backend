package com.changwu.questionnaire.typeEnum;

/**
 * @Author: Changwu
 * @Date: 2020-01-01 9:29
 */
public enum QuestionTypeEnum {
    RadioQuestion(1),
    CheckBoxQuestion(2),
    TextQuestion(3)
    ;
    private Integer questionType;

    QuestionTypeEnum(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionType() {
        return questionType;
    }
}
