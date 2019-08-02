package com.xiaohe.mapshow.modules.dashboardproperty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.QueryDashboardProperty;
import com.xiaohe.mapshow.modules.dashboardproperty.jpa.DashboardPropertyRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
/**
 * <p>
 * 仪表盘属性 DashboardProperty接口
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

public interface DashboardPropertyService  extends IBaseServiceTwo<DashboardProperty,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<DashboardProperty> findAll(int page, int pageSize, QueryDashboardProperty queryDashboardProperty);

    /**
     * 根据Id查询list
     * @return
     */
    List<DashboardProperty> findAllById(List<Integer> ids);

    /**
     * 查询所有
     * @return DashboardProperty
     */
    List<DashboardProperty> findAll();


}


