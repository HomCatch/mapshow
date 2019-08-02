package com.xiaohe.mapshow.modules.untils.query;

import java.io.Serializable;
import java.util.Map;

/**
 * 查询对象
 *
 * @author hzh
 * @version 1.0
 * @date 2018/12/28 10:50
 */
public class QueryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 查询第几页
     */
    private Integer page;
    /**
     * 每页显示个数
     */
    private Integer pageSize;
    /**
     * 排序方式
     */
    private SortVo sortVo;
    /**
     * 查询条件
     */
    private Map<String, Object> params;
    /**
     * 查询操作
     */
    private Map<String, Object> operators;


    public Integer getPage() {
        if (this.page == null) {
            page = 1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        if (this.pageSize == null) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public SortVo getSortVo() {
        return sortVo;
    }

    public void setSortVo(SortVo sortVo) {
        this.sortVo = sortVo;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getOperators() {
        return operators;
    }

    public void setOperators(Map<String, Object> operators) {
        this.operators = operators;
    }


}
