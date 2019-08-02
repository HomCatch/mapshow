package com.iswater.map.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iswater.common.utils.CommonUtil;
import com.iswater.common.utils.DateUtil;
import com.iswater.common.utils.StringUtil;
import com.iswater.common.vo.HistoryVO;
import com.iswater.common.vo.PageVO;
import com.iswater.map.mapper.DeviceMapper;
import com.iswater.map.mapper.HistoryMapper;
import com.iswater.map.pojos.Device;
import com.iswater.map.pojos.History;
import com.iswater.map.pojos.TdsInfo;

@Service
public class HistoryService {

	@Autowired
	private HistoryMapper historyMapper;
	@Autowired
	private DeviceMapper deviceMapper;
	
	
	public TdsInfo selectAvgTds(String start, String end, Integer userId){
		return historyMapper.selectAvgTds(start, end, userId);
	}
	
	public  PageVO getRecordHistoryList(HistoryVO paramVO) {
		int total = historyMapper.selectPageCount(paramVO);
		List<History> list = historyMapper.selectPage(paramVO);
		Integer pageCurrent = paramVO.getPageCurrent();
		int pagesize = paramVO.getPagesize();
		int pageCount = 1;
		if (total % pagesize == 0) {
			pageCount = total / pagesize;
		} else {
			pageCount = total / pagesize + 1;
		}
		return new PageVO(pageCurrent, total, pageCount, list);
	}
	
	public boolean isRecordExist(String deviceId, String date){
		return historyMapper.isRecordExist(deviceId, date);
	}

	public List<History> getHistoryList() {
		return historyMapper.selectAll();
	}

	public List<History> getHistoryTop20List(String deviceId, Integer count) {
		return historyMapper.select20ByDeviceId(deviceId, count);
	}
	
	public List<History> selectHistorys(String deviceCode, Date startDate, Date enDate) {
		return historyMapper.selectHistorysByDateInterVal(deviceCode, startDate, enDate);
	}	
	
	public List<History> getLastWaterQuality(Integer binded_user_id){
		return historyMapper.getLastWaterQuality(binded_user_id);
	}
	
	public History getOpointWaterQuality(Integer userId, String startTm, String endTm) {
		return historyMapper.getOpointWaterQuality(userId, startTm, endTm);
	}
	
	public List<History> getHistoryByDeviceId(String deviceId) {
		return historyMapper.selectByDeviceId(deviceId);
	}

	public List<History> getHistoryList(Integer regionId) {
		return historyMapper.selectByRegionId(regionId);
	}

	/**
	 * 新增设备历史记录
	 * @param device
	 */
	public void addHistory(Device device) {
		History history = new History();
		history.setAmount(device.getAmount());
		history.setColor(device.getColor());
		history.setDeviceDesc(device.getDeviceDesc());
		history.setDeviceId(device.getDeviceId());
		history.setDeviceName(device.getDeviceName());
		history.setDeviceX(device.getDeviceX());
		history.setDeviceY(device.getDeviceY());
		history.setPh(device.getPh());
		history.setPurifyColor(device.getPurifyColor());
		history.setPurifyPh(device.getPurifyPh());
		history.setPurifyTbdt(device.getPurifyTbdt());
		history.setPurifyTds(device.getPurifyTds());
		history.setPurifyTrt(device.getPurifyTrt());
		history.setPurifyAmount(device.getPurifyAmount());
		history.setRecordTime(device.getUpdateTime());
		history.setlastDate(device.getlastDate());
		history.setRegionId(device.getRegionId());
		history.setTbdt(device.getTbdt());
		history.setTds(device.getTds());
		history.setTrt(device.getTrt());
		history.setBinded_user_id(device.getBindedUserId());
		history.setTodayAmount(device.getUsageInOnDay());
		history.setWeekAmount(device.getUsageInWeek());
		history.setMonthAmount(device.getUsageInMonth());
		historyMapper.insertSelective(history);
	}

	public void updateHistory(Integer regionId, Integer min, Integer max) {
		List<History> histories = getHistoryList(regionId);
		for (History history : histories) {
			Integer tds = CommonUtil.getRandomIntValue(min, max);
			Integer purifyTds = CommonUtil.getRandomIntValue(1, 5);
			history.setTds(tds);
			history.setPurifyTds(purifyTds);
			historyMapper.updateByPrimaryKeySelective(history);
		}
	}

	public void addHistory(String regionName) {
		List<Device> devices = null;
		if (StringUtil.isBlank(regionName)) {
			devices = deviceMapper.selectAll();
		} else {
			devices = deviceMapper.selectByRegionName(regionName);
		}
		if (StringUtil.isNotEmpty(devices)) {
			for (Device info : devices) {
				String deviceId = info.getDeviceId();
				List<History> histories = historyMapper.selectByDeviceId(deviceId);
				if (!StringUtil.isNotEmpty(histories)) {
					for (int i = 1; i <= 8; i++) {
						History device = new History();
						device.setDeviceId(info.getDeviceId());
						device.setDeviceName(info.getDeviceName());
						device.setDeviceDesc(info.getDeviceDesc());
						device.setDeviceX(info.getDeviceX());
						device.setDeviceY(info.getDeviceY());
						device.setRegionId(info.getRegionId());

						// 净化前比净化后值要小
						Integer tds = CommonUtil.getRandomIntValue(100, 190);
						Integer purifyTds = CommonUtil.getRandomIntValue(1, 5);
						device.setTds(tds);
						device.setPurifyTds(purifyTds);

						Float color = CommonUtil.getRandomValue(1f, 5f);
						Float purifyColor = CommonUtil.getRandomValue(0.3f, 0.5f);
						device.setColor(color);
						device.setPurifyColor(purifyColor);

						Integer c_trt = 28;
						Integer trt = CommonUtil.getRandomIntValue(c_trt - (c_trt * 10 / 100), c_trt);
						Integer purifyTrt = CommonUtil.getRandomIntValue(c_trt, c_trt + (c_trt * 10 / 100));
						device.setTrt(Float.parseFloat(trt.toString()));
						device.setPurifyTrt(Float.parseFloat(purifyTrt.toString()));

						Float tbdt = CommonUtil.getRandomValue(6.3f, 8f);
						Float purifyTbdt = CommonUtil.getRandomValue(0.8f, 1.5f);
						device.setTbdt(tbdt);
						device.setPurifyTbdt(purifyTbdt);

						device.setAmount(Float.valueOf(120 - (i * 2)));

						device.setPh(0f);
						device.setPurifyPh(0f);

						Date upHours = DateUtil.getHourUp(i);
						System.out.println(upHours);
						device.setRecordTime(upHours);
						historyMapper.insertSelective(device);
					}
				}
			}
		}

	}
	
	public float getAmount(String deviceCode, Date startDate, Date currentDate) {
		
		List<History> histories = selectHistorys(deviceCode, startDate, currentDate);
		
		if (null != histories && histories.size() > 1) {
			History first = histories.get(0);
			History last = histories.get(histories.size()-1);
			float amount = last.getPurifyAmountTotal() - first.getPurifyAmountTotal();
			
			if (amount < 0) {
				amount = 0.0f;
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
			System.out.println(String.format("流量(%s - %s)：%s", dateFormat.format(first.getRecordTime()), dateFormat.format(last.getRecordTime()), amount));
			return amount;
		}else {
			return 0.0f;
		}
		
	}
	
	public History getFirstWaterQuality(String deviceCode, Date startDate, Date enDate){
		History history = historyMapper.selectFirstWaterQuality(deviceCode, startDate, enDate);
		return history;
	}
	
	public History getLatestWaterQuality(String deviceCode, Date startDate, Date enDate){
		History history = historyMapper.selectLatestWaterQuality(deviceCode, startDate, enDate);
		return history;
	}
	
	public History getLatestWaterQuality(String deviceCode){
		History history = historyMapper.selectLatestByDeviceId(deviceCode);
		return history;
	}
	
	public float getWater(String devCode, Date beginDate, Date endDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		History first  = getFirstWaterQuality(devCode, beginDate, endDate);
		History last  = getLatestWaterQuality(devCode, beginDate, endDate);
		float outWater = 0f;
		
		if (null != first && null != last) {
			outWater = last.getPurifyAmountTotal() - first.getPurifyAmountTotal();
			System.out.println("deviceId="+last.getDeviceId());
			System.out.println(String.format("recordTimeFirst=%s,recordTimeLast=%s", dateFormat.format(first.getRecordTime()), dateFormat.format(last.getRecordTime())));
			System.out.println(String.format("outWaterFirst=%s,outWaterLast=%s", first.getPurifyAmountTotal(), last.getPurifyAmountTotal()));
	    	
			if (outWater < 0) {
				outWater = 0.0f;
			}
			
			
			System.out.println(String.format("流量(%s - %s)：%s", dateFormat.format(first.getRecordTime()), dateFormat.format(last.getRecordTime()), outWater));
			
		}
    	
		return outWater;
		
	}
	
	
	/*public History getFirstWaterQuality(String deviceCode, Date startDate, Date enDate){
		History history = historyMapper.selectFirstWaterQuality(deviceCode, startDate, enDate);
		return history;
	}
	
	public History getLatestWaterQuality(String deviceCode, Date startDate, Date enDate){
		History history = historyMapper.selectLatestWaterQuality(deviceCode, startDate, enDate);
		return history;
	}*/
}











