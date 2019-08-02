package com.xiaohe.mapshow.modules.userstatic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.userstatic.entity.UserStatic;
import com.xiaohe.mapshow.modules.userstatic.entity.QueryUserStatic;
import com.xiaohe.mapshow.modules.userstatic.jpa.UserStaticRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
/**
 * <p>
 *  UserStatic接口
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */

public interface UserStaticService  extends IBaseServiceTwo<UserStatic,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<UserStatic> findAll(int page, int pageSize, QueryUserStatic queryUserStatic);

    Page<UserStatic> findAll(int page, int pageSize,String dbName);

    /**
     * 根据Id查询list
     * @return
     */
    List<UserStatic> findAllById(List<Integer> ids);

    void save(UserStatic userStatic,String dbName);
}


