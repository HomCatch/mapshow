package com.xiaohe.experienceUser.modules.operatelog.service;

import org.springframework.data.domain.Page;
import com.xiaohe.experienceUser.base.IBaseService;
import com.xiaohe.experienceUser.modules.operatelog.entity.OperateLog;
import com.xiaohe.experienceUser.modules.operatelog.entity.QueryOperateLog;

import java.util.List;

/**
 * <p>
 *  OperateLog接口
 * </p>
 *
 * @author gmq
 * @since 2018-12-25
 */

public interface OperateLogService  extends IBaseService<OperateLog,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<OperateLog> findAll(int page, int pageSize, QueryOperateLog queryOperateLog);

    /**
     * 根据Id查询list
     * @return
     */
    List<OperateLog> findAllById(List<Integer> ids);

}


