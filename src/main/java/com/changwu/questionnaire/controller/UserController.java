package com.changwu.questionnaire.controller;

import com.changwu.questionnaire.bean.Paper;
import com.changwu.questionnaire.bean.User;
import com.changwu.questionnaire.dto.UserDto;
import com.changwu.questionnaire.exception.CustomException;
import com.changwu.questionnaire.security.JwtTokenProvider;
import com.changwu.questionnaire.service.UserService;
import com.changwu.questionnaire.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Author: Changwu
 * @Date: 2019-12-27 22:09
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    /**
     * 登录只返回token
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public JSONResult login(@RequestParam("username") String username, @RequestParam("password") String password) {
        String token = userService.login(username, password);
        return JSONResult.responseToken(200, token);
    }

    /**
     * 获取用户的信息
     *
     * @return
     */
    /*  @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN')")*/
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public JSONResult info(HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolve(httpServletRequest);
        String username = jwtTokenProvider.getUsername(token);
        return JSONResult.build(200, "ok", userService.findUserByUsername(username));
    }


    @RequestMapping(path = "/getUserByPage")
    public JSONResult getUserByPage(@RequestParam Integer from, @RequestParam Integer limit, @RequestParam(required = false) String name) {
        Page<User> page = userService.getUserByPage(from, limit, name);
        return JSONResult.responsePage(200, page);
    }


    // 禁用用户
    @RequestMapping(path = "/forbidUser")
    public JSONResult forbidUser(@RequestParam Integer id) {
        try {
            userService.forbidUser(id);
            return JSONResult.ok("已禁用用户");
        } catch (CustomException e) {
            return JSONResult.errorMsg(e.getStatus(), e.getMessage());
        }
    }

    // 激活用户
    @RequestMapping(path = "/activeUser")
    public JSONResult activeUser(@RequestParam Integer id) {
        try {
            userService.activeUser(id);
            return JSONResult.ok("已激活用户");
        } catch (CustomException e) {
            return JSONResult.errorMsg(e.getStatus(), e.getMessage());
        }
    }

    // 激活请求
    @GetMapping("/edit")
    public JSONResult edit(@RequestParam Integer id) {
        try {
           User user =  userService.edit(id);
            return JSONResult.responsePage(200,user);
        } catch (CustomException e) {
            return JSONResult.errorMsg(e.getStatus(), e.getMessage());
        }
    }

    // 执行修改操作
    @PostMapping("/doEdit")
    public JSONResult doEdit(@RequestBody UserDto user) {
        try {
             userService.doEdit(user);
            return JSONResult.ok("修改成功");
        } catch (CustomException e) {
            return JSONResult.errorMsg(e.getStatus(), e.getMessage());
        }
    }

}
