package com.changwu.questionnaire.controller;

import com.changwu.questionnaire.security.JwtTokenProvider;
import com.changwu.questionnaire.service.UserService;
import com.changwu.questionnaire.vo.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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
        String token = userService.login(username,password);
        return  JSONResult.responseToken(200,token);
    }

    /**
     * 获取用户的信息
     *
     * @return
     */
  /*  @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN')")*/
    @RequestMapping(path = "/info",method = RequestMethod.GET)
    public JSONResult info(HttpServletRequest httpServletRequest) {
        String token =  jwtTokenProvider.resolve(httpServletRequest);
        String username = jwtTokenProvider.getUsername(token);
        return  JSONResult.build(200,"ok",userService.findUserByUsername(username));
    }
}
