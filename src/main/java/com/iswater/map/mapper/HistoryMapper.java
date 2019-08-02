package com.iswater.map.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iswater.common.vo.HistoryVO;
import com.iswater.map.pojos.History;
import com.iswater.map.pojos.TdsInfo;

public interface HistoryMapper {
	int deleteByPrimaryKey(Integer historyId);
	
	int deleteByDeviceId(String deviceId);

	int insert(History record);

	int insertSelective(History record);

	History selectByPrimaryKey(Integer historyId);

	public List<History> selectAll();
	
	public List<History> selectByRegionId(Integer regionId);
	
	public List<History> selectByDeviceId(String deviceId);
	
	public List<History> select20ByDeviceId(@Param("device_id")String device_id, @Param("count")Integer count);
	
	public List<History> selectHistorysByDateInterVal(String deviceCode, Date startDate, Date enDate);
	
	public History selectFirstWaterQuality(String deviceCode, Date startDate, Date enDate);
	
	public History selectLatestWaterQuality(String deviceCode, Date startDate, Date enDate);
	
	public History selectLatestByDeviceId(String deviceCode);

	public History getOpointWaterQuality(@Param("binded_user_id")Integer binded_user_id, @Param("start")String start, @Param("end")String end);
	
	public List<History> getLastWaterQuality(Integer binded_user_id);
	
	int updateByPrimaryKeySelective(History record);

	int updateByPrimaryKey(History record);
	
	Integer selectPageCount(HistoryVO history);
	List<History> selectPage(HistoryVO paramVO);
	
	boolean  isRecordExist(@Param("device_id")String device_id, @Param("record_time")String record_time);
	
	public TdsInfo selectAvgTds(@Param("start")String start, @Param("end")String end, @Param("binded_user_id")Integer binded_user_id);

}