package com.xiaohe.mapshow.modules.provinces.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.provinces.entity.Provinces;
import com.xiaohe.mapshow.modules.provinces.entity.QueryProvinces;
import com.xiaohe.mapshow.modules.provinces.jpa.ProvincesRepository;
import com.xiaohe.mapshow.modules.provinces.service.ProvincesService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 省份信息表 Provinces服务类
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

@Service
@DS("#session.tenantName")
public class ProvincesServiceImpl implements ProvincesService {

    @Autowired
    private ProvincesRepository provincesRepository;

    /**
     * 保存对象
     *
     * @param provinces 对象
     *                  持久对象，或者对象集合
     */
    @Override
    public Provinces save(Provinces provinces) {
        return provincesRepository.save(provinces);
    }

    /**
     * 删除对象
     *
     * @param provinces 对象
     */
    @Override
    public void delete(Provinces provinces) {
        provincesRepository.delete(provinces);
    }

    @Override
    public void deleteAll(List<Provinces> list) {
        provincesRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        provincesRepository.deleteInBatch(provincesRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return provincesRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return provincesRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return Provinces对象
     */
    @Override
    public Provinces findById(Integer id) {
        Optional<Provinces> optional = provincesRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<Provinces>对象
     */
    @Override
    public Page<Provinces> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return provincesRepository.findAll(pageable);
    }

    @Override
    public Page<Provinces> findAll(int page, int pageSize, QueryProvinces queryProvinces) {
        //过滤自己的广告
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<Provinces> spec = new Specification<Provinces>() {
            @Override
            public Predicate toPredicate(Root<Provinces> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(queryProvinces.getProvinceid())) {
                    predicate.getExpressions().add(cb.like(root.get("provinceid").as(String.class), "%" + queryProvinces.getProvinceid() + "%"));
                }
                if (StringUtils.isNotBlank(queryProvinces.getProvince())) {
                    predicate.getExpressions().add(cb.like(root.get("province").as(String.class), "%" + queryProvinces.getProvince() + "%"));
                }
                return predicate;
            }

        };
        return provincesRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<Provinces> findAllById(List<Integer> ids) {
        return provincesRepository.findAllById(ids);
    }

    @Override
    public List<Provinces> findList() {
        return provincesRepository.findAll();
    }
}


