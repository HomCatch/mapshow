package com.xiaohe.mapshow.modules.waterstatic.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.waterstatic.service.WaterStaticService;
import com.xiaohe.mapshow.modules.waterstatic.entity.WaterStatic;
import com.xiaohe.mapshow.modules.waterstatic.entity.QueryWaterStatic;
import com.xiaohe.mapshow.modules.waterstatic.jpa.WaterStaticRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * WaterStatic服务类
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */

@Service
@DS("#session.tenantName")
public class WaterStaticServiceImpl implements WaterStaticService {

    @Autowired
    private WaterStaticRepository waterStaticRepository;

    /**
     * 保存对象
     *
     * @param waterStatic 对象
     *                    持久对象，或者对象集合
     */
    @Override
    public WaterStatic save(WaterStatic waterStatic) {
        return waterStaticRepository.save(waterStatic);
    }

    /**
     * 删除对象
     *
     * @param waterStatic 对象
     */
    @Override
    public void delete(WaterStatic waterStatic) {
        waterStaticRepository.delete(waterStatic);
    }

    @Override
    public void deleteAll(List<WaterStatic> list) {
        waterStaticRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        waterStaticRepository.deleteInBatch(waterStaticRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return waterStaticRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return waterStaticRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return WaterStatic对象
     */
    @Override
    public WaterStatic findById(Integer id) {
        Optional<WaterStatic> optional = waterStaticRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<WaterStatic>对象
     */
    @Override
    public Page<WaterStatic> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "date");
        return waterStaticRepository.findAll(pageable);
    }

    @Override
    @DS("#dbName")
    public Page<WaterStatic> findAll(int page, int pageSize,String dbName) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "date");
        return waterStaticRepository.findAll(pageable);
    }

    @Override
    public Page<WaterStatic> findAll(int page, int pageSize, QueryWaterStatic queryWaterStatic) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "date");
        //查询条件构造
        Specification<WaterStatic> spec = new Specification<WaterStatic>() {
            @Override
            public Predicate toPredicate(Root<WaterStatic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (queryWaterStatic.getUserId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("userId").as(Integer.class), queryWaterStatic.getUserId()));
                }

                if (queryWaterStatic.getWaterAmount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("waterAmount").as(Integer.class), queryWaterStatic.getWaterAmount()));
                }

                if (queryWaterStatic.getUseCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("useCount").as(Integer.class), queryWaterStatic.getUseCount()));
                }

                if (queryWaterStatic.getUseTime() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("useTime").as(Integer.class), queryWaterStatic.getUseTime()));
                }

                if (queryWaterStatic.getInTds() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("inTds").as(Integer.class), queryWaterStatic.getInTds()));
                }

                if (queryWaterStatic.getOutTds() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("outTds").as(Integer.class), queryWaterStatic.getOutTds()));
                }

                if (queryWaterStatic.getInColor() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("inColor").as(Integer.class), queryWaterStatic.getInColor()));
                }

                if (queryWaterStatic.getOutColor() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("outColor").as(Integer.class), queryWaterStatic.getOutColor()));
                }

                if (queryWaterStatic.getIntTbdt() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("intTbdt").as(Integer.class), queryWaterStatic.getIntTbdt()));
                }

                if (queryWaterStatic.getOutTbdt() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("outTbdt").as(Integer.class), queryWaterStatic.getOutTbdt()));
                }

                if (queryWaterStatic.getIntTemp() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("intTemp").as(Integer.class), queryWaterStatic.getIntTemp()));
                }

                if (queryWaterStatic.getOutTemp() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("outTemp").as(Integer.class), queryWaterStatic.getOutTemp()));
                }

                return predicate;
            }

        };
        return waterStaticRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<WaterStatic> findAllById(List<Integer> ids) {
        return waterStaticRepository.findAllById(ids);
    }

    @Override
    @DS("#dbName")
    public void save(WaterStatic waterStatic,String dbName){
        waterStaticRepository.save(waterStatic);
    }
}


