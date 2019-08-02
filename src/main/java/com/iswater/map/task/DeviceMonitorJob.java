package com.iswater.map.task;



import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.iswater.common.utils.CommonUtil;
import com.iswater.common.utils.DateUtil;
import com.iswater.common.utils.MailUtil;
import com.iswater.map.pojos.History;
import com.iswater.map.service.HistoryService;



//设备监测
public class DeviceMonitorJob {
	
	@Autowired
	private HistoryService historyService;
	
	
	private void execute() {
		Date currentDate = new Date();
		boolean isStop = false;
		String sendTo[] = { "huzhijie@he-live.com", "youchendong@he-live.com" };// 收件人
		String deviceId = "X2-11-161050209";
		
		History latest = historyService.getLatestWaterQuality(deviceId);
		History oldHistory = historyService.getFirstWaterQuality(deviceId, DateUtil.addHour(currentDate, -2), currentDate);
//		History oldHistory = historyService.getFirstWaterQuality(deviceId, DateUtil.addMinute(currentDate, -2), currentDate);
		
//		System.out.println(String.format("水质数据：%s --- %s", latest, oldHistory));
		
		if (null != latest && null != oldHistory) {
			Date latestDate = latest.getRecordTime();
			long recordInterval = DateUtil.getDateInterval(latestDate, currentDate);
			
			if (recordInterval > 1800) {
				isStop = true;
			}else {
				float newTodayAmount = latest.getTodayAmount();
				float oldTodayAmount = oldHistory.getTodayAmount();
				if (CommonUtil.isNumberEq(oldTodayAmount, newTodayAmount)) {
					isStop = true;
				}
			}
			
		}else{
			isStop = true;
		}
		
		
		try {
			if (isStop) {
				MailUtil.sendMessage("smtp.exmail.qq.com",
						"developer@he-live.com", "XHDeveloper123",
						sendTo, "设备告警",
						String.format("%s出现异常,请留意观察。", deviceId));
			}
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if (isStop) {
//			System.out.println("设备出现异常:"+ DateUtil.formatNow());
//		}
		
		System.out.println(String.format("监测设备中,isStop=%s：%s - %s", isStop, DateUtil.format(currentDate), DateUtil.format(DateUtil.addHour(currentDate, -2))));

	}
}
