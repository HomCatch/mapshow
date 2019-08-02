package com.xiaohe.mapshow.modules.device.service;

import com.xiaohe.mapshow.modules.history.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.device.entity.Device;
import com.xiaohe.mapshow.modules.device.entity.QueryDevice;
import com.xiaohe.mapshow.modules.device.jpa.DeviceRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Device接口
 * </p>
 *
 * @author gmq
 * @since 2019-03-28
 */

public interface DeviceService extends IBaseServiceTwo<Device, Integer> {


	Device findCustInfoByDevId(String devId);

	/**
	 * 按条件查询
	 *
	 * @param page     页数
	 * @param pageSize 数量
	 * @param
	 * @return Page
	 */
	Page<Device> findAll(int page, int pageSize, QueryDevice queryDevice);

	/**
	 * 根据Id查询list
	 *
	 * @return
	 */
	List<Device> findAllById(List<Integer> ids);

	/**
	 * 按运行状态查询0离线 1在线
	 *
	 * @param integer 状态
	 * @return long
	 */
	long countByRunStateEquals(Integer integer, Integer userId);

	/**
	 * 根据用户ID集合查询设备编号集合
	 *
	 * @param userIdList 用户ID集合
	 * @return
	 */
	List<String> findDeviceIdByUserIdIn(List<Integer> userIdList);

	/**
	 * 根据ID查询设备总数
	 *
	 * @param userId 用户ID
	 * @return long
	 */
	long countByBindedUserId(Integer userId);

	/**
	 * 根据ID集合查询设备总数
	 *
	 * @return
	 */
	long countByBindedUserIdIn(List<Integer> userIdList);

	/**
	 * 按运行状态查询0离线 1在线
	 *
	 * @param integer    状态
	 * @param userIdList 用户IDList
	 * @return long
	 */
	long countByRunStateEqualsAndBindedUserIdIn(Integer integer, List<Integer> userIdList);

	/**
	 * 通过区域查询设备
	 *
	 * @param province 要查询的省份
	 * @param city     要查询的城市
	 * @return 查询结果
	 */
	List<Device> findDeviceListByArea(String province, String city);


	void updateDevOffline(String dbName);

	void updateDevOnline(int count,String dbName);

	int findActive(String dbName);

	void saveDev(Device device,String dbName);

	long getCount(String dbName);

	List<Device>  getAll();
}


