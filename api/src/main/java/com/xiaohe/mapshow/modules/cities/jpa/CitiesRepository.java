package com.xiaohe.mapshow.modules.cities.jpa;

import com.xiaohe.mapshow.modules.cities.entity.Cities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 行政区域地州市信息表 jpa 接口
 *
 * @author hzh
 * @since 2019-04-08
 */

@Transactional(rollbackFor = Exception.class)
public interface CitiesRepository extends JpaRepository<Cities, Integer> {

    /**
     * 按条件查询方案
     *
     * @param spec     spec
     * @param pageable 分页
     * @return page
     */
    Page<Cities> findAll(Specification<Cities> spec, Pageable pageable);

    /**
     * 通过省份id查询
     *
     * @param provinceId 省份id
     * @return 查询集合
     */
    List<Cities> findByProvinceid(String provinceId);
}