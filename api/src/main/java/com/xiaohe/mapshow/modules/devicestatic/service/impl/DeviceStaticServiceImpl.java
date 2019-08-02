package com.xiaohe.mapshow.modules.devicestatic.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.devicestatic.service.DeviceStaticService;
import com.xiaohe.mapshow.modules.devicestatic.entity.DeviceStatic;
import com.xiaohe.mapshow.modules.devicestatic.entity.QueryDeviceStatic;
import com.xiaohe.mapshow.modules.devicestatic.jpa.DeviceStaticRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * DeviceStatic服务类
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */

@Service
@DS("#session.tenantName")
public class DeviceStaticServiceImpl implements DeviceStaticService {

    @Autowired
    private DeviceStaticRepository deviceStaticRepository;

    /**
     * 保存对象
     *
     * @param deviceStatic 对象
     *                     持久对象，或者对象集合
     */
    @Override
    public DeviceStatic save(DeviceStatic deviceStatic) {
        return deviceStaticRepository.save(deviceStatic);
    }

    /**
     * 删除对象
     *
     * @param deviceStatic 对象
     */
    @Override
    public void delete(DeviceStatic deviceStatic) {
        deviceStaticRepository.delete(deviceStatic);
    }

    @Override
    public void deleteAll(List<DeviceStatic> list) {
        deviceStaticRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        deviceStaticRepository.deleteInBatch(deviceStaticRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return deviceStaticRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return deviceStaticRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return DeviceStatic对象
     */
    @Override
    public DeviceStatic findById(Integer id) {
        Optional<DeviceStatic> optional = deviceStaticRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<DeviceStatic>对象
     */
    @Override
    public Page<DeviceStatic> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "date");
        return deviceStaticRepository.findAll(pageable);
    }
    @Override
    @DS("#dbName")
    public Page<DeviceStatic> findAll(int page, int pageSize,String dbName) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "date");
        return deviceStaticRepository.findAll(pageable);
    }
    @Override
    public Page<DeviceStatic> findAll(int page, int pageSize, QueryDeviceStatic queryDeviceStatic) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "date");
        //查询条件构造
        Specification<DeviceStatic> spec = new Specification<DeviceStatic>() {
            @Override
            public Predicate toPredicate(Root<DeviceStatic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (queryDeviceStatic.getUserId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("userId").as(Integer.class), queryDeviceStatic.getUserId()));
                }

                if (queryDeviceStatic.getActiveDeviceCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("activeDeviceCount").as(Integer.class), queryDeviceStatic.getActiveDeviceCount()));
                }

                if (queryDeviceStatic.getOnlineCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("onlineCount").as(Integer.class), queryDeviceStatic.getOnlineCount()));
                }

                if (queryDeviceStatic.getOfflineCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("offlineCount").as(Integer.class), queryDeviceStatic.getOfflineCount()));
                }

                if (queryDeviceStatic.getActiveFilterCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("activeFilterCount").as(Integer.class), queryDeviceStatic.getActiveFilterCount()));
                }

                if (queryDeviceStatic.getMaturityFilterCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("maturityFilterCount").as(Integer.class), queryDeviceStatic.getMaturityFilterCount()));
                }

                if (queryDeviceStatic.getFaultDeviceCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("faultDeviceCount").as(Integer.class), queryDeviceStatic.getFaultDeviceCount()));
                }

                if (queryDeviceStatic.getAddDeviceCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("addDeviceCount").as(Integer.class), queryDeviceStatic.getAddDeviceCount()));
                }

                return predicate;
            }

        };
        return deviceStaticRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<DeviceStatic> findAllById(List<Integer> ids) {
        return deviceStaticRepository.findAllById(ids);
    }

    @Override
    @DS("#dbName")
    public void save(String dbName,DeviceStatic deviceStatic){
        deviceStaticRepository.save(deviceStatic);
    }

}


