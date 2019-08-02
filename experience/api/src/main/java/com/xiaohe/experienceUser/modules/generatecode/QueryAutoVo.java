package com.xiaohe.experienceUser.modules.generatecode;

import java.util.List;

/**
 * @Author: hzh
 * @Date: 2018/12/28 16:55
 * @Version 1.0
 */
public class QueryAutoVo {
    private String table;
    /**
     * 排序方式
     */
    private String sort;
    /**
     * 操作的方式
     */
    private List<AutoVo> data;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<AutoVo> getData() {
        return data;
    }

    public void setData(List<AutoVo> data) {
        this.data = data;
    }
}
