package com.xiaohe.mapshow.modules.changelist.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.changelist.entity.ChangeList;
import com.xiaohe.mapshow.modules.changelist.entity.QueryChangeList;
import com.xiaohe.mapshow.modules.changelist.jpa.ChangeListRepository;
import com.xiaohe.mapshow.modules.changelist.service.ChangeListService;
import com.xiaohe.mapshow.modules.device.entity.Device;
import com.xiaohe.mapshow.modules.device.service.DeviceService;
import org.apache.commons.lang.StringUtils;
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
 * ChangeList服务类
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

@Service
@DS("#session.tenantName")
public class ChangeListServiceImpl implements ChangeListService {

    @Autowired
    private ChangeListRepository changeListRepository;
    @Autowired
    private DeviceService deviceService;

    /**
     * 保存对象
     *
     * @param changeList 对象
     *                   持久对象，或者对象集合
     */
    @Override
    public ChangeList save(ChangeList changeList) {
        return changeListRepository.save(changeList);
    }

    /**
     * 删除对象
     *
     * @param changeList 对象
     */
    @Override
    public void delete(ChangeList changeList) {
        changeListRepository.delete(changeList);
    }

    @Override
    public void deleteAll(List<ChangeList> list) {
        changeListRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        changeListRepository.deleteInBatch(changeListRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return changeListRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return changeListRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return ChangeList对象
     */
    @Override
    public ChangeList findById(Integer id) {
        Optional<ChangeList> optional = changeListRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<ChangeList>对象
     */
    @Override
    public Page<ChangeList> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return changeListRepository.findAll(pageable);
    }

    @Override
    public Page<ChangeList> findAll(int page, int pageSize, QueryChangeList queryChangeList) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
//        //查询所有设备
//        List<Device> deviceAll = deviceService.findDeviceListByArea(null, null);
//        if (CollectionUtils.isEmpty(deviceAll)) {
//            return null;
//        }
        //查询条件构造
        Specification<ChangeList> spec = (Specification<ChangeList>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            String date = "";
            String year = queryChangeList.getYear();
            String month = queryChangeList.getMonth();
            if (StringUtils.isNotEmpty(year)) {
                date = year;
                if (StringUtils.isNotBlank(month)) {
                    date = date + "-" + month;
                }
            }
            //过滤更换时间
            if (StringUtils.isNotBlank(date)) {
                expressions.add(cb.like(root.get("planReplaceTime").as(String.class), "%" + date + "%"));
            }
            //过滤完成状态
            if (queryChangeList.getReplaceFinshed() != null) {
                expressions.add(cb.equal(root.get("replaceFinshed").as(Integer.class), queryChangeList.getReplaceFinshed()));
            }
            if (StringUtils.isNotBlank(queryChangeList.getDeviceId())) {
                expressions.add(cb.like(root.get("deviceId").as(String.class), "%" + queryChangeList.getDeviceId() + "%"));
            }
//            //过滤设备
//            CriteriaBuilder.In<Object> in = cb.in(root.get("deviceId").as(String.class));
//            if (!CollectionUtils.isEmpty(deviceAll)) {
//                for (Device device : deviceAll) {
//                    in.value(device.getDeviceId());
//                }
//                expressions.add(in);
//            }
            return predicate;
        };
        return changeListRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<ChangeList> findAllById(List<Integer> ids) {
        return changeListRepository.findAllById(ids);
    }

    @Override
    public  List<ChangeList> getAll(){
        return changeListRepository.getAll();
    }
}


