package com.xiaohe.mapshow.modules.userstatic.jpa;

import com.xiaohe.mapshow.modules.userstatic.entity.UserStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *  jpa 接口
 *
 * @author wzq
 * @since 2019-06-04
 */

@Transactional(rollbackFor = Exception.class)
public interface UserStaticRepository extends JpaRepository<UserStatic, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<UserStatic> findAll(Specification<UserStatic> spec, Pageable pageable);

}