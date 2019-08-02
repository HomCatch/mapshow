package com.xiaohe.experienceUser.modules.loginlog.service.impl;

import com.xiaohe.experienceUser.modules.sys.dao.SysUserDao;
import com.xiaohe.experienceUser.modules.sys.entity.SysUserEntity;
import com.xiaohe.experienceUser.modules.sys.service.SysDeptService;
import com.xiaohe.experienceUser.modules.sys.service.SysRoleDeptService;
import com.xiaohe.experienceUser.modules.sys.service.SysUserRoleService;
import com.xiaohe.experienceUser.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.experienceUser.modules.loginlog.service.LoginLogService;
import com.xiaohe.experienceUser.modules.loginlog.entity.LoginLog;
import com.xiaohe.experienceUser.modules.loginlog.entity.QueryLoginLog;
import com.xiaohe.experienceUser.modules.loginlog.jpa.LoginLogRepository;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * <p>
 * LoginLog服务类
 * </p>
 *
 * @author gmq
 * @since 2018-12-25
 */

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogRepository loginLogRepository;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 保存对象
     *
     * @param loginLog 对象
     *                 持久对象，或者对象集合
     */
    @Override
    public LoginLog save(LoginLog loginLog) {
        return loginLogRepository.save(loginLog);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        loginLogRepository.deleteInBatch(loginLogRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean exists(Integer id) {
        return loginLogRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return loginLogRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return LoginLog对象
     */
    @Override
    public LoginLog findById(Integer id) {
        Optional<LoginLog> optional = loginLogRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<LoginLog>对象
     */
    @Override
    public Page<LoginLog> findAll(int page, int pageSize) {
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        return loginLogRepository.findAll(pageable);
    }

    @Override
    public Page<LoginLog> findAll(int page, int pageSize, QueryLoginLog queryLoginLog) {
        if (queryLoginLog == null) {
            return findAll(page, pageSize);
        }
        //过滤自己的广告
        Pageable pageable = new PageRequest(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<LoginLog> spec = new Specification<LoginLog>() {
            @Override
            public Predicate toPredicate(Root<LoginLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (queryLoginLog.getOperateType() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("operateType").as(Integer.class), queryLoginLog.getOperateType()));
                }

                if (queryLoginLog.getStatus() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("status").as(Integer.class), queryLoginLog.getStatus()));
                }

                if (StringUtils.isNotBlank(queryLoginLog.getIp())) {
                    predicate.getExpressions().add(cb.like(root.get("ip").as(String.class), "%" + queryLoginLog.getIp() + "%"));
                }
                if (StringUtils.isNotBlank(queryLoginLog.getUserAgent())) {
                    predicate.getExpressions().add(cb.like(root.get("userAgent").as(String.class), "%" + queryLoginLog.getUserAgent() + "%"));
                }
                if (StringUtils.isNotBlank(queryLoginLog.getStartTime())) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), queryLoginLog.getStartTime()));
                }
                if (StringUtils.isNotBlank(queryLoginLog.getEndTime())) {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class), queryLoginLog.getEndTime()));
                }
                List<String> allSubUser = findAllSubUser();
                if (!CollectionUtils.isEmpty(allSubUser)) {
                    if (StringUtils.isNotBlank(queryLoginLog.getUserName())) {
                        allSubUser.add(queryLoginLog.getUserName());
                    }
                    CriteriaBuilder.In<String> in = cb.in(root.get("userName"));
                    for (String s : allSubUser) {
                        in.value(s);
                    }
                    predicate.getExpressions().add(cb.and(in));
                } else {
                    if (StringUtils.isNotBlank(queryLoginLog.getUserName())) {
                        predicate.getExpressions().add(cb.like(root.get("userName").as(String.class), "%" + queryLoginLog.getUserName() + "%"));
                    }
                }
                return predicate;
            }

        };
        return loginLogRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<LoginLog> findAllById(List<Integer> ids) {
        return loginLogRepository.findAllById(ids);
    }

    @Override
    public List<String> findAllSubUser() {
        SysUserEntity user = ShiroUtils.getUserEntity();
        //部门ID列表
        Set<Long> deptIdList = new HashSet<>();

        //用户角色对应的部门ID列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
        if (roleIdList.size() > 0) {
            List<Long> userDeptIdList = sysRoleDeptService.queryDeptIdList(roleIdList.toArray(new Long[roleIdList.size()]));
            deptIdList.addAll(userDeptIdList);
        }

        //用户子部门ID列表
        List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(user.getDeptId());
        if (!CollectionUtils.isEmpty(subDeptIdList)) {
            deptIdList.addAll(subDeptIdList);
        }
        deptIdList.add(user.getUserId());
        //根据deptId获取用户ID
        Long[] longs = new Long[deptIdList.size()];
        deptIdList.toArray(longs);
        return sysUserDao.queryAllSubUserId(longs);
    }
}


