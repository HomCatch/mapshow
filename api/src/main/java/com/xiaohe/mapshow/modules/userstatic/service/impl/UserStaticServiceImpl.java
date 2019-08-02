package com.xiaohe.mapshow.modules.userstatic.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.userstatic.service.UserStaticService;
import com.xiaohe.mapshow.modules.userstatic.entity.UserStatic;
import com.xiaohe.mapshow.modules.userstatic.entity.QueryUserStatic;
import com.xiaohe.mapshow.modules.userstatic.jpa.UserStaticRepository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * UserStatic服务类
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */

@Service
@DS("#session.tenantName")
public class UserStaticServiceImpl implements UserStaticService {

    @Autowired
    private UserStaticRepository userStaticRepository;

    /**
     * 保存对象
     *
     * @param userStatic 对象
     *                   持久对象，或者对象集合
     */
    @Override
    public UserStatic save(UserStatic userStatic) {
        return userStaticRepository.save(userStatic);
    }

    /**
     * 删除对象
     *
     * @param userStatic 对象
     */
    @Override
    public void delete(UserStatic userStatic) {
        userStaticRepository.delete(userStatic);
    }

    @Override
    public void deleteAll(List<UserStatic> list) {
        userStaticRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        userStaticRepository.deleteInBatch(userStaticRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return userStaticRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return userStaticRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return UserStatic对象
     */
    @Override
    public UserStatic findById(Integer id) {
        Optional<UserStatic> optional = userStaticRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<UserStatic>对象
     */
    @Override
    public Page<UserStatic> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "date");
        return userStaticRepository.findAll(pageable);
    }

    @Override
    @DS("#dbName")
    public Page<UserStatic> findAll(int page, int pageSize,String dbName) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.ASC, "date");
        return userStaticRepository.findAll(pageable);
    }

    @Override
    public Page<UserStatic> findAll(int page, int pageSize, QueryUserStatic queryUserStatic) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "date");
        //查询条件构造
        Specification<UserStatic> spec = new Specification<UserStatic>() {
            @Override
            public Predicate toPredicate(Root<UserStatic> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (queryUserStatic.getUserId() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("userId").as(Integer.class), queryUserStatic.getUserId()));
                }

                if (queryUserStatic.getRegistCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("registCount").as(Integer.class), queryUserStatic.getRegistCount()));
                }

                if (queryUserStatic.getActiveToday() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("activeToday").as(Integer.class), queryUserStatic.getActiveToday()));
                }

                if (queryUserStatic.getActiveWeek() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("activeWeek").as(Integer.class), queryUserStatic.getActiveWeek()));
                }

                if (queryUserStatic.getActiveMonth() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("activeMonth").as(Integer.class), queryUserStatic.getActiveMonth()));
                }

                if (queryUserStatic.getAddCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("addCount").as(Integer.class), queryUserStatic.getAddCount()));
                }

                if (queryUserStatic.getTotalCount() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("totalCount").as(Integer.class), queryUserStatic.getTotalCount()));
                }

                return predicate;
            }

        };
        return userStaticRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<UserStatic> findAllById(List<Integer> ids) {
        return userStaticRepository.findAllById(ids);
    }

    @Override
    @DS("#dbName")
    public void save(UserStatic userStatic,String dbName){
        userStaticRepository.save(userStatic);
    }
}


