package com.xiaohe.mapshow.modules.dashboardinstance.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.dashboardinstance.service.DashboardInstanceService;
import com.xiaohe.mapshow.modules.dashboardinstance.entity.DashboardInstance;
import com.xiaohe.mapshow.modules.dashboardinstance.entity.QueryDashboardInstance;
import com.xiaohe.mapshow.modules.dashboardinstance.jpa.DashboardInstanceRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 仪表盘实例 DashboardInstance服务类
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

@Service
public class DashboardInstanceServiceImpl implements DashboardInstanceService {

    @Autowired
    private DashboardInstanceRepository dashboardInstanceRepository;

    /**
     * 保存对象
     *
     * @param dashboardInstance 对象
     *                          持久对象，或者对象集合
     */
    @Override
    public DashboardInstance save(DashboardInstance dashboardInstance) {
        return dashboardInstanceRepository.save(dashboardInstance);
    }

    /**
     * 删除对象
     *
     * @param dashboardInstance 对象
     */
    @Override
    public void delete(DashboardInstance dashboardInstance) {
        dashboardInstanceRepository.delete(dashboardInstance);
    }

    @Override
    public void deleteAll(List<DashboardInstance> list) {
        dashboardInstanceRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        dashboardInstanceRepository.deleteInBatch(dashboardInstanceRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return dashboardInstanceRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return dashboardInstanceRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return DashboardInstance对象
     */
    @Override
    public DashboardInstance findById(Integer id) {
        Optional<DashboardInstance> optional = dashboardInstanceRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<DashboardInstance>对象
     */
    @Override
    public Page<DashboardInstance> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "id");
        return dashboardInstanceRepository.findAll(pageable);
    }

    @Override
    public Page<DashboardInstance> findAll(int page, int pageSize, QueryDashboardInstance queryDashboardInstance) {
        //仪表盘不分页
        pageSize = (int)count();
        Pageable pageable =  PageRequest.of(page, pageSize, Sort.Direction.ASC, "id");
        //查询条件构造
        Specification<DashboardInstance> spec = (Specification<DashboardInstance>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (StringUtils.isNotBlank(queryDashboardInstance.getPosition())) {
                predicate.getExpressions().add(cb.like(root.get("position").as(String.class), "%" + queryDashboardInstance.getPosition() + "%"));
            }
            if (queryDashboardInstance.getDashboardPropertyId() != null) {
                predicate.getExpressions().add(cb.equal(root.get("dashboardPropertyId").as(Integer.class), queryDashboardInstance.getDashboardPropertyId()));
            }

            if (StringUtils.isNotBlank(queryDashboardInstance.getName())) {
                predicate.getExpressions().add(cb.like(root.get("name").as(String.class), "%" + queryDashboardInstance.getName() + "%"));
            }
            if (queryDashboardInstance.getUserId()!=null) {
                predicate.getExpressions().add(cb.equal(root.get("userId").as(String.class),  queryDashboardInstance.getUserId() ));
            }
            if (StringUtils.isNotBlank(queryDashboardInstance.getBackgroundColor())) {
                predicate.getExpressions().add(cb.like(root.get("backgroundColor").as(String.class), "%" + queryDashboardInstance.getBackgroundColor() + "%"));
            }
            return predicate;
        };
        return dashboardInstanceRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<DashboardInstance> findAllById(List<Integer> ids) {
        return dashboardInstanceRepository.findAllById(ids);
    }

    @Override
    public List<DashboardInstance> saveAll(List<DashboardInstance> dashboardInstanceList) {
        return dashboardInstanceRepository.saveAll(dashboardInstanceList);
    }
}


