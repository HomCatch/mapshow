package com.xiaohe.mapshow.modules.cities.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.cities.entity.Cities;
import com.xiaohe.mapshow.modules.cities.entity.QueryCities;
import com.xiaohe.mapshow.modules.cities.jpa.CitiesRepository;
import com.xiaohe.mapshow.modules.cities.service.CitiesService;
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
 * 行政区域地州市信息表 Cities服务类
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

@Service
@DS("#session.tenantName")
public class CitiesServiceImpl implements CitiesService {

    @Autowired
    private CitiesRepository citiesRepository;

    /**
     * 保存对象
     *
     * @param cities 对象
     *               持久对象，或者对象集合
     */
    @Override
    public Cities save(Cities cities) {
        return citiesRepository.save(cities);
    }

    /**
     * 删除对象
     *
     * @param cities 对象
     */
    @Override
    public void delete(Cities cities) {
        citiesRepository.delete(cities);
    }

    @Override
    public void deleteAll(List<Cities> list) {
        citiesRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        citiesRepository.deleteInBatch(citiesRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return citiesRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return citiesRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return Cities对象
     */
    @Override
    public Cities findById(Integer id) {
        Optional<Cities> optional = citiesRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<Cities>对象
     */
    @Override
    public Page<Cities> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return citiesRepository.findAll(pageable);
    }

    @Override
    public Page<Cities> findAll(int page, int pageSize, QueryCities queryCities) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<Cities> spec = new Specification<Cities>() {
            @Override
            public Predicate toPredicate(Root<Cities> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(queryCities.getCityid())) {
                    predicate.getExpressions().add(cb.like(root.get("cityid").as(String.class), "%" + queryCities.getCityid() + "%"));
                }
                if (StringUtils.isNotBlank(queryCities.getCity())) {
                    predicate.getExpressions().add(cb.like(root.get("city").as(String.class), "%" + queryCities.getCity() + "%"));
                }
                if (StringUtils.isNotBlank(queryCities.getProvinceid())) {
                    predicate.getExpressions().add(cb.like(root.get("provinceid").as(String.class), "%" + queryCities.getProvinceid() + "%"));
                }
                return predicate;
            }

        };
        return citiesRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<Cities> findAllById(List<Integer> ids) {
        return citiesRepository.findAllById(ids);
    }

    @Override
    public List<Cities> findByProvinceId(String provinceId) {
        return citiesRepository.findByProvinceid(provinceId);
    }
}


