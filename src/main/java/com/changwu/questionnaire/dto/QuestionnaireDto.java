package com.changwu.questionnaire.dto;

import com.changwu.questionnaire.bean.Answer;
import com.changwu.questionnaire.bean.Paper;
import com.changwu.questionnaire.bean.Question;
import com.changwu.questionnaire.typeEnum.QuestionTypeEnum;
import com.changwu.questionnaire.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * 问卷DTO, 用户前后端中转数据使用
 *
 * @Author: Changwu
 * @Date: 2019-12-31 20:04
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireDto {
  //根据id区分是添加, 还是修改
  private Integer id;
  private String title;
  private ArrayList<Option> Questions;
  private Date exipreTime;

  /**
   * 将Paper中的信息解析到DTO中
   * @param paper
   */
  public QuestionnaireDto(Paper paper) {
    this.id=paper.getId();
    this.title=paper.getTitle();
    this.exipreTime=paper.getEndTime();
    this.Questions = new ArrayList<>();
    Set<Question> questions = paper.getQuestions();

    for (Question question : questions) {
        if (question.getQuestionType().equals(QuestionTypeEnum.RadioQuestion.getQuestionType())){
          Option option = new Option();
          option.setId(question.getId());
          option.setTitle(question.getQuestionTitle());
          option.setType("radio");
          option.setAnswers(JsonUtils.jsonToList(question.getQuestionOption(),String.class));
          option.setAnswersData(JsonUtils.jsonToList(question.getQuestionAnswer(),String.class));

          Questions.add(option);
          System.out.println("-------------RadioQuestion------------------------");
        }else if (question.getQuestionType().equals(QuestionTypeEnum.CheckBoxQuestion.getQuestionType())){
          Option option = new Option();
          option.setId(question.getId());
          option.setTitle(question.getQuestionTitle());
          option.setType("checkbox");
          option.setAnswers(JsonUtils.jsonToList(question.getQuestionOption(),String.class));
          option.setAnswersData(JsonUtils.jsonToList(question.getQuestionAnswer(),String.class));

          Questions.add(option);
          System.out.println("-------------CheckBoxQuestion------------------------");
        }else if (question.getQuestionType().equals(QuestionTypeEnum.TextQuestion.getQuestionType())){
          Option option = new Option();
          option.setId(question.getId());
          option.setTitle(question.getQuestionTitle());
          option.setType("text");
          option.setRequired(question.getIsRequired());
          option.setText(question.getQuestionAnswer());

          Questions.add(option);
          System.out.println("-------------TextQuestion------------------------");
        }
    }
  }
}
