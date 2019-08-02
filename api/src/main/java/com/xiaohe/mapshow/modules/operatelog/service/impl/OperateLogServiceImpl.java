package com.xiaohe.mapshow.modules.operatelog.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.loginlog.entity.LoginLog;
import com.xiaohe.mapshow.modules.loginlog.service.LoginLogService;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.modules.operatelog.service.OperateLogService;
import com.xiaohe.mapshow.modules.operatelog.entity.OperateLog;
import com.xiaohe.mapshow.modules.operatelog.entity.QueryOperateLog;
import com.xiaohe.mapshow.modules.operatelog.jpa.OperateLogRepository;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * OperateLog服务类
 * </p>
 *
 * @author gmq
 * @since 2018-12-25
 */

@Service
@DS("#session.tenantName")
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogRepository operateLogRepository;
    @Autowired
    private LoginLogService loginLogService;


    /**
     * 保存对象
     *
     * @param operateLog 对象
     *                   持久对象，或者对象集合
     */
    @Override
    public OperateLog save(OperateLog operateLog) {
        return operateLogRepository.save(operateLog);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        operateLogRepository.deleteInBatch(operateLogRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean exists(Integer id) {
        return operateLogRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return operateLogRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return OperateLog对象
     */
    @Override
    public OperateLog findById(Integer id) {
        Optional<OperateLog> optional = operateLogRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<LogOperate>对象
     */
    @Override
    public Page<OperateLog> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return operateLogRepository.findAll(pageable);
    }

    @Override
    public Page<OperateLog> findAll(int page, int pageSize, QueryOperateLog queryOperateLog) {
        if (queryOperateLog == null) {
            return findAll(page, pageSize);
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<OperateLog> spec = (Specification<OperateLog>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (StringUtils.isNotBlank(queryOperateLog.getUserName())) {
                predicate.getExpressions().add(cb.like(root.get("userName").as(String.class), "%" + queryOperateLog.getUserName() + "%"));
            }
            if (StringUtils.isNotBlank(queryOperateLog.getOperateInfo())) {
                predicate.getExpressions().add(cb.like(root.get("operateInfo").as(String.class), "%" + queryOperateLog.getOperateInfo() + "%"));
            }
            if (StringUtils.isNotBlank(queryOperateLog.getReqUrl())) {
                predicate.getExpressions().add(cb.like(root.get("reqUrl").as(String.class), "%" + queryOperateLog.getReqUrl() + "%"));
            }
            if (StringUtils.isNotBlank(queryOperateLog.getReqType())) {
                predicate.getExpressions().add(cb.like(root.get("reqType").as(String.class), "%" + queryOperateLog.getReqType() + "%"));
            }
            if (StringUtils.isNotBlank(queryOperateLog.getReqParam())) {
                predicate.getExpressions().add(cb.like(root.get("reqParam").as(String.class), "%" + queryOperateLog.getReqParam() + "%"));
            }
            if (queryOperateLog.getReqTime() != null) {
                predicate.getExpressions().add(cb.equal(root.get("reqTime").as(Integer.class), queryOperateLog.getReqTime()));
            }

            if (queryOperateLog.getStatus() != null) {
                predicate.getExpressions().add(cb.equal(root.get("status").as(Integer.class), queryOperateLog.getStatus()));
            }

            if (StringUtils.isNotBlank(queryOperateLog.getIp())) {
                predicate.getExpressions().add(cb.like(root.get("ip").as(String.class), "%" + queryOperateLog.getIp() + "%"));
            }
            if (StringUtils.isNotBlank(queryOperateLog.getUserAgent())) {
                predicate.getExpressions().add(cb.like(root.get("userAgent").as(String.class), "%" + queryOperateLog.getUserAgent() + "%"));
            }
            if (StringUtils.isNotBlank(queryOperateLog.getStartTime())) {
                predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), queryOperateLog.getStartTime()));
            }
            if (StringUtils.isNotBlank(queryOperateLog.getEndTime())) {
                predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class), queryOperateLog.getEndTime()));
            }
            //按部门权限 过滤数据  超管和演示用户不过滤
            //所有子部门
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            if (1 != userEntity.getUserId() && 1 != userEntity.getDemoFlag()) {
                List<String> allSubUser = loginLogService.findAllSubUserName();
                if (!CollectionUtils.isEmpty(allSubUser)) {
                    if (StringUtils.isNotBlank(queryOperateLog.getUserName())) {
                        allSubUser.add(queryOperateLog.getUserName());
                    }
                    CriteriaBuilder.In<String> in = cb.in(root.get("userName"));
                    for (String s : allSubUser) {
                        in.value(s);
                    }
                    predicate.getExpressions().add(cb.and(in));
                } else {
                    //没有子部门
                    if (StringUtils.isNotBlank(queryOperateLog.getUserName())) {
                        predicate.getExpressions().add(cb.like(root.get("userName").as(String.class), "%" + queryOperateLog.getUserName() + "%"));
                    }
                }
            }
            return predicate;
        };
        return operateLogRepository.findAll(spec, pageable);
    }

    @Override
    public List<OperateLog> findAllById(List<Integer> ids) {
        return operateLogRepository.findAllById(ids);
    }

    @Override
    @DS("#operateTenantName")
    public OperateLog save(String operateTenantName, OperateLog operateLog) {
        return operateLogRepository.save(operateLog);
    }
}


