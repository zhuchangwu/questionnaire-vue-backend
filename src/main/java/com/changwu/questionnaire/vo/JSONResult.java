package com.changwu.questionnaire.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * @Description:
 * 自定义响应数据结构
 * 200：表示成功
 */
public class JSONResult implements Serializable {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应的token信息
    private String token;

    // 响应中的数据
    private Object data;

    public JSONResult(Integer code, Object page) {
        this.code=code;
        this.data=page;
    }


    // 返回给前端token
    public static JSONResult responseToken(Integer code, String token) {
        return new JSONResult(code, token);
    }


    // 返回给前端正常的响应信息
    public static JSONResult build(Integer code, String msg, Object data) {
        return new JSONResult(code, msg, data);
    }

    public static JSONResult responsePage(Integer code, Object page) {
        return new JSONResult(code, page);
    }



    public static JSONResult ok(String msg) {
        return new JSONResult(msg);
    }

    public static JSONResult errorMsg(Integer code , String msg) {
        return new JSONResult(code, msg, null);
    }


    public JSONResult() {

    }

    public JSONResult(Integer code, String token) {
        this.code = code;
        this.token = token;
    }


    public JSONResult(Integer code, String msg, Object data) {
        this.code = code;
        this.token = msg;
        this.data = data;
    }

    public JSONResult(String msg) {
        this.code = 200;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
