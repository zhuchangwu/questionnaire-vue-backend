package com.changwu.questionnaire.controller;

import com.changwu.questionnaire.dto.QuestionnaireDto;
import com.changwu.questionnaire.security.JwtTokenProvider;
import com.changwu.questionnaire.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Changwu
 * @Date: 2019-12-31 20:01
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    JwtTokenProvider provider;
    /**
     * 上传问卷草稿
     *
     * @return
     */
    @PostMapping("/add")
    public JSONResult addQuestionnaire(@RequestBody QuestionnaireDto dto, HttpServletRequest request) {
        String token = provider.resolve(request);
        String userId = provider.getUserId(token);

        // todo 将前端传递寄进来的数据保存起来
        System.out.println(userId);
        System.out.println(dto.toString());
        return null;
    }

}
