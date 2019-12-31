package com.changwu.questionnaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

/**
 * 封装单个问题
 *
 * @Author: Changwu
 * @Date: 2019-12-31 20:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Option {
    // 单个问题的名称
    private String title;
    // 单个问题的类型
    // radio
    // checkbox
    // text
    private String type;
    // 是否是必填的
    private String required;
    // 问题的描述
    private ArrayList<String> answers;
    // 问题的参考答案
    private ArrayList<String> answersData;

    // 文本问题的参考答案
    private String text;

}
