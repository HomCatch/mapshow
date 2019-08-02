package com.xiaohe.mapshow.modules.dashboardinstance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.dashboardinstance.entity.DashboardInstance;
import com.xiaohe.mapshow.modules.dashboardinstance.entity.QueryDashboardInstance;
import com.xiaohe.mapshow.modules.dashboardinstance.jpa.DashboardInstanceRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
/**
 * <p>
 * 仪表盘实例 DashboardInstance接口
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

public interface DashboardInstanceService  extends IBaseServiceTwo<DashboardInstance,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<DashboardInstance> findAll(int page, int pageSize, QueryDashboardInstance queryDashboardInstance);

    /**
     * 根据Id查询list
     * @return
     */
    List<DashboardInstance> findAllById(List<Integer> ids);

    List<DashboardInstance> saveAll(List<DashboardInstance> dashboardInstanceList);


}


