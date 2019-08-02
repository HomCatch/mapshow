package com.xiaohe.mapshow.modules.userinfo.jpa;

import com.xiaohe.mapshow.modules.userinfo.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户信息表 jpa 接口
 *
 * @author gmq
 * @since 2019-04-01
 */

@Transactional(rollbackFor = Exception.class)
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    /**
     * 按条件查询方案
     *
     * @param spec     spec
     * @param pageable 分页
     * @return page
     */
    Page<UserInfo> findAll(Specification<UserInfo> spec, Pageable pageable);

    /**
     * 根据用户名查询用户ID集合
     * @param nickName 用户昵称
     * @return List
     */
    List<UserInfo> findAllByNickNameLike(String nickName);

    @Modifying
    @Query(value = "update hxcl_user_info SET last_login_time=now() ORDER BY rand() LIMIT ?1",nativeQuery = true)
    void updateUserActive(int count);

    @Query(value = "SELECT count(id) FROM hxcl_user_info where last_login_time>=DATE_SUB(NOW(), INTERVAL 7 DAY) ",nativeQuery = true)
    int getWeekActive();

    @Query(value = "SELECT count(id) FROM hxcl_user_info where last_login_time>=DATE_SUB(NOW(), INTERVAL 1 MONTH)",nativeQuery = true)
    int getMonthActive();

    @Query(value = "select id from hxcl_user_info",nativeQuery = true)
    List getIds();

    @Query(value = "select * from hxcl_user_info" ,nativeQuery =  true)
    List<UserInfo> getAll();
}