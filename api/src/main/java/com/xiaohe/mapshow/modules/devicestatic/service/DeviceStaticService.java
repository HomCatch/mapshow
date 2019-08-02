package com.xiaohe.mapshow.modules.devicestatic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.devicestatic.entity.DeviceStatic;
import com.xiaohe.mapshow.modules.devicestatic.entity.QueryDeviceStatic;
import com.xiaohe.mapshow.modules.devicestatic.jpa.DeviceStaticRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
/**
 * <p>
 *  DeviceStatic接口
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */

public interface DeviceStaticService  extends IBaseServiceTwo<DeviceStatic,Integer>{

    /**
     * 按条件查询
     * @param page 页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<DeviceStatic> findAll(int page, int pageSize, QueryDeviceStatic queryDeviceStatic);

    /**
     * 根据Id查询list
     * @return
     */
    List<DeviceStatic> findAllById(List<Integer> ids);

    Page<DeviceStatic> findAll(int page,int pageSize,String dbName);

    void save(String dbName,DeviceStatic deviceStatic);
}


