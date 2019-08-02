package com.xiaohe.mapshow.modules.waterstatic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.waterstatic.entity.WaterStatic;
import com.xiaohe.mapshow.modules.waterstatic.entity.QueryWaterStatic;
import com.xiaohe.mapshow.modules.waterstatic.jpa.WaterStaticRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
/**
 * <p>
 *  WaterStatic接口
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */

public interface WaterStaticService  extends IBaseServiceTwo<WaterStatic,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<WaterStatic> findAll(int page, int pageSize, QueryWaterStatic queryWaterStatic);

    /**
     * 根据Id查询list
     * @return
     */
    List<WaterStatic> findAllById(List<Integer> ids);

    Page<WaterStatic> findAll(int page, int pageSize, String dbName);

    void save(WaterStatic waterStatic,String dbName);
}


