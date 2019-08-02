package com.xiaohe.mapshow.modules.cloudwaterorder.jpa;

import com.xiaohe.mapshow.modules.cloudwaterorder.entity.CloudWaterOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 *  jpa 接口
 *
 * @author gmq
 * @since 2019-04-19
 */

@Transactional(rollbackFor = Exception.class)
public interface CloudWaterOrderRepository extends JpaRepository<CloudWaterOrder, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<CloudWaterOrder> findAll(Specification<CloudWaterOrder> spec, Pageable pageable);

    @Query(value = "select * from cloud_water_order",nativeQuery = true)
    List<CloudWaterOrder> getAll();
}