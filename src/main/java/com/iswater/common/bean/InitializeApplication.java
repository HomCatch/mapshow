package com.iswater.common.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.iswater.map.service.DeviceService;
import com.iswater.map.service.HistoryService;
import com.iswater.map.service.RegionService;

@Component
public class InitializeApplication implements ApplicationContextAware {

	@Autowired
	private RegionService regionService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private HistoryService historyService;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContext context = SpringContextHolder.getContext();
		if (context != null) {
			//setMongoDBCache();
		}
	}

//	private void setMongoDBCache() {
//		PojoUtil pojoUtil = PojoUtil.getInstance();
//		// 存储Region信息
//		String Region = pojoUtil.getClassName(new Region());
//		long count = mongoService.findAllCount(Region);
//		if (count > 0) {
//			mongoService.remove(Region);
//		}
//		List<Region> regions = regionService.getRegionList();
//		mongoService.insertRegion(Region, regions);
//
//		// 存储设备信息
//		String Device = pojoUtil.getClassName(new Device());
//		count = mongoService.findAllCount(Device);
//		if (count > 0) {
//			mongoService.remove(Device);
//		}
//		List<Device> devices = deviceService.getDeviceList();
//		mongoService.insertDevice(Device, devices);
//	}

}
