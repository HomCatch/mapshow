package com.xiaohe.mapshow.modules.filterlist.jpa;

import org.springframework.data.domain.Page;
import com.xiaohe.mapshow.modules.filterlist.entity.FilterList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 
 *  jpa 接口
 *
 * @author hzh
 * @since 2019-04-08
 */

public interface FilterListRepository extends JpaRepository<FilterList, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<FilterList> findAll(Specification<FilterList> spec, Pageable pageable);


    @Query(value = "select count(id) from filter_list d where d.device_id = :devId", nativeQuery = true)
    int selectDevice(@Param("devId") String devId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update device d  SET d.set_date = ?1 where d.device_id = ?2", nativeQuery = true)
    void updateSetDateByDeviceId(String setDate, String deviceId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into filter_list (device_id,first_filter,second_filter,third_filter,fourth_filter,cust,address,tel) values(?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    int insertFilterList(String deviceId, String firstFilter, String secondFilter, String thirdFilter, String fourthFilter, String customer, String address, String phone_number);

    @Query(value = "select * from filter_list", nativeQuery = true)
    List<FilterList> findFilter();

    @Query(value = "select plan_replace_time from change_list where device_id = :devId", nativeQuery = true)
    List findChangeList(@Param("devId") String devId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into change_list (device_id,customer,phone_number,replace_first_filter,replace_second_filter,replace_third_filter,replace_fouth_filter,plan_replace_time,address,replace_finshed) values(?1,?6,?8,?2,?3,?4,?5,?9,?7,?10)",nativeQuery = true)
    int insertChangeList(String deviceId, int firstFilter, int secondFilter, int thirdFilter, int fourthFilter, String customer, String address, String tel, String needTime, int status);

    @Query(value = "select count(id) from change_list d where d.device_id = :devId", nativeQuery = true)
    int selectReplaceFilterInfo(@Param("devId") String devId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update filter_list d  SET d.first_filter = ?2 where d.device_id = ?1", nativeQuery = true)
    void updateFirstFilterByDevId(String deviceId, String firstFilter);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update filter_list d  SET d.second_filter = ?2 where d.device_id = ?1", nativeQuery = true)
    void updateSecondFilterByDevId(String deviceId, String secondFilter);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update filter_list d  SET d.third_filter = ?2 where d.device_id = ?1", nativeQuery = true)
    void updateThirdFilterByDevId(String deviceId, String thirdFilter);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update filter_list d  SET d.fourth_filter = ?2 where d.device_id = ?1", nativeQuery = true)
    void updateFourthFilterByDevId(String deviceId, String fourthFilter);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update filter_list d  SET d.repairer = ?1,d.repairer_phone_number=?2 where d.device_id = ?3", nativeQuery = true)
    void updateRepairerInfo(String repairer, String repairerPhoneNumber, String deviceId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update change_list d  SET d.repairer = ?1,d.repairer_phone_number = ?2,d.remark = ?3,replace_finshed = 1 ,real_replace_time=?6 where d.device_id = ?4 and plan_replace_time =?5", nativeQuery = true)
    void updateChangeList(String repairer, String repairerPhoneNumber, String remark, String devId, String planReplaceTime, String realReplaceTime);

    @Query(value = "select * from filter_list where device_id=?1",nativeQuery = true)
    FilterList findByDevId(String devId);
}