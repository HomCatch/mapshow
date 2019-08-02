package com.xiaohe.mapshow.modules.provinces.service;

import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.provinces.entity.Provinces;
import com.xiaohe.mapshow.modules.provinces.entity.QueryProvinces;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>
 * 省份信息表 Provinces接口
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

public interface ProvincesService extends IBaseServiceTwo<Provinces, Integer> {

    /**
     * 按条件查询
     *
     * @param page     页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<Provinces> findAll(int page, int pageSize, QueryProvinces queryProvinces);

    /**
     * 根据Id查询list
     *
     * @return
     */
    List<Provinces> findAllById(List<Integer> ids);

    /**
     * 查询所有省份id
     *
     * @return 查询集合
     */
    List<Provinces> findList();


}


