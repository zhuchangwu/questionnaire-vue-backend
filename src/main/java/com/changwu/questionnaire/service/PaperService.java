package com.changwu.questionnaire.service;

import com.changwu.questionnaire.bean.Paper;
import com.changwu.questionnaire.dto.PageBean;
import com.changwu.questionnaire.dto.QuestionnaireDto;
import com.changwu.questionnaire.exception.CustomException;
import com.changwu.questionnaire.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author: Changwu
 * @Date: 2020-01-01 15:58
 */
@Service
public class PaperService {

    @Autowired
    PaperRepository paperRepository;

    /**
     * 分页查询问卷的数据
     *
     * @param from
     * @param limit
     * @return
     */
    public Page  getQuestionnaireByPage(int from, Integer limit) {
        if (from <= 0) {
            from = 0;
        } else {
            from --;
        }
        Pageable pageable = PageRequest.of(from, limit);
        Page<Paper> page = paperRepository.findAll(pageable);

        System.out.println(page.toString());
        System.out.println("************************************************");
        for (Paper o : page.getContent()) {
            System.out.println(o);
            System.out.println("---------------------------------------------------------");
        }
        return page;
    }

    /**
     * 根据id查询问卷
     *
     * @param id
     * @return
     */
    public QuestionnaireDto getQuestionnaireById(Integer id) {

        Optional<Paper> optional = paperRepository.findById(id);
        if (!optional.isPresent()){
            throw  new CustomException("问卷不存在",50008);
        }

        Paper paper = optional.get();

        return new QuestionnaireDto(paper);

    }
}
