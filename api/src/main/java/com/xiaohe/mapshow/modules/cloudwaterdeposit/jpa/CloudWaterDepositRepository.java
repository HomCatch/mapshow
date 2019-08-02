package com.xiaohe.mapshow.modules.cloudwaterdeposit.jpa;

import com.xiaohe.mapshow.modules.cloudwaterdeposit.entity.CloudWaterDeposit;
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
public interface CloudWaterDepositRepository extends JpaRepository<CloudWaterDeposit, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<CloudWaterDeposit> findAll(Specification<CloudWaterDeposit> spec, Pageable pageable);

    @Query(value = "select * from cloud_water_deposit",nativeQuery = true)
    List<CloudWaterDeposit> getAll();
}