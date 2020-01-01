package com.changwu.questionnaire.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Changwu
 * @Date: 2020-01-01 15:42
 */
@ToString
public class PageBean implements Serializable {
    private long total; // 总条数
    private Integer pageSize; // 总页数
    private List currentPageData;// 当前页的内容

    public PageBean() {
    }

    public PageBean(Page page) {
       this.total=page.getTotalElements();
        this.pageSize =page.getTotalPages();
        this.currentPageData=page.getContent();
    }

    public long getTotal() {
        return total;
    }
    @JsonBackReference
    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }
    @JsonBackReference
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List getCurrentPageData() {
        return currentPageData;
    }
    @JsonBackReference
    public void setCurrentPageData(List currentPageData) {
        this.currentPageData = currentPageData;
    }
}
