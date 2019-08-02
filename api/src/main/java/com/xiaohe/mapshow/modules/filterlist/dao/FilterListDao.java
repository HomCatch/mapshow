package com.xiaohe.mapshow.modules.filterlist.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaohe.mapshow.modules.filterlist.entity.FilterList;

import java.util.List;

/**
 *
 *  FilterListDao层
 *
 * @author hzh
 * @since 2019-04-08
 */
public interface FilterListDao extends BaseMapper<FilterList> {
    int selectDevice(String devId);
    void updateChangeList(String repairer, String repairerPhoneNumber, String remark, String devId, String planReplaceTime, String realReplaceTime);
    void updateRepairerInfo(String repairer, String repairerPhoneNumber, String deviceId);
    void updateFourthFilterByDevId(String deviceId, String fourthFilter);
    void updateThirdFilterByDevId(String deviceId, String thirdFilter);
    void updateSecondFilterByDevId(String deviceId, String secondFilter);
    void updateFirstFilterByDevId(String deviceId, String firstFilter);
    int selectReplaceFilterInfo(String devId);
    void insertChangeList(String deviceId, int firstFilter, int secondFilter, int thirdFilter, int fourthFilter, String customer, String address, String tel, String needTime, int status);
    List findChangeList(String devId);
    List<FilterList> findFilter();
}
