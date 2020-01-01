package com.changwu.questionnaire.repository;

import com.changwu.questionnaire.bean.Paper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Changwu
 * @Date: 2020-01-01 9:13
 */
public interface PaperRepository extends JpaRepository<Paper,Integer> {
    Page<Paper> findAll(Pageable pageable);
}
