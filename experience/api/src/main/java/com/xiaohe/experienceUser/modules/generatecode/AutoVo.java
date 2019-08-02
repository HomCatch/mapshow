package com.xiaohe.experienceUser.modules.generatecode;

/**
 * @Author: hzh
 * @Date: 2018/12/28 16:56
 * @Version 1.0
 */
public class AutoVo {
    /**
     * 字段
     */
    private String label;
    /**
     * 查询操作（等于，like...）
     */
    private String operators;
    /**
     * 是否排序
     */
    private String sortable;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getSortable() {
        return sortable;
    }

    public void setSortable(String sortable) {
        this.sortable = sortable;
    }
}
