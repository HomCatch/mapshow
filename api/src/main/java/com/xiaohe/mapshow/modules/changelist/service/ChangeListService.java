package com.xiaohe.mapshow.modules.changelist.service;

import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.changelist.entity.ChangeList;
import com.xiaohe.mapshow.modules.changelist.entity.QueryChangeList;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>
 *  ChangeList接口
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

public interface ChangeListService  extends IBaseServiceTwo<ChangeList,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<ChangeList> findAll(int page, int pageSize, QueryChangeList queryChangeList);

    /**
     * 根据Id查询list
     * @return
     */
    List<ChangeList> findAllById(List<Integer> ids);

    List<ChangeList> getAll();
}


