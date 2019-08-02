package com.xiaohe.experienceUser.modules.errorlog.service;

import org.springframework.data.domain.Page;
import com.xiaohe.experienceUser.base.IBaseService;
import com.xiaohe.experienceUser.modules.errorlog.entity.ErrorLog;
import com.xiaohe.experienceUser.modules.errorlog.entity.QueryErrorLog;
import java.util.List;

/**
 * <p>
 *  ErrorLog接口
 * </p>
 *
 * @author gmq
 * @since 2018-12-25
 */

public interface ErrorLogService  extends IBaseService<ErrorLog,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<ErrorLog> findAll(int page, int pageSize, QueryErrorLog queryErrorLog);

    /**
     * 根据Id查询list
     * @param ids id集合
     * @return list
     */
    List<ErrorLog> findAllById(List<Integer> ids);

}


