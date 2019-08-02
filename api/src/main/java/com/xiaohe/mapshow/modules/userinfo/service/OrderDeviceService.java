package com.xiaohe.mapshow.modules.userinfo.service;

import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.userinfo.entity.OrderDevice;
import com.xiaohe.mapshow.modules.userinfo.entity.QueryOrderDevice;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>
 * 设备表 HxclOrderDevice接口
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

public interface OrderDeviceService extends IBaseServiceTwo<OrderDevice,Integer> {

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<OrderDevice> findAll(int page, int pageSize, QueryOrderDevice queryHxclOrderDevice);

    /**
     * 根据Id查询list
     * @return
     */
    List<OrderDevice> findAllById(List<Integer> ids);

    /**
     * 通过设备号集合查询
     * @param deviceIds 设备号集合
     * @return 查询结果
     */
    List<OrderDevice> findAllByDeviceNoIn(List<String> deviceIds);
}


