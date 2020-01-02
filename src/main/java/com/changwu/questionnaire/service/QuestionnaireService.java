package com.changwu.questionnaire.service;

import com.changwu.questionnaire.bean.Answer;
import com.changwu.questionnaire.bean.Paper;
import com.changwu.questionnaire.bean.Question;
import com.changwu.questionnaire.dto.Option;
import com.changwu.questionnaire.dto.PageBean;
import com.changwu.questionnaire.dto.QuestionnaireDto;
import com.changwu.questionnaire.exception.CustomException;
import com.changwu.questionnaire.repository.AnswerRepository;
import com.changwu.questionnaire.repository.PaperRepository;
import com.changwu.questionnaire.repository.QuestionRepository;
import com.changwu.questionnaire.typeEnum.QuestionTypeEnum;
import com.changwu.questionnaire.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Changwu
 * @Date: 2020-01-01 9:05
 */
@Service
public class QuestionnaireService {

    @Autowired
    PaperRepository paperRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    /**
     * 保存问卷草稿 , 将前端提交过来的问卷拆分保存进不同的表中
     *
     * @param dto
     * @param userId
     */
    @Transactional
    public void addQuestionnaire(QuestionnaireDto dto, String userId) {
        // 再校验一下, 此时token & userId是否过期了
        if (userId == null) {
            System.out.println("用户身份已过期");
            throw new CustomException("用户身份已过期,请重新登录", 50012);
        }
        // 问卷标题
        String title = dto.getTitle().trim();
        Date endTime = dto.getExipreTime();

        // 保存问卷信息
        Paper paper = new Paper(Integer.valueOf(userId), title, dto.getExipreTime());

        // 如果id不为空,则说明本次save实际上是进行修改
        if (dto.getId() != null) {
            paper.setId(dto.getId());
        }
        Paper save = paperRepository.save(paper);
        if (save == null) {
            throw new CustomException("问卷保存失败，请重试, 或联系管理员", 50008);
        }

        String questionTitle = null;
        Integer paperId = save.getId();
        Question q = null;

        ArrayList<Option> questions = dto.getQuestions();
        for (Option item : questions) {
            if (item.getType().equals("radio")) {// 处理单选
                questionTitle = item.getTitle().trim();
                List<String> answers = item.getAnswers();
                List<String> answersData = item.getAnswersData();
                // 保存问题
                Question question = new Question(paperId, QuestionTypeEnum.RadioQuestion.getQuestionType(), questionTitle, JsonUtils.objectToJson(answers), JsonUtils.objectToJson(answersData));
                if (item.getId() != null) {
                    question.setId(item.getId());
                }
                q = questionRepository.save(question);
            } else if (item.getType().equals("checkbox")) {// 处理多选
                questionTitle = item.getTitle().trim();
                List<String> answers = item.getAnswers();
                List<String> answersData = item.getAnswersData();
                // 保存问题
                Question question = new Question(paperId, QuestionTypeEnum.CheckBoxQuestion.getQuestionType(), questionTitle, JsonUtils.objectToJson(answers), JsonUtils.objectToJson(answersData));
                if (item.getId() != null) {
                    question.setId(item.getId());
                }
                q = questionRepository.save(question);
            } else if (item.getType().equals("text")) {// 处理文本题
                questionTitle = item.getTitle().trim();
                String text = item.getText();
                String required = item.getRequired();
                // 保存问题
                Question question = new Question(paperId, QuestionTypeEnum.TextQuestion.getQuestionType(), questionTitle, "-", text, required);
                if (item.getId() != null) {
                    question.setId(item.getId());
                }
                q = questionRepository.save(question);
            }
            // 校验
            if (q == null) {
                throw new CustomException("问题保存失败，请重试, 或联系管理员", 50008);
            }
        }
    }


}
