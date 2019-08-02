package com.xiaohe.mapshow.modules.dashboardproperty.jpa;

import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 仪表盘属性 jpa 接口
 *
 * @author gmq
 * @since 2019-04-01
 */

@Transactional(rollbackFor = Exception.class)
public interface DashboardPropertyRepository extends JpaRepository<DashboardProperty, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<DashboardProperty> findAll(Specification<DashboardProperty> spec, Pageable pageable);

}