package com.xiaohe.mapshow.modules.device.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.device.entity.Device;
import com.xiaohe.mapshow.modules.device.entity.QueryDevice;
import com.xiaohe.mapshow.modules.device.jpa.DeviceRepository;
import com.xiaohe.mapshow.modules.device.service.DeviceService;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.modules.sys.service.SysUserService;
import com.xiaohe.mapshow.modules.sys.shiro.ShiroUtils;
import com.xiaohe.mapshow.modules.userinfo.entity.UserInfo;
import com.xiaohe.mapshow.modules.userinfo.jpa.UserInfoRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Device服务类
 * </p>
 *
 * @author gmq
 * @since 2019-03-28
 */

@Service
@DS("#session.tenantName")
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Device findCustInfoByDevId(String devId) {
        return deviceRepository.findCustInfoByDevId(devId);
    }

    /**
     * 保存对象
     *
     * @param device 对象
     *               持久对象，或者对象集合
     */
    @Override
    public Device save(Device device) {
        device.setUpdateTime(new Date());
        //绑定时间
        if (device.getBindedUserId() != null) {
            Date bindTime = device.getBindTime();
            device.setBindTime(bindTime == null ? new Date() : bindTime);
        }
        if (StringUtils.isNotBlank(device.getDeptId())) {
            device.setBindedUserId(Integer.parseInt(device.getDeptId().trim()));
        }
        return deviceRepository.save(device);
    }

    @Override
    @DS("#dbName")
    public void saveDev(Device device,String dbName){
        device.setUpdateTime(new Date());
        //绑定时间
        if (device.getBindedUserId() != null) {
            Date bindTime = device.getBindTime();
            device.setBindTime(bindTime == null ? new Date() : bindTime);
        }
        if (StringUtils.isNotBlank(device.getDeptId())) {
            device.setBindedUserId(Integer.parseInt(device.getDeptId().trim()));
        }
         deviceRepository.save(device);
    }

    /**
     * 删除对象
     *
     * @param device 对象
     */
    @Override
    public void delete(Device device) {
        deviceRepository.delete(device);
    }

    @Override
    public void deleteAll(List<Device> list) {
        deviceRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        deviceRepository.deleteInBatch(deviceRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return deviceRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return deviceRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return Device对象
     */
    @Override
    public Device findById(Integer id) {
        Optional<Device> optional = deviceRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<Device>对象
     */
    @Override
    public Page<Device> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return deviceRepository.findAll(pageable);
    }

    @Override
    public Page<Device> findAll(int page, int pageSize, QueryDevice queryDevice) {
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Specification<Device> spec = null;
        //查询条件构造
        spec = (Specification<Device>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (queryDevice.getSpecial() != null) {
                predicate.getExpressions().add(cb.equal(root.get("special").as(Integer.class), queryDevice.getSpecial()));
            }

            if (queryDevice.getGeneralInTds() != null) {
                predicate.getExpressions().add(cb.equal(root.get("generalInTds").as(Integer.class), queryDevice.getGeneralInTds()));
            }

            if (queryDevice.getHasAmountProbe() != null) {
                predicate.getExpressions().add(cb.equal(root.get("hasAmountProbe").as(Integer.class), queryDevice.getHasAmountProbe()));
            }

            if (queryDevice.getStatus() != null) {
                predicate.getExpressions().add(cb.equal(root.get("status").as(Integer.class), queryDevice.getStatus()));
            }

            if (queryDevice.getRunState() != null) {
                predicate.getExpressions().add(cb.equal(root.get("runState").as(Integer.class), queryDevice.getRunState()));
            }

            if (queryDevice.getRecordInterval() != null) {
                predicate.getExpressions().add(cb.equal(root.get("recordInterval").as(Integer.class), queryDevice.getRecordInterval()));
            }


            if (StringUtils.isNotBlank(queryDevice.getDeviceId())) {
                predicate.getExpressions().add(cb.like(root.get("deviceId").as(String.class), "%" + queryDevice.getDeviceId() + "%"));
            }
            if (StringUtils.isNotBlank(queryDevice.getDeviceName())) {
                predicate.getExpressions().add(cb.like(root.get("deviceName").as(String.class), "%" + queryDevice.getDeviceName() + "%"));
            }
            if (StringUtils.isNotBlank(queryDevice.getRemark())) {
                predicate.getExpressions().add(cb.like(root.get("remark").as(String.class), "%" + queryDevice.getRemark() + "%"));
            }
            if (queryDevice.getTds() != null) {
                predicate.getExpressions().add(cb.equal(root.get("tds").as(Integer.class), queryDevice.getTds()));
            }

            if (queryDevice.getPurifyTds() != null) {
                predicate.getExpressions().add(cb.equal(root.get("purifyTds").as(Integer.class), queryDevice.getPurifyTds()));
            }

            if (StringUtils.isNotBlank(queryDevice.getDeviceDesc())) {
                predicate.getExpressions().add(cb.like(root.get("deviceDesc").as(String.class), "%" + queryDevice.getDeviceDesc() + "%"));
            }
            if (queryDevice.getRegionId() != null) {
                predicate.getExpressions().add(cb.equal(root.get("regionId").as(Integer.class), queryDevice.getRegionId()));
            }

            if (queryDevice.getDataSource() != null) {
                predicate.getExpressions().add(cb.equal(root.get("dataSource").as(Integer.class), queryDevice.getDataSource()));
            }

            if (StringUtils.isNotBlank(queryDevice.getProvince())) {
                predicate.getExpressions().add(cb.like(root.get("province").as(String.class), "%" + queryDevice.getProvince() + "%"));
            }
            //如果是超管  不过滤  按经销商过滤
            Integer bindedUserId = queryDevice.getBindedUserId();
            if ((bindedUserId != null && !bindedUserId.equals(1)) && 1 != userEntity.getDemoFlag()) {
                Long[] selfAndSubDeptIds = sysDeptService.getSelfAndSubDeptIds();
                CriteriaBuilder.In<Integer> in = cb.in(root.get("bindedUserId"));
                for (Long s : selfAndSubDeptIds) {
                    in.value(s.intValue());
                }
                predicate.getExpressions().add(cb.and(in));
            }
            if (StringUtils.isNotBlank(queryDevice.getBindAccount())) {
                //查询模糊匹配的用户ID
                List<UserInfo> allByNickNameLike = userInfoRepository.findAllByNickNameLike("%" + queryDevice.getBindAccount() + "%");
                CriteriaBuilder.In<Integer> in = cb.in(root.get("bindedAppId"));
                for (UserInfo s : allByNickNameLike) {
                    in.value(s.getId());
                }
                predicate.getExpressions().add(cb.and(in));
            }
            return predicate;
        };
        return deviceRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<Device> findAllById(List<Integer> ids) {
        return deviceRepository.findAllById(ids);
    }

    /**
     * 按运行状态查询0离线 1在线
     *
     * @param integer 状态
     * @return long
     */
    @Override
    public long countByRunStateEquals(Integer integer, Integer userId) {
        if (1 == userId) {
            return deviceRepository.countByRunStateEquals(integer);
        }
        return deviceRepository.countByRunStateEqualsAndBindedUserId(integer, userId);
    }

    /**
     * 按运行状态查询0离线 1在线
     *
     * @param integer    状态
     * @param userIdList 用户 Id集合
     * @return long
     */
    @Override
    public long countByRunStateEqualsAndBindedUserIdIn(Integer integer, List<Integer> userIdList) {
        if (1 == userIdList.size() && 1 == userIdList.get(0)) {
            return deviceRepository.countByRunStateEquals(integer);
        }
        return deviceRepository.countByRunStateEqualsAndBindedUserIdIn(integer, userIdList);
    }

    @Override
    public List<String> findDeviceIdByUserIdIn(List<Integer> userIdList) {
        return deviceRepository.findDeviceIdByUserIdIn(userIdList);
    }

    @Override
    public long countByBindedUserId(Integer userId) {
        if (1 == userId) {
            return deviceRepository.count();
        }
        return deviceRepository.countByBindedUserId(userId);
    }

    /**
     * 根据用户ID集合查询设备总数 ID为1全查
     *
     * @return long
     */
    @Override
    public long countByBindedUserIdIn(List<Integer> userIdList) {
        if (1 == userIdList.size() && 1 == userIdList.get(0)) {
            return deviceRepository.count();
        }
        return deviceRepository.countByBindedUserIdIn(userIdList);
    }

    @Override
    public List<Device> findDeviceListByArea(String province, String city) {
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        Long userId1 = userEntity.getUserId();
        List<Long> userIds = null;
        //如果是超管或者是演示用户不过滤
        if (1L != userId1 && 1 != userEntity.getDemoFlag()) {
            //递归查询所有子部门
            List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(userEntity.getDeptId());
            if (!CollectionUtils.isEmpty(subDeptIdList)) {
                userIds = sysUserService.findUserByDeptIds(subDeptIdList);
            }
        }
        //查询条件构造
        List<Long> finalUserIds = userIds;

        Specification<Device> spec = (root, query, cb) -> {
            Predicate conjunction = cb.conjunction();
            List<Expression<Boolean>> expressions = conjunction.getExpressions();
            Long userId = userEntity.getUserId();
            if (userId != null && userId.intValue() != 1 && 1 != userEntity.getDemoFlag()) {
                CriteriaBuilder.In<Object> in = cb.in(root.get("bindedSubUserId").as(Long.class));
                if (!CollectionUtils.isEmpty(finalUserIds)) {
                    for (Long id : finalUserIds) {
                        in.value(id);
                    }
                    expressions.add(cb.or(cb.and(cb.equal(root.get("bindedSubUserId"), userId)), cb.equal(root.get("bindedUserId"), userId), in));
                } else {
                    expressions.add(cb.or(cb.and(cb.equal(root.get("bindedSubUserId"), userId)), cb.equal(root.get("bindedUserId"), userId)));
                }
            }
            if (StringUtils.isNotBlank(province)) {
                if (!"全国".equals(province)) {
                    expressions.add(cb.equal(root.get("province"), province));
                }
            }
            if (StringUtils.isNotBlank(city)) {
                expressions.add(cb.equal(root.get("city"), city));
            }


            return conjunction;

        };
        return deviceRepository.findAll(spec);
    }

    @Override
    @DS("#dbName")
    public void updateDevOffline(String dbName) {
        deviceRepository.updateDevOffline();
    }

    @Override
    @DS("#dbName")
    public void updateDevOnline(int count,String dbName) {
        deviceRepository.updateDevOnline(count);
    }

    @Override
    @DS("#dbName")
    public int findActive(String dbName) {
        return deviceRepository.findActive();
    }

    @Override
    @DS("#dbName")
    public long getCount(String dbName){
        return deviceRepository.count();
    }

    @Override
    public List<Device> getAll(){
        return deviceRepository.getAll();
    }
}


