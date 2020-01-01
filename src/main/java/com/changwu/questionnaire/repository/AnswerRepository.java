package com.changwu.questionnaire.repository;

import com.changwu.questionnaire.bean.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Changwu
 * @Date: 2020-01-01 9:13
 */
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
}
