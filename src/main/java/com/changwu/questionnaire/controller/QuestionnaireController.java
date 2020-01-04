package com.changwu.questionnaire.controller;

import com.changwu.questionnaire.bean.Paper;
import com.changwu.questionnaire.dto.PageBean;
import com.changwu.questionnaire.dto.QuestionnaireDto;
import com.changwu.questionnaire.exception.CustomException;
import com.changwu.questionnaire.security.JwtTokenProvider;
import com.changwu.questionnaire.service.PaperService;
import com.changwu.questionnaire.service.QuestionnaireService;
import com.changwu.questionnaire.vo.JSONResult;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Changwu
 * @Date: 2019-12-31 20:01
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    QuestionnaireService questionnaireService;
    @Autowired
    JwtTokenProvider provider;
    @Autowired
    PaperService paperService;

    /**
     * 上传问卷草稿
     *
     * @return
     */
    @PostMapping("/add")
    public JSONResult addQuestionnaire(@RequestBody QuestionnaireDto dto, HttpServletRequest request) {
        String token = provider.resolve(request);
        String userId = provider.getUserId(token);
        try {
            questionnaireService.addQuestionnaire(dto, userId);
            return JSONResult.ok("问卷保存成功");
        } catch (CustomException e) {
            System.out.println("controller  " + e.getMessage());
            return JSONResult.errorMsg(e.getStatus(), e.getMessage());
        }
    }

    /**
     * 分页查询问卷
     *
     * @param from
     * @param limit
     * @return
     */
    @GetMapping("/getQuestionnaireByPage")
    public JSONResult getQuestionnaireByPage(@RequestParam Integer from, @RequestParam Integer limit, @RequestParam(required = false) String name) {
        Page page = paperService.getQuestionnaireByPage(from, limit,name);
        List<Paper> content = page.getContent();
        content.forEach(item -> {
            item.setUserName(item.getUser().getName());
        });
        return JSONResult.responsePage(200, page);
    }

    @GetMapping("/getQuestionnaireById")
    public JSONResult getQuestionnaireById(@RequestParam Integer id) {
        QuestionnaireDto questionnaireDto = paperService.getQuestionnaireById(id);
        return JSONResult.responsePage(200, questionnaireDto);
    }

    // 将问卷上线
    @PutMapping("/pushOnline")
    public JSONResult pushOnline(@RequestParam Integer id){
        try{
            paperService.pushOnline(id);
            return JSONResult.ok("问卷已上线成功");
        }catch (CustomException e){
            return JSONResult.errorMsg(e.getStatus(),e.getMessage());
        }
    }

    // 将问卷下线
    @PutMapping("/backOnline")
    public JSONResult backOnline(@RequestParam Integer id){
        try{
            paperService.backOnline(id);
            return JSONResult.ok("问卷成功下线");
        }catch (CustomException e){
            return JSONResult.errorMsg(e.getStatus(),e.getMessage());
        }
    }

    @PutMapping("/delete")
    public JSONResult delete(@RequestParam Integer id){
        try{
            paperService.delete(id);
            return JSONResult.ok("问卷删除成功");
        }catch (CustomException e){
            return JSONResult.errorMsg(e.getStatus(),e.getMessage());
        }
    }

}
