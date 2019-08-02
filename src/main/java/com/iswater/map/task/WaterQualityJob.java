package com.iswater.map.task;


import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.iswater.common.bean.SpringContextHolder;
import com.iswater.common.utils.DateUtil;
import com.iswater.map.interfaces.WaterQualityImpl;
import com.iswater.map.pojos.Device;
import com.iswater.map.service.DeviceService;


public class WaterQualityJob {

	private static Logger logger = Logger.getLogger(WaterQualityJob.class);

	public void execute() {
		System.out.println("WaterQualityJob开始执行:" + DateUtil.formatNow());
		try {
			WaterQualityImpl service = SpringContextHolder.getBean("waterQualityImpl");

			DeviceService deviceService = SpringContextHolder.getBean("deviceService");
			
			List<Device> devices = deviceService.getMngingDeviceList();
			
			Date date = new Date();
			
			for (Device tmp:devices){				
				service.queryAndInsertWaterQuality(tmp.getDeviceId(), tmp.getBindedUserId());//从净水服务获取数据
//				System.out.println(String.format("devCode=%s,从净水服务获取数据", tmp.getDeviceId()));
				service.setDevRunSate(date, tmp);//设置设备的运行状态
			}

		} catch (Exception e) {
			logger.error("WaterQualityJob执行异常:", e);
		}
//		System.out.println("WaterQualityJob执行完毕:" + DateUtil.formatNow());
	}
	
	
}
