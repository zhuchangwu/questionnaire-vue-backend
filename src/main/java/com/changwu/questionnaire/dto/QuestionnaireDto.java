package com.changwu.questionnaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;

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
  private Integer userId;
  private String title;
  private ArrayList<Option> Questions;
  private Date exipreTime;
}
