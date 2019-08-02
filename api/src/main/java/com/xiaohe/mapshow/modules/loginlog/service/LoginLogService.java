package com.xiaohe.mapshow.modules.loginlog.service;

import org.springframework.data.domain.Page;
import com.xiaohe.mapshow.base.IBaseService;
import com.xiaohe.mapshow.modules.loginlog.entity.LoginLog;
import com.xiaohe.mapshow.modules.loginlog.entity.QueryLoginLog;
import java.util.List;


/**
 * <p>
 *  LoginLog接口
 * </p>
 *
 * @author gmq
 * @since 2018-12-25
 */

public interface LoginLogService  extends IBaseService<LoginLog,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param queryLoginLog
     * @return Page
     */
    Page<LoginLog> findAll(int page, int pageSize, QueryLoginLog queryLoginLog);

    /**
     * 根据Id查询list
     * @param ids id集合
     * @return list
     */
    List<LoginLog> findAllById(List<Integer> ids);

    /**
     * 获取子部门的用户名
     * @return
     */
    List<String> findAllSubUserName();

    LoginLog save(String loginTn, LoginLog loginLog);

}


