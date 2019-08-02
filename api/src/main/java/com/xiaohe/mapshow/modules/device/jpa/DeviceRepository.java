package com.xiaohe.mapshow.modules.device.jpa;

import com.xiaohe.mapshow.modules.device.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 *  jpa 接口
 *
 * @author gmq
 * @since 2019-03-28
 */

@Transactional(rollbackFor = Exception.class)
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    /**
     * 根据设备号查询
     *
     * @param deviceId 设备号
     * @return 查询结果
     */
    @Query(value = "select * from device t where t.device_id = :deviceId", nativeQuery = true)
    Device findCustInfoByDevId(@Param("deviceId") String deviceId);

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<Device> findAll(Specification<Device> spec, Pageable pageable);

    /**
     * 按运行状态查询0离线 1在线
     * @param integer 状态
     * @param userId 用户ID
     * @return long
     */
    long countByRunStateEqualsAndBindedUserId(Integer integer, Integer userId);
    /**
     * 按运行状态查询0离线 1在线
     * @param integer 状态
     * @param userIdList 用户IDList
     * @return long
     */
    long countByRunStateEqualsAndBindedUserIdIn(Integer integer, List<Integer> userIdList);

    long countByRunStateEquals(Integer integer);

    /**
     * 根据用户ID集合查询设备编号集合
     * @param userIdList 用户ID
     * @return
     */
    @Query(value = "SELECT device_id FROM device where binded_user_id in ?1",nativeQuery = true)
    List<String> findDeviceIdByUserIdIn(List<Integer> userIdList);

    /**
     * 根据ID查询设备总数
     * @param userId 用户ID
     * @return long
     */
    long countByBindedUserId(Integer userId);

    /**
     * 根据ID集合查询设备总数
     * @return
     */
    long countByBindedUserIdIn(List<Integer> userIdList);

    /**
     * 按条件查询方案
     *
     * @param spec spec
     * @return page
     */
    List<Device> findAll(Specification<Device> spec);

    @Modifying
    @Query(value = "update device set status=0",nativeQuery = true)
    void updateDevOffline();

    @Modifying
    @Query(value = "update device SET status=1 ORDER BY rand() LIMIT ?1",nativeQuery = true)
    void updateDevOnline(int count);

    @Query(value = "select count(id) from device where binded_app_id!=''",nativeQuery = true)
    int findActive();

    @Query(value = "select * from device",nativeQuery =true)
    List<Device> getAll();
}