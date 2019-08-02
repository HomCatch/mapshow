package com.xiaohe.mapshow.modules.filterplan.jpa;

import com.xiaohe.mapshow.modules.filterplan.entity.FilterPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 *  jpa 接口
 *
 * @author gmq
 * @since 2019-03-29
 */

@Transactional(rollbackFor = Exception.class)
public interface FilterPlanRepository extends JpaRepository<FilterPlan, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<FilterPlan> findAll(Specification<FilterPlan> spec, Pageable pageable);

    boolean existsByDeviceId(String deviceId);

    /**
     * 按ID集合查询list
     * @param idList id集合
     * @return List<FilterPlan>
     */
    List<FilterPlan> findAllByIdIn(List<Integer> idList);


}