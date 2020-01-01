package com.changwu.questionnaire.repository;

import com.changwu.questionnaire.bean.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Changwu
 * @Date: 2020-01-01 9:13
 */
public interface QuestionRepository extends JpaRepository<Question,Integer> {

}
