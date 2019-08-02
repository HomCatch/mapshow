package com.xiaohe.mapshow.modules.cloudwaterorder.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.cloudwaterorder.service.CloudWaterOrderService;
import com.xiaohe.mapshow.modules.cloudwaterorder.entity.CloudWaterOrder;
import com.xiaohe.mapshow.modules.cloudwaterorder.entity.QueryCloudWaterOrder;
import com.xiaohe.mapshow.modules.cloudwaterorder.jpa.CloudWaterOrderRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * CloudWaterOrder服务类
 * </p>
 *
 * @author gmq
 * @since 2019-04-19
 */

@Service
@DS("#session.tenantName")
public class CloudWaterOrderServiceImpl implements CloudWaterOrderService {

    @Autowired
    private CloudWaterOrderRepository cloudWaterOrderRepository;

    /**
     * 保存对象
     *
     * @param cloudWaterOrder 对象
     *                        持久对象，或者对象集合
     */
    @Override
    public CloudWaterOrder save(CloudWaterOrder cloudWaterOrder) {
        return cloudWaterOrderRepository.save(cloudWaterOrder);
    }

    /**
     * 删除对象
     *
     * @param cloudWaterOrder 对象
     */
    @Override
    public void delete(CloudWaterOrder cloudWaterOrder) {
        cloudWaterOrderRepository.delete(cloudWaterOrder);
    }

    @Override
    public void deleteAll(List<CloudWaterOrder> list) {
        cloudWaterOrderRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        cloudWaterOrderRepository.deleteInBatch(cloudWaterOrderRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return cloudWaterOrderRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return cloudWaterOrderRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return CloudWaterOrder对象
     */
    @Override
    public CloudWaterOrder findById(Integer id) {
        Optional<CloudWaterOrder> optional = cloudWaterOrderRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<CloudWaterOrder>对象
     */
    @Override
    public Page<CloudWaterOrder> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return cloudWaterOrderRepository.findAll(pageable);
    }

    @Override
    public Page<CloudWaterOrder> findAll(int page, int pageSize, QueryCloudWaterOrder queryCloudWaterOrder) {
        //过滤自己的广告
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<CloudWaterOrder> spec = new Specification<CloudWaterOrder>() {
            @Override
            public Predicate toPredicate(Root<CloudWaterOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(queryCloudWaterOrder.getOrderNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("orderNumber").as(String.class), "%" + queryCloudWaterOrder.getOrderNumber() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterOrder.getDeviceNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("deviceNumber").as(String.class), "%" + queryCloudWaterOrder.getDeviceNumber() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterOrder.getUserName())) {
                    predicate.getExpressions().add(cb.like(root.get("userName").as(String.class), "%" + queryCloudWaterOrder.getUserName() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterOrder.getPhoneNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("phoneNumber").as(String.class), "%" + queryCloudWaterOrder.getPhoneNumber() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterOrder.getRealName())) {
                    predicate.getExpressions().add(cb.like(root.get("realName").as(String.class), "%" + queryCloudWaterOrder.getRealName() + "%"));
                }
                if (queryCloudWaterOrder.getType() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class), queryCloudWaterOrder.getType()));
                }

                if (queryCloudWaterOrder.getPayType() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("payType").as(Integer.class), queryCloudWaterOrder.getPayType()));
                }

                if (StringUtils.isNotBlank(queryCloudWaterOrder.getThirdNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("thirdNumber").as(String.class), "%" + queryCloudWaterOrder.getThirdNumber() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterOrder.getStartTime())) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), queryCloudWaterOrder.getStartTime()));
                }
                if (StringUtils.isNotBlank(queryCloudWaterOrder.getEndTime())) {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class), queryCloudWaterOrder.getEndTime()));
                }
                return predicate;
            }

        };
        return cloudWaterOrderRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<CloudWaterOrder> findAllById(List<Integer> ids) {
        return cloudWaterOrderRepository.findAllById(ids);
    }

    @Override
    public List<CloudWaterOrder> getAll(){
        return cloudWaterOrderRepository.getAll();
    }
}


