package com.changwu.questionnaire.service;

import com.changwu.questionnaire.bean.Paper;
import com.changwu.questionnaire.dto.QuestionnaireDto;
import com.changwu.questionnaire.exception.CustomException;
import com.changwu.questionnaire.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
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
    public Page getQuestionnaireByPage(int from, Integer limit, String name) {
        if (from <= 0) {
            from = 0;
        } else {
            from--;
        }
        if (limit < 10) {
            limit = 10;
        }

        Pageable pageable = PageRequest.of(from, limit);
        Page<Paper> page = paperRepository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicateList = new ArrayList<>();
                if (name != null) {
                    Path labelname = root.get("title");
                    Predicate like = criteriaBuilder.like(labelname.as(String.class), "%" + name + "%");
                    predicateList.add(like);
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                predicateList.toArray(predicates);
                // 参数位置支持可变参数, 但是我们要尽量传递进去有效的条件
                return criteriaBuilder.and(predicates);
            }
        }, pageable);

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
        if (!optional.isPresent()) {
            throw new CustomException("问卷不存在", 50008);
        }

        Paper paper = optional.get();

        return new QuestionnaireDto(paper);

    }

    // 将问卷的状态修改成上线的状态
    public void pushOnline(Integer id) {
        Optional<Paper> optional = paperRepository.findById(id);
        if (!optional.isPresent()) {
            throw new CustomException("问卷不存在", 50008);
        }
        Paper paper = optional.get();

        // 检查是否已经过期了
        Date date = new Date();
        if (paper.getEndTime().before(date)) {
            throw new CustomException("对不起, 问卷已结束", 50008);
        }
        paper.setStatus(1);
        Paper save = paperRepository.save(paper);
        if (save == null) {
            throw new CustomException("问卷上线失败, 请联系管理员", 50008);
        }
    }

    // 下线问卷
    public void backOnline(Integer id) {
        Optional<Paper> optional = paperRepository.findById(id);
        if (!optional.isPresent()) {
            throw new CustomException("问卷不存在", 50008);
        }
        Paper paper = optional.get();
        paper.setStatus(0);
        Paper save = paperRepository.save(paper);
        if (save == null) {
            throw new CustomException("问卷下线失败, 请联系管理员", 50008);
        }
    }

    /**
     * 删除问卷, 如果问卷已经在线上, 不能直接删除
     *
     * @param id
     */
    public void delete(Integer id) {
        Optional<Paper> optional = paperRepository.findById(id);

        if (!optional.isPresent()) {
            throw new CustomException("问卷不存在", 50008);
        }
        Paper paper = optional.get();

        if (paper.getStatus() == 1) {
            throw new CustomException("问卷处于发布状态, 无法直接删除", 50008);
        }
        paperRepository.deleteById(id);
    }
}
