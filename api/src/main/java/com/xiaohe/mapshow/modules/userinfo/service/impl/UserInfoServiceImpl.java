package com.xiaohe.mapshow.modules.userinfo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.device.entity.Device;
import com.xiaohe.mapshow.modules.device.service.DeviceService;
import com.xiaohe.mapshow.modules.userinfo.entity.OrderDevice;
import com.xiaohe.mapshow.modules.userinfo.entity.QueryUserInfo;
import com.xiaohe.mapshow.modules.userinfo.entity.UserInfo;
import com.xiaohe.mapshow.modules.userinfo.jpa.UserInfoRepository;
import com.xiaohe.mapshow.modules.userinfo.service.OrderDeviceService;
import com.xiaohe.mapshow.modules.userinfo.service.UserInfoService;
import com.xiaohe.mapshow.utils.DevUtils;
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
 * 用户信息表 HxclUserInfo服务类
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

@Service
@DS("#session.tenantName")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private OrderDeviceService orderDeviceService;

    /**
     * 保存对象
     *
     * @param hxclUserInfo 对象
     *                     持久对象，或者对象集合
     */
    @Override
    public UserInfo save(UserInfo hxclUserInfo) {
        return userInfoRepository.save(hxclUserInfo);
    }

    /**
     * 删除对象
     *
     * @param hxclUserInfo 对象
     */
    @Override
    public void delete(UserInfo hxclUserInfo) {
        userInfoRepository.delete(hxclUserInfo);
    }


    @Override
    public void deleteAll(List<UserInfo> list) {
        userInfoRepository.deleteAll();
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        userInfoRepository.deleteInBatch(userInfoRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return userInfoRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return userInfoRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return HxclUserInfo对象
     */
    @Override
    public UserInfo findById(Integer id) {
        Optional<UserInfo> byId = userInfoRepository.findById(id);
        return byId.orElse(null);
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<HxclUserInfo>对象
     */
    @Override
    public Page<UserInfo> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return userInfoRepository.findAll(pageable);
    }

    @Override
    public Page<UserInfo> findAll(int page, int pageSize, QueryUserInfo queryHxclUserInfo) {
        //获取登录用户下的设备
        List<Device> deviceList = deviceService.findDeviceListByArea(null, null);
        if (CollectionUtils.isEmpty(deviceList)) {
            return null;
        }
        //同过设备号查询所有绑定的设备
//        List<OrderDevice> allUserDevice = orderDeviceService.findAllByDeviceNoIn(DevUtils.getDeviceIds(deviceList));
//        if (CollectionUtils.isEmpty(allUserDevice)) {
//            return null;
//        }
        //过滤自己的广告
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Specification<UserInfo> spec = (Specification<UserInfo>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            if (StringUtils.isNotBlank(queryHxclUserInfo.getPhoneNumber())) {
                expressions.add(cb.like(root.get("phoneNumber").as(String.class), "%" + queryHxclUserInfo.getPhoneNumber() + "%"));
            }
            if (StringUtils.isNotBlank(queryHxclUserInfo.getNickName())) {
                expressions.add(cb.like(root.get("nickName").as(String.class), "%" + queryHxclUserInfo.getNickName() + "%"));
            }
//            CriteriaBuilder.In<Integer> in = cb.in(root.get("id").as(Integer.class));
//            for (OrderDevice orderDevice : allUserDevice) {
//                in.value(orderDevice.getUserId());
//            }
//            expressions.add(in);

            return predicate;
        };
        return userInfoRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<UserInfo> findAllById(List<Integer> ids) {
        return userInfoRepository.findAllById(ids);
    }


    @Override
    @DS("#dbName")
    public void updateUserActive(String dbName,int count){
        userInfoRepository.updateUserActive(count);
    }

    @Override
    @DS("#dbName")
    public int getWeekActive(String dbName){
        return userInfoRepository.getWeekActive();
    }

    @Override
    @DS("#dbName")
    public int getMonthActive(String dbName){
        return userInfoRepository.getMonthActive();
    }

    @Override
    @DS("#dbName")
    public List getIds(String dbName){
        return userInfoRepository.getIds();
    }

    @Override
    @DS("#dbName")
    public void save(UserInfo userInfo,String dbName){
        userInfoRepository.save(userInfo);
    }

    @Override
    @DS("#dbName")
    public long count(String dbName){
        return userInfoRepository.count();
    }

    @Override
    public  List<UserInfo> getAll(){
        return userInfoRepository.getAll();
    }
}


