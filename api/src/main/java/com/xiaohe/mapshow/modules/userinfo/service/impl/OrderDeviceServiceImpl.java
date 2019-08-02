package com.xiaohe.mapshow.modules.userinfo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.device.entity.Device;
import com.xiaohe.mapshow.modules.device.service.DeviceService;
import com.xiaohe.mapshow.modules.userinfo.entity.OrderDevice;
import com.xiaohe.mapshow.modules.userinfo.entity.QueryOrderDevice;
import com.xiaohe.mapshow.modules.userinfo.jpa.OrderDeviceRepository;
import com.xiaohe.mapshow.modules.userinfo.service.OrderDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 设备表 HxclOrderDevice服务类
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

@Service
@DS("#session.tenantName")
public class OrderDeviceServiceImpl implements OrderDeviceService {

    @Autowired
    private OrderDeviceRepository hxclOrderDeviceRepository;
    @Autowired
    private DeviceService deviceService;

    /**
     * 保存对象
     *
     * @param hxclOrderDevice 对象
     *                        持久对象，或者对象集合
     */
    @Override
    public OrderDevice save(OrderDevice hxclOrderDevice) {
        return hxclOrderDeviceRepository.save(hxclOrderDevice);
    }

    /**
     * 删除对象
     *
     * @param hxclOrderDevice 对象
     */
    @Override
    public void delete(OrderDevice hxclOrderDevice) {
        hxclOrderDeviceRepository.delete(hxclOrderDevice);
    }

    @Override
    public void deleteAll(List<OrderDevice> list) {
        hxclOrderDeviceRepository.deleteInBatch(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        hxclOrderDeviceRepository.deleteInBatch(hxclOrderDeviceRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return hxclOrderDeviceRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return hxclOrderDeviceRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return HxclOrderDevice对象
     */
    @Override
    public OrderDevice findById(Integer id) {
        Optional<OrderDevice> byId = hxclOrderDeviceRepository.findById(id);
        return byId.orElse(null);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<OrderDevice>对象
     */
    @Override
    public Page<OrderDevice> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return hxclOrderDeviceRepository.findAll(pageable);
    }

    @Override
    public Page<OrderDevice> findAll(int page, int pageSize, QueryOrderDevice queryObj) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //获取经销商下的设备
//        List<Device> deviceList = deviceService.findDeviceListByArea(null, null);
//        if (CollectionUtils.isEmpty(deviceList)) {
//            return null;
//        }
        //查询条件构造
        Specification<OrderDevice> spec = (Specification<OrderDevice>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            //一个使用设备得人，可能存在绑定两个经销商下面的设备，此处获取当前经销商下的设备数
            List<Expression<Boolean>> expressions = predicate.getExpressions();
//            CriteriaBuilder.In<Object> in = cb.in(root.get("deviceNo"));
//            for (Device device : deviceList) {
//                in.value(device.getDeviceId());
//            }
//            expressions.add(in);
            if (queryObj.getUserId() != null) {
                expressions.add(cb.equal(root.get("userId").as(Integer.class), queryObj.getUserId()));
            }

            return predicate;
        };
        return hxclOrderDeviceRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<OrderDevice> findAllById(List<Integer> ids) {
        return hxclOrderDeviceRepository.findAllById(ids);
    }

    @Override
    public List<OrderDevice> findAllByDeviceNoIn(List<String> deviceIds) {
        return hxclOrderDeviceRepository.findAllByDeviceNoIn(deviceIds);
    }
}


