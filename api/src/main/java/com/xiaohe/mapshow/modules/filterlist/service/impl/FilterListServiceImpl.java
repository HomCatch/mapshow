package com.xiaohe.mapshow.modules.filterlist.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaohe.mapshow.modules.filterlist.dao.FilterListDao;
import com.xiaohe.mapshow.modules.filterlist.entity.FilterList;
import com.xiaohe.mapshow.modules.filterlist.entity.QueryFilterList;
import com.xiaohe.mapshow.modules.filterlist.jpa.FilterListRepository;
import com.xiaohe.mapshow.modules.filterlist.service.FilterListService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * FilterList服务类
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

@Service
@DS("#session.tenantName")
public class FilterListServiceImpl extends ServiceImpl<FilterListDao, FilterList> implements FilterListService {

    @Autowired
    private FilterListRepository filterListRepository;

    @Override
    public FilterList findByDevId(String devId){
        return filterListRepository.findByDevId(devId);
    }
    @Override
    public int selectDevice(String deviceId) {
        return filterListRepository.selectDevice(deviceId);
    }

    @Override
    public void updateChangeList(String repairer, String repairerPhoneNumber, String remark, String devId, String planReplaceTime, String realReplaceTime) {
        filterListRepository.updateChangeList(repairer, repairerPhoneNumber, remark, devId, planReplaceTime, realReplaceTime);
    }

    @Override
    public void updateRepairerInfo(String repairer, String repairerPhoneNumber, String deviceId) {
        filterListRepository.updateRepairerInfo(repairer, repairerPhoneNumber, deviceId);
    }

    @Override
    public void updateFourthFilterByDevId(String deviceId, String fourthFilter) {
        filterListRepository.updateFourthFilterByDevId(deviceId, fourthFilter);
    }

    @Override
    public void updateThirdFilterByDevId(String deviceId, String thirdFilter) {
        filterListRepository.updateThirdFilterByDevId(deviceId, thirdFilter);
    }

    @Override
    public void updateSecondFilterByDevId(String deviceId, String secondFilter) {
        filterListRepository.updateSecondFilterByDevId(deviceId, secondFilter);
    }

    @Override
    public void updateFirstFilterByDevId(String deviceId, String firstFilter) {
        filterListRepository.updateFirstFilterByDevId(deviceId, firstFilter);
    }

    @Override
    public int selectReplaceFilterInfo(String devId) {
        return filterListRepository.selectReplaceFilterInfo(devId);
    }

    @Override
    public void insertChangeList(String deviceId, int firstFilter, int secondFilter, int thirdFilter, int fourthFilter, String customer, String address, String tel, String needTime, int status) {
        filterListRepository.insertChangeList(deviceId, firstFilter, secondFilter, thirdFilter, fourthFilter, customer, address, tel, needTime, status);
    }

    @Override
    public List findChangeList(String devId) {
        return filterListRepository.findChangeList(devId);
    }

    @Override
    public List<FilterList> findFilter() {
        return filterListRepository.findFilter();
    }

    @Override
    public void updateSetDateByDeviceId(String setDate, String deviceId) {
        filterListRepository.updateSetDateByDeviceId(setDate, deviceId);
    }

    @Override
    public void insertFilterList(String deviceId, String firstFilter, String secondFilter, String thirdFilter, String fourthFilter, String customer, String address, String phone_number) {
        //,customer,address,tel
        filterListRepository.insertFilterList(deviceId, firstFilter, secondFilter, thirdFilter, fourthFilter, customer, address, phone_number);
    }


    /**
     * 保存对象
     *
     * @param filterList 对象
     *                   持久对象，或者对象集合
     */
    @Override
    public FilterList save(FilterList filterList) {
        return filterListRepository.save(filterList);
    }

    /**
     * 删除对象
     *
     * @param filterList 对象
     */
    @Override
    public void delete(FilterList filterList) {
        filterListRepository.delete(filterList);
    }

    @Override
    public void deleteAll(List<FilterList> list) {
        filterListRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        filterListRepository.deleteInBatch(filterListRepository.findAllById(ids));
    }


    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return filterListRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return filterListRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return FilterList对象
     */
    @Override
    public FilterList findById(Integer id) {
        Optional<FilterList> optional = filterListRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<FilterList>对象
     */
    @Override
    public Page<FilterList> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return filterListRepository.findAll(pageable);
    }

    @Override
    public Page<FilterList> findAll(int page, int pageSize, QueryFilterList queryFilterList) {
        //过滤自己的广告
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<FilterList> spec = new Specification<FilterList>() {
            @Override
            public Predicate toPredicate(Root<FilterList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(queryFilterList.getDeviceId())) {
                    predicate.getExpressions().add(cb.like(root.get("deviceId").as(String.class), "%" + queryFilterList.getDeviceId() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterList.getFirstFilter())) {
                    predicate.getExpressions().add(cb.like(root.get("firstFilter").as(String.class), "%" + queryFilterList.getFirstFilter() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterList.getSecondFilter())) {
                    predicate.getExpressions().add(cb.like(root.get("secondFilter").as(String.class), "%" + queryFilterList.getSecondFilter() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterList.getThirdFilter())) {
                    predicate.getExpressions().add(cb.like(root.get("thirdFilter").as(String.class), "%" + queryFilterList.getThirdFilter() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterList.getFourthFilter())) {
                    predicate.getExpressions().add(cb.like(root.get("fourthFilter").as(String.class), "%" + queryFilterList.getFourthFilter() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterList.getCust())) {
                    predicate.getExpressions().add(cb.like(root.get("cust").as(String.class), "%" + queryFilterList.getCust() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterList.getAddress())) {
                    predicate.getExpressions().add(cb.like(root.get("address").as(String.class), "%" + queryFilterList.getAddress() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterList.getTel())) {
                    predicate.getExpressions().add(cb.like(root.get("tel").as(String.class), "%" + queryFilterList.getTel() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterList.getRepairer())) {
                    predicate.getExpressions().add(cb.like(root.get("repairer").as(String.class), "%" + queryFilterList.getRepairer() + "%"));
                }
                if (queryFilterList.getRepairerPhoneNumber() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("repairerPhoneNumber").as(Integer.class), queryFilterList.getRepairerPhoneNumber()));
                }

                return predicate;
            }

        };
        return filterListRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<FilterList> findAllById(List<Integer> ids) {
        return filterListRepository.findAllById(ids);
    }


}


