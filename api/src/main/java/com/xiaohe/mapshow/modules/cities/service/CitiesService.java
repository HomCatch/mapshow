package com.xiaohe.mapshow.modules.cities.service;

import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.cities.entity.Cities;
import com.xiaohe.mapshow.modules.cities.entity.QueryCities;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>
 * 行政区域地州市信息表 Cities接口
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

public interface CitiesService extends IBaseServiceTwo<Cities, Integer> {

    /**
     * 按条件查询
     *
     * @param page        页数
     * @param pageSize    数量
     * @param queryCities 查询条件
     * @return Page
     */
    Page<Cities> findAll(int page, int pageSize, QueryCities queryCities);

    /**
     * 根据Id查询list
     *
     * @return 查询集合
     */
    List<Cities> findAllById(List<Integer> ids);

    /**
     * 通过省份id查询
     *
     * @param provinceId 省份id
     * @return 查询集合
     */
    List<Cities> findByProvinceId(String provinceId);
}


