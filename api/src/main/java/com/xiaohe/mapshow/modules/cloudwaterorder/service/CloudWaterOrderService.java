package com.xiaohe.mapshow.modules.cloudwaterorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.cloudwaterorder.entity.CloudWaterOrder;
import com.xiaohe.mapshow.modules.cloudwaterorder.entity.QueryCloudWaterOrder;
import com.xiaohe.mapshow.modules.cloudwaterorder.jpa.CloudWaterOrderRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
/**
 * <p>
 *  CloudWaterOrder接口
 * </p>
 *
 * @author gmq
 * @since 2019-04-19
 */

public interface CloudWaterOrderService  extends IBaseServiceTwo<CloudWaterOrder,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<CloudWaterOrder> findAll(int page, int pageSize, QueryCloudWaterOrder queryCloudWaterOrder);

    /**
     * 根据Id查询list
     * @return
     */
    List<CloudWaterOrder> findAllById(List<Integer> ids);

    List<CloudWaterOrder> getAll();
}


