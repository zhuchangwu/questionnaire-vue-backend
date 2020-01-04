package com.changwu.questionnaire.repository.projection;

import java.util.List;

/**
 * @Author: Changwu
 * @Date: 2020-01-02 22:23
 */
public interface UserProjection {
    Integer getId();
    String getName();
    String getUsername();
    String getPhone();
    String getEmail();
    List getRole();
    String getStatus();
}
