package com.xiaohe.mapshow.modules.userinfo.jpa;

import com.xiaohe.mapshow.modules.userinfo.entity.OrderDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备表 jpa 接口
 *
 * @author gmq
 * @since 2019-04-01
 */

@Transactional(rollbackFor = Exception.class)
public interface OrderDeviceRepository extends JpaRepository<OrderDevice, Integer> {

    /**
     * 按条件查询方案
     *
     * @param spec     spec
     * @param pageable 分页
     * @return page
     */
    Page<OrderDevice> findAll(Specification<OrderDevice> spec, Pageable pageable);


    /**
     * 通过设备号集合查询
     *
     * @param deviceIds 设备号集合
     * @return 查询结果
     */
    List<OrderDevice> findAllByDeviceNoIn(List<String> deviceIds);
}