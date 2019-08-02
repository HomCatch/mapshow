package com.iswater.map.interfaces;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.iswater.common.utils.DateUtil;
import com.iswater.common.utils.HttpUtils;
import com.iswater.map.pojos.Device;
import com.iswater.map.pojos.History;
import com.iswater.map.service.DeviceService;
import com.iswater.map.service.HistoryService;

@Service
public class WaterQualityImpl {

	private static Logger logger = Logger.getLogger(WaterQualityImpl.class);

//	public static final String URL = "http://112.95.228.71:6080/xiaohe/home/";
	public static final String URL = "http://jsfw.xhjsq.com/api/webapi/waterQuality/";

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private HistoryService historyService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 取水质参数
	 * 
	 * @param number
	 * @param type
	 * @param begindate
	 * @param enddate
	 * @return
	 */
	public JSONObject queryWaterQuality(String number, String type, String begindate, String enddate) {
		Map<String, String> params = Maps.newHashMap();
		params.put("number", number);
		params.put("type", type);
		if (!type.equals("1")) {
			params.put("begindate", begindate);
			params.put("enddate", enddate);
		}
		String url = WaterQualityImpl.URL + "AppGetQuality";
		String result = HttpUtils.URLPost(url, params, HttpUtils.UTF8);
		System.out.println("getQuality_result="+result);
		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}

	/**
	 * 取总水量
	 * 
	 * @param number
	 * @return
	 */
	public JSONObject queryWaterAmount(String number, String startTm, String endTm) {
		
		/*
		Map<String, String> params = Maps.newHashMap();
		params.put("number", number);
		String url = WaterQualityImpl.URL + "AppGetWaterEx";
		String result = HttpUtils.URLGet(url, params, HttpUtils.UTF8);
		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
		*/
		
		
		
		Map<String, String> params = Maps.newHashMap();
		params.put("number", number);
		params.put("begindate", startTm);
		params.put("enddate", endTm);
	
		String url = WaterQualityImpl.URL + "AppGetWater";
		String result = HttpUtils.URLPost(url, params, HttpUtils.UTF8);
		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
		
		
	}

	/**
	 * 处理真实设备数据
	 * 
	 * @param number
	 * @throws ParseException
	 */
	public void queryAndInsertWaterQuality(String number, int bindedUserId) throws ParseException {
		// 取最新水质参数
		JSONObject jsonObject = queryWaterQuality(number, "1", null, null);
			
		Integer result = jsonObject.getInt("result");
		String message = jsonObject.getString("message");
		Integer intds = null;
		Integer outtds = null;
		Float intemp = null;
		Float outtemp = null;
		Float inshades = null;
		Float outshades = null;
		Float inturbidity = null;
		Float outturbidity = null;
		Float inwater = 0f;
		Float outwater = 0f;
		Long longdate;
		Float usageInOnDay = null;
		Float usageInWeek = null;
		Float usageInMonth = null; 	
		Date date = null;
		String sdate = null;
		String lastTime ="";
		if (result == 0) {
			logger.info("设备号<" + number + ">查询水质参数成功");
			JSONArray array = jsonObject.getJSONArray("quality");
			if (array != null && array.size() > 0) {
				JSONObject object = array.getJSONObject(0);
				
				History last = historyService.getLatestWaterQuality(number);
				if(last!=null && last.getlastDate()!=null){	
					lastTime=last.getlastDate();
					lastTime=lastTime.substring(0, lastTime.length()-2);
					System.out.println("lastTime:"+lastTime);			
				}
				 
				intds = object.getInt("intds");
				outtds = object.getInt("outtds");
				intemp = Float.valueOf(object.getString("intemp"));
				outtemp = Float.valueOf(object.getString("outtemp"));
				inshades = Float.valueOf(object.getString("inshades"));
				outshades = Float.valueOf(object.getString("outshades"));
				inturbidity = Float.valueOf(object.getString("inturbidity"));
				outturbidity = Float.valueOf(object.getString("outturbidity"));
				//date = object.getString("date");
				longdate = Long.valueOf(object.getString("longdate"));
				
			
				//sdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(longdate*1000));
				sdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(longdate)); 
				
				
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  			 
			    try {  
			           
			    	date = format.parse(sdate);   
			    } catch (ParseException e) {  
			            e.printStackTrace();  
			    }  
			     
				Device device = deviceService.getDeviceByDeviceId(number);
				if (device != null) {
					
					
					//查询水量
				
					if (device.getHasAmountProbe() != 0){ //拥有水质探头
												
						//水量
						Calendar calendar = Calendar.getInstance();  
						String   now  = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());  
						 
					 
										
						 //当天
						Calendar calToday = Calendar.getInstance(); 
						calToday.set(Calendar.HOUR_OF_DAY, 0); 
						calToday.set(Calendar.SECOND, 0); 
						calToday.set(Calendar.MINUTE, 0); 
						calToday.set(Calendar.MILLISECOND, 0); 
					
						 
						 //一周内
						Calendar calWeekStart = Calendar.getInstance(); 
						calWeekStart.set(calWeekStart.get(Calendar.YEAR),calWeekStart.get(Calendar.MONDAY), calWeekStart.get(Calendar.DAY_OF_MONTH), 0, 0,0); 
						calWeekStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 						
			       
					
						 
						 //一月内				
					    Calendar calMonthStart = Calendar.getInstance();  
					    calMonthStart.add(Calendar.MONTH, 0);  
					    calMonthStart.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天  
					        //将小时至0  
					    calMonthStart.set(Calendar.HOUR_OF_DAY, 0);  
					        //将分钟至0  
					    calMonthStart.set(Calendar.MINUTE, 0);  
					        //将秒至0  
					    calMonthStart.set(Calendar.SECOND,0);  
					        //将毫秒至0  
					    calMonthStart.set(Calendar.MILLISECOND, 0);
					    
					    System.out.println("calMonthStart="+calMonthStart);
					        
					 						
						String monthStart = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calMonthStart.getTime()); 
						String weekStart = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calWeekStart.getTime()); 
						String strToday =  (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calToday.getTime()); 
						
						JSONObject amountObj = queryWaterAmount(device.getDeviceId(), strToday, now);
						
						
						result = amountObj.getInt("result");
						if (result != null && result == 0){
							usageInOnDay = Float.valueOf(amountObj.getString("outwater"));
						}
						
						
						amountObj = queryWaterAmount(device.getDeviceId(), weekStart, now);
						result = amountObj.getInt("result");
						if (result != null && result == 0){
							usageInWeek = Float.valueOf(amountObj.getString("outwater"));
						}						
					
						amountObj = queryWaterAmount(device.getDeviceId(), monthStart, now);
						result = amountObj.getInt("result");
						if (result != null && result == 0){
							usageInMonth = Float.valueOf(amountObj.getString("outwater"));
						}												
						
					}
					
					
					if (device.getSpecial() != 0){ //是特殊设备，做随机值处理
						 if(sdate.equals(lastTime)) {
							 System.out.println("时间相同不插入");
						 }
						 else {
							 
						 
						 int max= device.getGeneralInTds();
					     int min= max - 5;
					     Random random = new Random();

					     int s = random.nextInt(max)%(max-min+1) + min;
					     intds  = s;
					     
					     max = 6;
					     min = 2;
					     
					     s = random.nextInt(max)%(max-min+1) + min;
					     
					     outtds = s;
					     
					     int ranOutTurbidity = (int)(Math.random()*21) + 40;
					     int ranOutShades = (int)(Math.random()*21) + 40;
					     outturbidity = ranOutTurbidity / 100.0f;
					     outshades = ranOutShades / 100.0f;
					     System.out.println(String.format("outturbidity=%s,outshades=%s", outturbidity, outshades));
					     
					     date = new Date();
					     
					     inshades = device.getGeneralInSd();
					     inturbidity = device.getGeneralInZd();
					     
						 device.setTds(intds);
						 device.setPurifyTds(outtds);
						 device.setTrt(intemp);
						 device.setPurifyTrt(outtemp);
						 device.setColor(inshades);
						 device.setPurifyColor(outshades);
						 device.setTbdt(inturbidity);
						 device.setPurifyTbdt(outturbidity);
						 device.setUpdateTime(date);						 
						 device.setlastDate(sdate);//lastTime
						 device.setAmount(inwater);
						 device.setPurifyAmount(outwater);
						 device.setBindedUserId(bindedUserId);		
						 device.setUsageInOnDay(usageInOnDay);
						 device.setUsageInWeek(usageInWeek);
						 device.setUsageInMonth(usageInMonth);
						 
						 device.setUsageInOnDay(usageInOnDay);
						 device.setUsageInWeek(usageInWeek);
						 device.setUsageInMonth(usageInMonth);
						 

						
						
//						 if(lastTm.equals("")){
//							 System.out.println("时间相同不插入");
//						 }
//						 else {
							 
							 historyService.addHistory(device);
							 deviceService.updateDevice(device);
							 System.out.println("是特殊设备，做随机值处理:"+device.getDeviceId());
						 //}
							 }	
						 }

					else{
						
						if (!historyService.isRecordExist(number, sdate)){			

							//	setDeviceInfo(intds, outtds, intemp, outtemp, inshades, outshades, inturbidity, outturbidity, date, inwater, outwater, device, bindedUserId);
								device.setTds(intds);
								device.setPurifyTds(outtds);
								device.setTrt(intemp);
								device.setPurifyTrt(outtemp);
								device.setColor(inshades);
								device.setPurifyColor(outshades);
								device.setTbdt(inturbidity);
								device.setPurifyTbdt(outturbidity);
								device.setUpdateTime(date);
								device.setlastDate(sdate);//lastTime
								device.setAmount(inwater);
								device.setPurifyAmount(outwater);
								device.setBindedUserId(bindedUserId);
								System.out.println("device.setBindedUserId="+bindedUserId);
								
								device.setUsageInOnDay(usageInOnDay);
								device.setUsageInWeek(usageInWeek);
								device.setUsageInMonth(usageInMonth);
								device.setRecordInterval(120);
																	
								historyService.addHistory(device);
								deviceService.updateDevice(device);
								
							}
						
						
						/*
						if (number.equals("X2-12-170328074")){
							 //对于tds 随机变化
							 int max=45;
						     int min=50;
						     Random random = new Random();

						     int s = random.nextInt(max)%(max-min+1) + min;
						     intds  = s;
						     
						     max = 6;
						     min = 2;
						     
						     s = random.nextInt(max)%(max-min+1) + min;
						     
						     outtds = s;
						     date = new Date();
							 setDeviceInfo(intds, outtds, intemp, outtemp, inshades, outshades, inturbidity, outturbidity, date, inwater, outwater, device, bindedUserId);
							 historyService.addHistory(device);
							 deviceService.updateDevice(device);
						     
						}
						*/
					}
				}		
			//	} 			     		                                              
				
	
			}
		} else if (result == 1) {
			logger.error("设备号<" + number + ">查询水质参数失败:" + message);
		} else if (result == 2) {
			logger.error("设备号<" + number + ">查询水质参数失败:" + message);
		}

	
	}


	
	public static JSONObject queryWaterAmount2(String number, String startTm, String endTm) {
		
		/*
		Map<String, String> params = Maps.newHashMap();
		params.put("number", number);
		String url = WaterQualityImpl.URL + "AppGetWaterEx";
		String result = HttpUtils.URLGet(url, params, HttpUtils.UTF8);
		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
		*/
		
		
		
		Map<String, String> params = Maps.newHashMap();
		params.put("number", number);
		params.put("begindate", startTm);
		params.put("enddate", endTm);
	
		String url = WaterQualityImpl.URL + "AppGetWaterEx2";
		String result = HttpUtils.URLGet(url, params, HttpUtils.UTF8);
		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
		
		
	}
	
	public static void main(String[] args){
		 /*
		 Calendar calendar = Calendar.getInstance();  
		 String   str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());  
		 
		 System.out.println(str);
		 
		 calendar.add(Calendar.DAY_OF_MONTH, -5);
		
		 //昨天
		 str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());  
		 System.out.println(str);
		 
		 //一周内
		 calendar.add(Calendar.DAY_OF_MONTH, -7);
		 str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime()); 
		 
		 //一月内
		 calendar.add(Calendar.DAY_OF_MONTH, -30);
		 str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime()); 
		 
		 System.out.println(str);
		 */
		 Calendar calendar = Calendar.getInstance();  
		 String   now  = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());  
		 
	 
		 calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		 //昨天
		 String yesterdayStart = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());  
	
		 
		 JSONObject obj = queryWaterAmount2("X2-11-161051208", yesterdayStart, now);
		 
		 //一周内
		 calendar.add(Calendar.DAY_OF_MONTH, -7);
		 String weekStart = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime()); 
		 
		 obj = queryWaterAmount2("X2-11-161051208", weekStart, now);
		 
		 //一月内
		 calendar.add(Calendar.DAY_OF_MONTH, -30);
		 String monthStart = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime()); 	
		 
		 obj = queryWaterAmount2("X2-11-161051208", monthStart, now);
		 obj = queryWaterAmount2("X2-11-161051208", monthStart, now);
		
	}
	
	
	public void setDevRunSate(Date currentDate, Device device) {
		Date latestTime =  device.getUpdateTime();
		long interval = DateUtil.getDateInterval(latestTime, currentDate);
		if (null == device.getRecordInterval()) {
			device.setRunState(0);
		}else if (interval > device.getRecordInterval()) {
			device.setRunState(1);
		}else {
			device.setRunState(0);
		}
		
		
		System.out.println(String.format("DevId=%s,latestTime=%s,currentDate=%s,interval=%s", device.getDeviceId(),sdf.format(latestTime), sdf.format(currentDate), interval));
		deviceService.updateDevRunSate(device);
	}
}
