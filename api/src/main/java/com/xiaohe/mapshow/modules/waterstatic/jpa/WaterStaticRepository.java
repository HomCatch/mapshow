package com.xiaohe.mapshow.modules.waterstatic.jpa;

import com.xiaohe.mapshow.modules.waterstatic.entity.WaterStatic;
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
public interface WaterStaticRepository extends JpaRepository<WaterStatic, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<WaterStatic> findAll(Specification<WaterStatic> spec, Pageable pageable);

}