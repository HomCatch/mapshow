package com.xiaohe.mapshow.modules.cloudwaterdeposit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.entity.CloudWaterDeposit;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.entity.QueryCloudWaterDeposit;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.jpa.CloudWaterDepositRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
/**
 * <p>
 *  CloudWaterDeposit接口
 * </p>
 *
 * @author gmq
 * @since 2019-04-19
 */

public interface CloudWaterDepositService  extends IBaseServiceTwo<CloudWaterDeposit,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<CloudWaterDeposit> findAll(int page, int pageSize, QueryCloudWaterDeposit queryCloudWaterDeposit);

    /**
     * 根据Id查询list
     * @return
     */
    List<CloudWaterDeposit> findAllById(List<Integer> ids);

    List<CloudWaterDeposit> getAll();
}


