package com.xiaohe.mapshow.modules.provinces.jpa;

import com.xiaohe.mapshow.modules.provinces.entity.Provinces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 省份信息表 jpa 接口
 *
 * @author hzh
 * @since 2019-04-08
 */

@Transactional(rollbackFor = Exception.class)
public interface ProvincesRepository extends JpaRepository<Provinces, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<Provinces> findAll(Specification<Provinces> spec, Pageable pageable);

}