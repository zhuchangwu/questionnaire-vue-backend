package com.changwu.questionnaire.dto;

import com.changwu.questionnaire.bean.Paper;
import com.changwu.questionnaire.typeEnum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Changwu
 * @Date: 2019-12-27 22:48
 */
@Data
@ToString
public class UserDto {
    private Integer id;
    private String username;
    private String name;
    private String phone;
    private String email;
    private String rolesDto;
}