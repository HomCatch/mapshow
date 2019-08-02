package com.xiaohe.mapshow.modules.userinfo.service;

import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.userinfo.entity.QueryUserInfo;
import com.xiaohe.mapshow.modules.userinfo.entity.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>
 * 用户信息表 HxclUserInfo接口
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

public interface UserInfoService extends IBaseServiceTwo<UserInfo, Integer> {

    /**
     * 按条件查询
     *
     * @param page     页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<UserInfo> findAll(int page, int pageSize, QueryUserInfo queryHxclUserInfo);

    /**
     * 根据Id查询list
     *
     * @return
     */
    List<UserInfo> findAllById(List<Integer> ids);

    void updateUserActive(String dbName,int count);

    int getWeekActive(String dbName);

    int getMonthActive(String dbName);

    List getIds(String dbName);

    void save(UserInfo userInfo,String dbName);

    long count(String dbName);

    List<UserInfo> getAll();
}


