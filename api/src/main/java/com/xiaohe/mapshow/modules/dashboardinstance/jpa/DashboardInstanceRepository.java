package com.xiaohe.mapshow.modules.dashboardinstance.jpa;

import com.xiaohe.mapshow.modules.dashboardinstance.entity.DashboardInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 仪表盘实例 jpa 接口
 *
 * @author gmq
 * @since 2019-04-01
 */

@Transactional(rollbackFor = Exception.class)
public interface DashboardInstanceRepository extends JpaRepository<DashboardInstance, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<DashboardInstance> findAll(Specification<DashboardInstance> spec, Pageable pageable);

}