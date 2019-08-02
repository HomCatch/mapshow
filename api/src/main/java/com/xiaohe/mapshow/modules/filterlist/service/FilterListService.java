package com.xiaohe.mapshow.modules.filterlist.service;

import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.filterlist.entity.FilterList;
import com.xiaohe.mapshow.modules.filterlist.entity.QueryFilterList;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p>
 * FilterList接口
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

public interface FilterListService extends IBaseServiceTwo<FilterList, Integer> {
    public FilterList findByDevId(String devId);

    public void updateChangeList(String repairer, String repairerPhoneNumber, String remark, String devId, String planReplaceTime, String realReplaceTime);

    public void updateRepairerInfo(String repairer, String repairerPhoneNumber, String deviceId);

    public void updateFourthFilterByDevId(String deviceId, String fourthFilter);

    public void updateThirdFilterByDevId(String deviceId, String thirdFilter);

    public void updateSecondFilterByDevId(String deviceId, String secondFilter);

    public void updateFirstFilterByDevId(String deviceId, String firstFilter);

    public int selectReplaceFilterInfo(String devId);

    public void insertChangeList(String deviceId, int firstFilter, int secondFilter, int thirdFilter, int fourthFilter, String customer, String address, String tel, String needTime, int status);

    public List findChangeList(String devId);

    public List<FilterList> findFilter();

    public int selectDevice(String deviceId);

    public void updateSetDateByDeviceId(String setDate, String deviceId);

    public void insertFilterList(String deviceId, String firstFilter, String secondFilter, String thirdFilter, String fourthFilter, String customer, String address, String phone_number);

    /**
     * 按条件查询
     *
     * @param page     页数
     * @param pageSize 数量
     * @param
     * @return Page
     */
    Page<FilterList> findAll(int page, int pageSize, QueryFilterList queryFilterList);

    /**
     * 根据Id查询list
     *
     * @return
     */
    List<FilterList> findAllById(List<Integer> ids);

}
