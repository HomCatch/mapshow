package com.iswater.map.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;



import org.apache.ibatis.annotations.Param;

import com.iswater.common.vo.DeviceMngVO;
import com.iswater.map.pojos.Device;

public interface DeviceMapper {
	
	int deleteTop100ByRegionId(Integer regionId);
	
	int deleteByPrimaryKey(Integer id);

	int insert(Device record);

	int insertSelective(Device record);
	
	int insertShortly(Device record);

	Device selectByPrimaryKey(Integer id);

	public List<Device> selectAll();
	
	public List<Device> selectAllMng();

	public List<Device> selectByRegionId(Integer regionId);
	
	public List<Device> selectMngDeviceByRegionId(@Param("region_id")Integer region_id, @Param("size")Integer size);
	
	public List<Device> selectByRegionAndUser(@Param("region_id")Integer region_id, @Param("binded_user_id")Integer binded_user_id, @Param("status") Integer status);
	
	public List<Device> selectByUserAndStatus(@Param("binded_user_id")Integer binded_user_id, @Param("status") Integer status);

	public List<Device> selectByUserAndRunStateAndStatus(@Param("binded_user_id")Integer binded_user_id, @Param("status") Integer status, @Param("run_state") Integer runState);
	
	public List<Device> selectByUser(@Param("binded_user_id")Integer binded_user_id);

	public List<Device> selectByUserAndRunState(@Param("binded_user_id")Integer binded_user_id, @Param("run_state") Integer runState);
	
	public List<Device> selectFilterReplace(@Param("binded_user_id")Integer binded_user_id);
	
	public List<Device> selectByRegionName(String regionName);

	public Integer selectCountByRegionId(Integer regionId);

	public Device selectSumByRegionId(HashMap<String, Object> params);
	
	public List<Device> selectTop100ByRegionId(Integer regionId);
	
	public List<Device> selectDeviceByPoint(HashMap<String, Object> params);
	
	public Device selectSumByPoint(HashMap<String, Object> params);
	
	public Device selectByDeviceId(String deviceId);
	
	public Device selectById(Integer id);

	public Device selectByDeviceKey(Integer id);
	
	int updateByPrimaryKeySelective(Device record);

	int updateByPrimaryKey(Device record);
	
	public void updateRunState(Device device);
	
	Integer selectPageCount(DeviceMngVO paramVO);
	List<Device> selectPage(DeviceMngVO paramVO);
	
	Integer selectBreakDevPageCount(DeviceMngVO paramVO);
	List<Device> selectBreakDevPage(DeviceMngVO paramVO);
	
	Integer selectFilterDevPageCount(DeviceMngVO paramVO);
	List<Device> selectFilterDevPage(DeviceMngVO paramVO);
	
	void updateDeviceName(Device device);
	
	void updateDeviceMng(Device device);
	
	void updateDeviceNameInHistory(Device device);
	
	void updateDeviceMngInHistory(HashMap<String, Object> params);
	
	
	
}







