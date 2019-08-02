package com.xiaohe.mapshow.modules.cloudwaterdeposit.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.service.CloudWaterDepositService;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.entity.CloudWaterDeposit;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.entity.QueryCloudWaterDeposit;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.jpa.CloudWaterDepositRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * CloudWaterDeposit服务类
 * </p>
 *
 * @author gmq
 * @since 2019-04-19
 */

@Service
@DS("#session.tenantName")
public class CloudWaterDepositServiceImpl implements CloudWaterDepositService {

    @Autowired
    private CloudWaterDepositRepository cloudWaterDepositRepository;

    /**
     * 保存对象
     *
     * @param cloudWaterDeposit 对象
     *                          持久对象，或者对象集合
     */
    @Override
    public CloudWaterDeposit save(CloudWaterDeposit cloudWaterDeposit) {
        return cloudWaterDepositRepository.save(cloudWaterDeposit);
    }

    /**
     * 删除对象
     *
     * @param cloudWaterDeposit 对象
     */
    @Override
    public void delete(CloudWaterDeposit cloudWaterDeposit) {
        cloudWaterDepositRepository.delete(cloudWaterDeposit);
    }

    @Override
    public void deleteAll(List<CloudWaterDeposit> list) {
        cloudWaterDepositRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        cloudWaterDepositRepository.deleteInBatch(cloudWaterDepositRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return cloudWaterDepositRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return cloudWaterDepositRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return CloudWaterDeposit对象
     */
    @Override
    public CloudWaterDeposit findById(Integer id) {
        Optional<CloudWaterDeposit> optional = cloudWaterDepositRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<CloudWaterDeposit>对象
     */
    @Override
    public Page<CloudWaterDeposit> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return cloudWaterDepositRepository.findAll(pageable);
    }

    @Override
    public Page<CloudWaterDeposit> findAll(int page, int pageSize, QueryCloudWaterDeposit queryCloudWaterDeposit) {
        //过滤自己的广告
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<CloudWaterDeposit> spec = new Specification<CloudWaterDeposit>() {
            @Override
            public Predicate toPredicate(Root<CloudWaterDeposit> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(queryCloudWaterDeposit.getOrderNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("orderNumber").as(String.class), "%" + queryCloudWaterDeposit.getOrderNumber() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterDeposit.getUserName())) {
                    predicate.getExpressions().add(cb.like(root.get("userName").as(String.class), "%" + queryCloudWaterDeposit.getUserName() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterDeposit.getPhoneNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("phoneNumber").as(String.class), "%" + queryCloudWaterDeposit.getPhoneNumber() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterDeposit.getReceiver())) {
                    predicate.getExpressions().add(cb.like(root.get("receiver").as(String.class), "%" + queryCloudWaterDeposit.getReceiver() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterDeposit.getReceivAddress())) {
                    predicate.getExpressions().add(cb.like(root.get("receivAddress").as(String.class), "%" + queryCloudWaterDeposit.getReceivAddress() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterDeposit.getReceivPhoneNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("receivPhoneNumber").as(String.class), "%" + queryCloudWaterDeposit.getReceivPhoneNumber() + "%"));
                }
                if (queryCloudWaterDeposit.getType() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("type").as(Integer.class), queryCloudWaterDeposit.getType()));
                }

                if (queryCloudWaterDeposit.getPayType() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("payType").as(Integer.class), queryCloudWaterDeposit.getPayType()));
                }

                if (queryCloudWaterDeposit.getOrderType() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("orderType").as(Integer.class), queryCloudWaterDeposit.getOrderType()));
                }

                if (StringUtils.isNotBlank(queryCloudWaterDeposit.getThirdNumber())) {
                    predicate.getExpressions().add(cb.like(root.get("thirdNumber").as(String.class), "%" + queryCloudWaterDeposit.getThirdNumber() + "%"));
                }
                if (StringUtils.isNotBlank(queryCloudWaterDeposit.getDeviceId())) {
                    predicate.getExpressions().add(cb.like(root.get("deviceId").as(String.class), "%" + queryCloudWaterDeposit.getDeviceId() + "%"));
                }
                return predicate;
            }

        };
        return cloudWaterDepositRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<CloudWaterDeposit> findAllById(List<Integer> ids) {
        return cloudWaterDepositRepository.findAllById(ids);
    }

    @Override
    public List<CloudWaterDeposit> getAll(){
        return cloudWaterDepositRepository.getAll();
    }
}


