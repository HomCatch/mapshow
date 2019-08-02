package com.xiaohe.mapshow.modules.dashboardproperty.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.dashboardproperty.service.DashboardPropertyService;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.QueryDashboardProperty;
import com.xiaohe.mapshow.modules.dashboardproperty.jpa.DashboardPropertyRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 仪表盘属性 DashboardProperty服务类
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

@Service
public class DashboardPropertyServiceImpl implements DashboardPropertyService {

    @Autowired
    private DashboardPropertyRepository dashboardPropertyRepository;

    /**
     * 保存对象
     *
     * @param dashboardProperty 对象
     *                          持久对象，或者对象集合
     */
    @Override
    public DashboardProperty save(DashboardProperty dashboardProperty) {
        return dashboardPropertyRepository.save(dashboardProperty);
    }

    /**
     * 删除对象
     *
     * @param dashboardProperty 对象
     */
    @Override
    public void delete(DashboardProperty dashboardProperty) {
        dashboardPropertyRepository.delete(dashboardProperty);
    }

    @Override
    public void deleteAll(List<DashboardProperty> list) {
        dashboardPropertyRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        dashboardPropertyRepository.deleteInBatch(dashboardPropertyRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return dashboardPropertyRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return dashboardPropertyRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return DashboardProperty对象
     */
    @Override
    public DashboardProperty findById(Integer id) {
        Optional<DashboardProperty> optional = dashboardPropertyRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<DashboardProperty>对象
     */
    @Override
    public Page<DashboardProperty> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return dashboardPropertyRepository.findAll(pageable);
    }

    @Override
    public Page<DashboardProperty> findAll(int page, int pageSize, QueryDashboardProperty queryDashboardProperty) {
        Pageable pageable =  PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<DashboardProperty> spec = (Specification<DashboardProperty>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (queryDashboardProperty.getId() != null) {
                predicate.getExpressions().add(cb.equal(root.get("id").as(Integer.class), queryDashboardProperty.getId()));
            }

            if (StringUtils.isNotBlank(queryDashboardProperty.getType())) {
                predicate.getExpressions().add(cb.like(root.get("type").as(String.class), "%" + queryDashboardProperty.getType() + "%"));
            }
            if (StringUtils.isNotBlank(queryDashboardProperty.getStyleData())) {
                predicate.getExpressions().add(cb.like(root.get("styleData").as(String.class), "%" + queryDashboardProperty.getStyleData() + "%"));
            }
            return predicate;
        };
        return dashboardPropertyRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<DashboardProperty> findAllById(List<Integer> ids) {
        return dashboardPropertyRepository.findAllById(ids);
    }

    @Override
    public List<DashboardProperty> findAll() {
        List<DashboardProperty> all = dashboardPropertyRepository.findAll();
        for (DashboardProperty dashboardProperty : all) {
            if(StringUtils.isNotBlank(dashboardProperty.getStyleData())){
                dashboardProperty.setStyleJson(JSON.parse(dashboardProperty.getStyleData()));
            }
        }
        return all;
    }
}


