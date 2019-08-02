package com.xiaohe.mapshow.modules.cities.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaohe.mapshow.modules.cities.entity.Cities;

import java.util.List;

/**
 *
 * 行政区域地州市信息表 CitiesDao层
 *
 * @author hzh
 * @since 2019-04-08
 */
public interface CitiesDao extends BaseMapper<Cities> {
    /**
     * 查询省份下面的城市
     *
     * @param provinceId 省份id
     * @return 城市列表
     */
    List<Cities> selectByProvinceId(String provinceId);
}
