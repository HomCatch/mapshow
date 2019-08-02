package com.iswater.map.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.iswater.common.utils.DateUtil;
import com.iswater.common.utils.StringUtil;
import com.iswater.common.vo.DeviceMngVO;
import com.iswater.common.vo.PageVO;
import com.iswater.common.vo.ResultVO;
import com.iswater.map.pojos.Device;
import com.iswater.map.pojos.History;
import com.iswater.map.pojos.LatestRunInfo;
import com.iswater.map.pojos.DashBoardStartUpPara;
import com.iswater.map.pojos.TdsInfo;
import com.iswater.map.pojos.User;
import com.iswater.map.service.DeviceService;
import com.iswater.map.service.DictService;
import com.iswater.map.service.HistoryService;
import com.iswater.map.service.RegionService;

@Controller
public class ParaController {

	@Autowired
	private RegionService regionService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private DictService dictService;
	
	
	@Autowired
	private DeviceService service;
	

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView waterQuality(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		//仪表盘首页启动参数
		List<Device> devices = deviceService.getDeviceList(user.getRegion_id(), user.getId(), 0); //正在管理中的设备
		Device target = devices.get(0);
		
		TreeMap<String, BigDecimal> day7samouts =   new TreeMap<String, BigDecimal>(new Comparator<String>() {
			public int compare(String obj1, String obj2) {
				return obj1.compareTo(obj2);
			}
		});
		
		
		Date now = new Date();
		
		List<BigDecimal> tdsInValus = new ArrayList<BigDecimal>();
		
		for (int i = 7; i > 0; i--){
			
			Date tmp = DateUtil.addDay(now, -i);
		
			String date = DateUtil.format(tmp, "MM-dd");
			
			String start = DateUtil.format(tmp, "yyyy-MM-dd ") + "00:00:00";
			String end = DateUtil.format(tmp, "yyyy-MM-dd ") + "23:59:59";
			
//			TdsInfo tds = historyService.selectAvgTds(start, end, user.getId());
			TdsInfo tds = new TdsInfo();
			tds.setAvg_in_tds(target.getGeneralInTds()*1.0 + (int)(Math.random()*5));
			tds.setAvg_out_tds(6.0 + (int)(Math.random()*4));
			
			if (tds == null){
				day7samouts.put(date, new BigDecimal(0.0));
				tdsInValus.add(new BigDecimal(0.0));
				
			//	tdsInValus.add(new BigDecimal(50.0));
			}else{
				
				if (tds.getAvg_out_tds() != null){
					day7samouts.put(date, new BigDecimal(tds.getAvg_out_tds()));
				}else{
					day7samouts.put(date, new BigDecimal(0.0));
				}
				
				if (tds.getAvg_in_tds() != null){
					tdsInValus.add(new BigDecimal(tds.getAvg_in_tds()));
				//	tdsInValus.add(new BigDecimal(50.0));
				}else{
					tdsInValus.add(new BigDecimal(0.0));
				//	tdsInValus.add(new BigDecimal(50.0));
				}
			}
			
			
		}
		
		
	
		
		
		ModelAndView modelAndView;
		
		if (target.getHasAmountProbe() >0 ){
			modelAndView = new ModelAndView("/onedevdashboard");
		}else{
			modelAndView = new ModelAndView("/dashboard");
		}
		

		JSONArray day7stime = JSONArray.fromObject(day7samouts.keySet());
		JSONArray day7samout = JSONArray.fromObject(day7samouts.values());
		JSONArray day7InTds = JSONArray.fromObject(tdsInValus);
		modelAndView.addObject("day7stime", day7stime);
		modelAndView.addObject("day7samout", day7samout);
		modelAndView.addObject("day7intds", day7InTds);
	
		int interval = user.getIntervals() * 1000; //转换成毫秒 
		
		interval =  interval < 30000 ? 30000: interval; //最小单位是30秒
		
		DashBoardStartUpPara para = new DashBoardStartUpPara();	
		para.setInterval(interval);
		para.setDeviceCount(devices.size());
		para.setEvaluate(90);
		para.setDevceNo(target.getDeviceId());
		
		// 一般只有一个设备

		
		para.setHasAmountProbe(target.getHasAmountProbe() >0 ?true:false);
		
		
		para.setDayAmount(target.getUsageInOnDay());
		para.setWeekAmount(target.getUsageInWeek());
		para.setMonthAmount(target.getUsageInWeek());
		
	
		
		modelAndView.addObject("startup", para);
		modelAndView.addObject("devices", devices);
		
		
		///////////////////////
		
		DeviceMngVO paramVO = new DeviceMngVO();
		

		paramVO.setBinded_user_id(user.getId());
		
		Integer pageCurrent = StringUtil.isBlank(request.getParameter("pageCurrent")) ? 1 : Integer.valueOf(request.getParameter("pageCurrent"));
		paramVO.setPageCurrent(pageCurrent);
		modelAndView.addObject("param", paramVO);

		PageVO pageVO = service.getRecordHistoryList(paramVO);
		modelAndView.addObject("page", pageVO);
		
		
		////////////
		
		return modelAndView;
	}
	
	//appointed
	
	/**
	 *  顶部仪表盘动态函数
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/getLatestWaterQuality", method = RequestMethod.GET)
	public JSONObject getLatestWaterQuality(HttpServletRequest request) {
	
	
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			
			
			String deviceId = request.getParameter("deviceId");
			
			
			List<Device> devices = deviceService.getDeviceList(user.getRegion_id(), user.getId(), 0); //正在管理中的设备
		
			boolean realTimeMode = false;
			if (devices.size() == 1) { //只有一个设备，实时模式
				Device target = devices.get(0);
				deviceId = target.getDeviceId();
				realTimeMode = true;
			}
			
			if (deviceId != null && !deviceId.equals("")){ //指定设备，实时模式
				realTimeMode = true;
			}
			
			
			if (realTimeMode){ //实时模式
				
				List<History> histories = historyService.getHistoryTop20List(deviceId, 1);
				
				
						
				History latest = histories.get(0);
				
				Device device = deviceService.getDeviceByDeviceId(deviceId);
			    //日，周， 月流量
				if (null != device && device.getDataSource() == 1) {
					Date currentDate = new Date();
					latest.setTodayAmount(historyService.getWater(latest.getDeviceId(), DateUtil.getDayBegin(), currentDate));
			 		latest.setWeekAmount(historyService.getWater(latest.getDeviceId(), DateUtil.getBeginDayOfWeek(), currentDate));
			 		latest.setMonthAmount(historyService.getWater(latest.getDeviceId(), DateUtil.getBeginDayOfMonth(), currentDate));
				}
				
				
//				if (latest.getMonthAmount() < latest.getWeekAmount()){
//					latest.setMonthAmount(latest.getWeekAmount()*4);
//				}
				
				
				JSONObject result = new JSONObject();
				
				Integer inTds = latest.getTds();
				Integer outTds = latest.getPurifyTds();
				String lastTime = latest.getlastDate();
				lastTime = lastTime.substring(0, lastTime.length()-2);
				Float inSd = latest.getColor();
				Float outSd = latest.getPurifyColor();
				Float inZd = latest.getTbdt();
				Float outZd = latest.getPurifyTbdt();
				
				LatestRunInfo runInfo = new LatestRunInfo();
				runInfo.setDeviceId(deviceId);
				runInfo.setLatest_device_count(1);
				runInfo.setLatest_in_tds(inTds);
				runInfo.setLatest_out_tds(outTds);
				
				runInfo.setLatest_in_sd(inSd);
				runInfo.setLatest_out_sd(outSd);
				
				runInfo.setLatest_in_zd(inZd);
				runInfo.setLatest_out_zd(outZd);
				
				runInfo.setLatest_tds_tm(new Date());
				
				runInfo.setLast_time(lastTime);
				
				runInfo.setDevice_size(devices.size());
				
				runInfo.setToday_amount(latest.getTodayAmount());
				
				runInfo.setWeek_amount(latest.getWeekAmount());
				
				runInfo.setMonth_amount(latest.getMonthAmount());
				
				//质量评判
				if (inTds >= 0 && inTds <= 50){
					runInfo.setIn_tds_quality("优");
				}else if (inTds > 50 && inTds <= 100){
					runInfo.setIn_tds_quality("良");
				}else if (inTds > 100 && inTds <= 300) {
					runInfo.setIn_tds_quality("中");
				}else{
					runInfo.setIn_tds_quality("差");
				}
				
				
				if (outTds >= 0 && outTds <= 50){
					runInfo.setOut_tds_quality("优");
				}else if (outTds > 50 && outTds <= 100){
					runInfo.setOut_tds_quality("良");
				}else if (outTds > 100 && outTds <= 300){
					runInfo.setOut_tds_quality("中");
				}else{
					runInfo.setOut_tds_quality("差");
				}
				
				
				//色度
				if (inSd >= 0 && inSd <= 1.0){
					runInfo.setIn_sd_quality("纯净");
				}else {
					runInfo.setIn_sd_quality("较纯");
				}
				
				if (outZd >= 0 && outZd <= 1.0){
					runInfo.setOut_sd_quality("纯净");
				}else {
					runInfo.setOut_sd_quality("较纯");
				}		
		
				
				//浊度
				if (inZd >= 0 && inZd <= 1.0){
					runInfo.setIn_zd_quality("澄清");
				}else {
					runInfo.setIn_zd_quality("较清");
				}
				
				if (outZd >= 0 && outZd <= 1.0){
					runInfo.setOut_zd_quality("澄清");
				}else {
					runInfo.setOut_zd_quality("较清");
				}					
				
						
				result = JSONObject.fromObject(runInfo);
			
				return result;									
				
			}else{
		
				JSONObject result = new JSONObject();
				if (devices.size() == 0) {
					return result;
				}
				Device target = devices.get(0);
				String devId = target.getDeviceId();
				List<History> histories = historyService.getHistoryTop20List(devId, 1);
				History last = histories.get(0); //最后一条
				
				long currernt = System.currentTimeMillis();
				
				
				// 选取平均值
				
				long interval = user.getIntervals() * 1000;
				
				long sectionEnd = currernt;
				long sectionStart = currernt - interval;
					
				SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				Date dateStart = new Date(sectionStart);
				Date dateEnd = new Date(sectionEnd);
					
				String strStartTm = sdFormatter.format(dateStart);
					
				String strEndTm = sdFormatter.format(dateEnd);
					
				
				History latest = historyService.getOpointWaterQuality(user.getId(), strStartTm, strEndTm);
		
				
				
				
				LatestRunInfo runInfo = new LatestRunInfo();
				runInfo.setDeviceId(last.getDeviceId());
				
				
				if (latest == null){ //如果指定时间为空，则用最后一条赋给当前
					latest = last;
					latest.setRecordTime(new Date());
				}
				
				if (latest != null){
					Integer inTds = latest.getTds();
					Integer outTds = latest.getPurifyTds();
					String lastTime = latest.getlastDate();
					lastTime = lastTime.substring(0, lastTime.length()-2);
					Float inSd = latest.getColor();
					Float outSd = latest.getPurifyColor();
					Float inZd = latest.getTbdt();
					Float outZd = latest.getPurifyTbdt();
					
					
					runInfo.setLatest_device_count(1);
					runInfo.setLatest_in_tds(inTds);
					runInfo.setLatest_out_tds(outTds);
					
					runInfo.setLatest_in_sd(inSd);
					runInfo.setLatest_out_sd(outSd);
					runInfo.setLast_time(lastTime);
					runInfo.setLatest_in_zd(inZd);
					runInfo.setLatest_out_zd(outZd);
					
					runInfo.setLatest_tds_tm(latest.getRecordTime());
					
					//质量评判
					if (inTds >= 0 && inTds <= 50){
						runInfo.setIn_tds_quality("优");
					}else if (inTds > 50 && inTds <= 100){
						runInfo.setIn_tds_quality("良");
					}else if (inTds > 100 && inTds <= 300) {
						runInfo.setIn_tds_quality("中");
					}else{
						runInfo.setIn_tds_quality("差");
					}
					
					
					if (outTds >= 0 && outTds <= 50){
						runInfo.setOut_tds_quality("优");
					}else if (outTds > 50 && outTds <= 100){
						runInfo.setOut_tds_quality("良");
					}else if (outTds > 100 && outTds <= 300){
						runInfo.setOut_tds_quality("中");
					}else{
						runInfo.setOut_tds_quality("差");
					}
					
					
					//色度
					if (inSd >= 0 && inSd <= 1.0){
						runInfo.setIn_sd_quality("纯净");
					}else {
						runInfo.setIn_sd_quality("较纯");
					}
					
					if (outZd >= 0 && outZd <= 1.0){
						runInfo.setOut_sd_quality("纯净");
					}else {
						runInfo.setOut_sd_quality("较纯");
					}		
			
					
					//浊度
					if (inZd >= 0 && inZd <= 1.0){
						runInfo.setIn_zd_quality("澄清");
					}else {
						runInfo.setIn_zd_quality("较清");
					}
					
					if (outZd >= 0 && outZd <= 1.0){
						runInfo.setOut_zd_quality("澄清");
					}else {
						runInfo.setOut_zd_quality("较清");
					}					
					
					runInfo.setDevice_size(devices.size());
					runInfo.setLatest_tds_tm(new Date());
							
					result = JSONObject.fromObject(runInfo);				
				}else{
					runInfo.setLatest_device_count(1);
					runInfo.setLatest_in_tds(10);
					runInfo.setLatest_out_tds(0);
					
					runInfo.setLatest_in_sd(0.0f);
					runInfo.setLatest_out_sd(0.0f);
					
					runInfo.setLatest_in_zd(0.0f);
					runInfo.setLatest_out_zd(0.0f);
					
		
					//质量评判
				
					runInfo.setIn_tds_quality("--");
							
					runInfo.setOut_tds_quality("--");
					
				
					runInfo.setIn_sd_quality("--");
					
					
					runInfo.setOut_sd_quality("--");
								
				
					runInfo.setIn_zd_quality("--");
				
					
					runInfo.setOut_zd_quality("--");
					
					runInfo.setDevice_size(devices.size());
					
					
					runInfo.setLatest_tds_tm(new Date(currernt));
							
					result = JSONObject.fromObject(runInfo);								
				}
				
	
				return result;	
			
		}
		
	}	
	
	@ResponseBody
	@RequestMapping(value = "/getLatestWaterInfo", method = RequestMethod.GET)
	public JSONObject getLatestWaterInfo(HttpServletRequest request) {
	
	
		String deviceId = request.getParameter("deviceId");
		if (StringUtil.isNotBlank(deviceId)) {
			List<History> histories = historyService.getHistoryTop20List(deviceId, 20);
	
			History latest = histories.get(0);
			JSONObject result = new JSONObject();
			
			
			Integer inTds = latest.getTds();
			Integer outTds = latest.getPurifyTds();
			
			Float inSd = latest.getColor();
			Float outSd = latest.getPurifyColor();
			Float inZd = latest.getTbdt();
			Float outZd = latest.getPurifyTbdt();
			
			LatestRunInfo runInfo = new LatestRunInfo();
			runInfo.setLatest_device_count(1);
			runInfo.setLatest_in_tds(inTds);
			runInfo.setLatest_out_tds(outTds);
			
			runInfo.setLatest_in_sd(inSd);
			runInfo.setLatest_out_sd(outSd);
			
			runInfo.setLatest_in_zd(inZd);
			runInfo.setLatest_out_zd(outZd);
			
			runInfo.setLatest_tds_tm(latest.getRecordTime());
			
			//质量评判
			if (inTds >= 0 && inTds <= 50){
				runInfo.setIn_tds_quality("优");
			}else if (inTds > 50 && inTds <= 100){
				runInfo.setIn_tds_quality("中");
			}else {
				runInfo.setIn_tds_quality("差");
			}
			
			
			if (outTds >= 0 && outTds <= 50){
				runInfo.setOut_tds_quality("优");
			}else if (outTds > 50 && outTds <= 100){
				runInfo.setOut_tds_quality("中");
			}else {
				runInfo.setOut_tds_quality("差");
			}
			
			
			//色度
			if (inSd >= 0 && inSd <= 1.0){
				runInfo.setIn_sd_quality("纯净");
			}else {
				runInfo.setIn_sd_quality("较纯");
			}
			
			if (outZd >= 0 && outZd <= 1.0){
				runInfo.setOut_sd_quality("纯净");
			}else {
				runInfo.setOut_sd_quality("较纯");
			}		
	
			
			//浊度
			if (inZd >= 0 && inZd <= 1.0){
				runInfo.setIn_zd_quality("澄清");
			}else {
				runInfo.setIn_zd_quality("较清");
			}
			
			if (outZd >= 0 && outZd <= 1.0){
				runInfo.setOut_zd_quality("澄清");
			}else {
				runInfo.setOut_zd_quality("较清");
			}					
			
					
			result = JSONObject.fromObject(runInfo);
		
			return result;						
		}	
		
		return null;
	}	
	
	/**
	 * 根据设备ID获取折线图数据
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInitialDynamicTds")
	public JSONArray getInitialDynamicTds(HttpServletRequest request,@RequestParam(value="device_id",required = false) String id
) {
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		JSONArray json = new JSONArray();
		
		List<Device> devices = deviceService.getDeviceList(user.getRegion_id(), user.getId(), 0);
		String deviceId = "";
	
		if(id.isEmpty()){
			deviceId = devices.get(0).getDeviceId();
		}
		// 如果只有一个设备，取一个设备的实时值
//		if (devices.size() == 1){
		else{
			deviceId = id;//devices.get(0).getDeviceId()
		}
			
			if (StringUtil.isNotBlank(deviceId)) {
				List<History> histories = historyService.getHistoryTop20List(deviceId, 24);
				Collections.sort(histories, new Comparator<History>() {
					@Override
					public int compare(History o1, History o2) {
						Date o1_val = o1.getRecordTime();
						Date o2_val = o2.getRecordTime();
						return o1_val.compareTo(o2_val);
					}
				});
				for (History history : histories) {
					Float PurifyTrt = history.getPurifyTrt();
					if (PurifyTrt < 0) {
						history.setPurifyTrt(-PurifyTrt);
					}
					history.setUnixTime(history.getRecordTime().getTime());
					String sdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(history.getRecordTime()); 
					String lastDate = history.getlastDate();
//					if(lastDate.equals(null)) {
					if(StringUtil.isBlank(lastDate)) {
						System.out.println(deviceId+"本条记录没有正确时间");
					}
					else {	
						lastDate=lastDate.substring(0, lastDate.length()-2);
						//history.setlastDate(lastDate);					
						System.out.println("sdate:"+sdate);					
						history.setStrTime(lastDate);		
					}			
					
					JSONObject jsonObject = JSONObject.fromObject(history);
					json.add(jsonObject);
				}
			}			
			
		/*}else{
		
		//	List<History> histories = historyService.getHistoryTop20List(deviceId, 20);
			long currernt = System.currentTimeMillis();
			long interval = user.getIntervals() * 1000;
			
			Device target = devices.get(0);
			List<History> histories = historyService.getHistoryTop20List(target.getDeviceId(), 1);
			
			History last = histories.get(0); //最后一条
			
			//获取该用户下最近一次设备上报记录

			for (int i = 19; i >= 0; i--){
				
				long sectionEnd = currernt - i*interval;
				long sectionStart = sectionEnd - 2 * interval; //描点范围扩大
				
				SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				Date dateStart = new Date(sectionStart);
				Date dateEnd = new Date(sectionEnd);
					
				String strStartTm = sdFormatter.format(dateStart);
					
				String strEndTm = sdFormatter.format(dateEnd);		
				
				
				History latest = historyService.getOpointWaterQuality(user.getId(), strStartTm, strEndTm);
				
				if (latest == null){
					latest = last; 
				
				}else{
					last = latest;
				}
				
				if (latest == null){
					latest = new History();
				
				}
				
				latest.setRecordTime(dateEnd);			
				latest.setUnixTime(latest.getRecordTime().getTime());			
				latest.setStrTime(strEndTm);
				
				JSONObject jsonObject = JSONObject.fromObject(latest);
				json.add(jsonObject);
				
			}
		}*/
	
		return json;
	}	
	
	/**
	 * 根据设备ID获取折线图数据
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getlineGraphData")
	public JSONArray getlineGraphData(HttpServletRequest request) {
		JSONArray json = new JSONArray();
		String deviceId = request.getParameter("deviceId");
		if (StringUtil.isNotBlank(deviceId)) {
			List<History> histories = historyService.getHistoryTop20List(deviceId, 20);
			Collections.sort(histories, new Comparator<History>() {
				@Override
				public int compare(History o1, History o2) {
					Date o1_val = o1.getRecordTime();
					Date o2_val = o2.getRecordTime();
					return o1_val.compareTo(o2_val);
				}
			});
			for (History history : histories) {
				Float PurifyTrt = history.getPurifyTrt();
				if (PurifyTrt < 0) {
					history.setPurifyTrt(-PurifyTrt);
				}
				history.setUnixTime(history.getRecordTime().getTime());
				String sdate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(history.getRecordTime()); 
				history.setStrTime(sdate);
				JSONObject jsonObject = JSONObject.fromObject(history);
				json.add(jsonObject);
			}
		}
		return json;
	}
	

	@ResponseBody
	@RequestMapping(value = "/getLatestTDS")
	public String getLatestTDS(HttpServletRequest request){
		
		String deviceId = request.getParameter("deviceId");
		if (StringUtil.isNotBlank(deviceId)) {
			List<History> histories = historyService.getHistoryTop20List(deviceId, 5);
		
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(histories.get(0).getTds().toString()).append(",");
			buffer.append(histories.get(0).getPurifyTds().toString());
		
			return buffer.toString();
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("100").append(",");
		buffer.append("2");
	
		return buffer.toString();
	
	}

	
	
	@ResponseBody
	@RequestMapping(value = "/getLatestWaterQuality2", method = RequestMethod.GET)
	public JSONObject getLatestWaterQuality2(HttpServletRequest request) {
	
	
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		String deviceId = request.getParameter("deviceId");
		System.out.println("deviceId="+deviceId);
		
		
			
		List<History> histories = historyService.getHistoryTop20List(deviceId, 1);
		
		History latest = histories.get(0);
		
		Device device = deviceService.getDeviceByDeviceId(deviceId);
	    //日，周， 月流量
		if (null != device && device.getDataSource() == 1) {
			Date currentDate = new Date();
			latest.setTodayAmount(historyService.getWater(latest.getDeviceId(), DateUtil.getDayBegin(), currentDate));
	 		latest.setWeekAmount(historyService.getWater(latest.getDeviceId(), DateUtil.getBeginDayOfWeek(), currentDate));
	 		latest.setMonthAmount(historyService.getWater(latest.getDeviceId(), DateUtil.getBeginDayOfMonth(), currentDate));
		}
		
 		
		
		JSONObject result = new JSONObject();
		
		Integer inTds = latest.getTds();
		Integer outTds = latest.getPurifyTds();
		String lastTime = latest.getlastDate();
		lastTime = lastTime.substring(0, lastTime.length()-2);
		Float inSd = latest.getColor();
		Float outSd = latest.getPurifyColor();
		Float inZd = latest.getTbdt();
		Float outZd = latest.getPurifyTbdt();
		
		LatestRunInfo runInfo = new LatestRunInfo();
		runInfo.setDeviceId(deviceId);
		runInfo.setLatest_device_count(1);
		runInfo.setLatest_in_tds(inTds);
		runInfo.setLatest_out_tds(outTds);
		
		runInfo.setLatest_in_sd(inSd);
		runInfo.setLatest_out_sd(outSd);
		
		runInfo.setLatest_in_zd(inZd);
		runInfo.setLatest_out_zd(outZd);
		
		runInfo.setLatest_tds_tm(new Date());
		runInfo.setLast_time(lastTime);
		
		runInfo.setDevice_size(1);
		
		runInfo.setToday_amount(latest.getTodayAmount());
		
		runInfo.setWeek_amount(latest.getWeekAmount());
		
		runInfo.setMonth_amount(latest.getMonthAmount());
		
		//质量评判
		if (inTds >= 0 && inTds <= 50){
			runInfo.setIn_tds_quality("优");
		}else if (inTds > 50 && inTds <= 100){
			runInfo.setIn_tds_quality("良");
		}else if (inTds > 100 && inTds <= 300) {
			runInfo.setIn_tds_quality("中");
		}else{
			runInfo.setIn_tds_quality("差");
		}
		
		
		if (outTds >= 0 && outTds <= 50){
			runInfo.setOut_tds_quality("优");
		}else if (outTds > 50 && outTds <= 100){
			runInfo.setOut_tds_quality("良");
		}else if (outTds > 100 && outTds <= 300){
			runInfo.setOut_tds_quality("中");
		}else{
			runInfo.setOut_tds_quality("差");
		}
		
		
		//色度
		if (inSd >= 0 && inSd <= 1.0){
			runInfo.setIn_sd_quality("纯净");
		}else {
			runInfo.setIn_sd_quality("较纯");
		}
		
		if (outZd >= 0 && outZd <= 1.0){
			runInfo.setOut_sd_quality("纯净");
		}else {
			runInfo.setOut_sd_quality("较纯");
		}		

		
		//浊度
		if (inZd >= 0 && inZd <= 1.0){
			runInfo.setIn_zd_quality("澄清");
		}else {
			runInfo.setIn_zd_quality("较清");
		}
		
		if (outZd >= 0 && outZd <= 1.0){
			runInfo.setOut_zd_quality("澄清");
		}else {
			runInfo.setOut_zd_quality("较清");
		}		
		
		
				
		result = JSONObject.fromObject(runInfo);
	
		return result;									
		
	}		
	
	
	@ResponseBody
	@RequestMapping(value = "/getLatestWaterQuality3", method = RequestMethod.GET)
	public ResultVO getLatestWaterQuality3(HttpServletRequest request) {
	
	
		HttpSession session = request.getSession();
		
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(500);
		
		User user = (User)session.getAttribute("user");
		if (null == user) {
			resultVO.setMsg("用户为空");
			return resultVO;
		}
		
		String deviceId = request.getParameter("deviceId");
		if (null == deviceId || deviceId.equals("")) {
			resultVO.setMsg("deviceId参数出错");
			return resultVO;
		}
		System.out.println("deviceId="+deviceId);
			
		List<History> histories = historyService.getHistoryTop20List(deviceId, 1);
		if (null == histories || histories.size() == 0) {
			resultVO.setMsg("未查询到历史记录");
			return resultVO;
		}
		
		
		History latest = histories.get(0);
		
	    //日，周， 月流量 没有
//		Date currentDate = new Date();
// 		latest.setTodayAmount(getAmount(latest.getDeviceId(), DateUtil.getDayBegin(), currentDate));
// 		latest.setWeekAmount(getAmount(latest.getDeviceId(), DateUtil.getBeginDayOfWeek(), currentDate));
// 		latest.setMonthAmount(getAmount(latest.getDeviceId(), DateUtil.getBeginDayOfMonth(), currentDate));
 		
		// 需要计算
		
		JSONObject result = new JSONObject();
		
		Integer inTds = latest.getTds();
		Integer outTds = latest.getPurifyTds();
		
		Float inSd = latest.getColor();
		Float outSd = latest.getPurifyColor();
		Float inZd = latest.getTbdt();
		Float outZd = latest.getPurifyTbdt();
		
		LatestRunInfo runInfo = new LatestRunInfo();
		runInfo.setDeviceId(deviceId);
		runInfo.setLatest_device_count(1);
		runInfo.setLatest_in_tds(inTds);
		runInfo.setLatest_out_tds(outTds);
		
		runInfo.setLatest_in_sd(inSd);
		runInfo.setLatest_out_sd(outSd);
		
		runInfo.setLatest_in_zd(inZd);
		runInfo.setLatest_out_zd(outZd);
		
		runInfo.setLatest_tds_tm(new Date());
		
		runInfo.setDevice_size(1);
		
		runInfo.setToday_amount(latest.getTodayAmount());
		
		runInfo.setWeek_amount(latest.getWeekAmount());
		
		runInfo.setMonth_amount(latest.getMonthAmount());
		
		//质量评判
		if (inTds >= 0 && inTds <= 50){
			runInfo.setIn_tds_quality("优");
		}else if (inTds > 50 && inTds <= 100){
			runInfo.setIn_tds_quality("良");
		}else if (inTds > 100 && inTds <= 300) {
			runInfo.setIn_tds_quality("中");
		}else{
			runInfo.setIn_tds_quality("差");
		}
		
		
		if (outTds >= 0 && outTds <= 50){
			runInfo.setOut_tds_quality("优");
		}else if (outTds > 50 && outTds <= 100){
			runInfo.setOut_tds_quality("良");
		}else if (outTds > 100 && outTds <= 300){
			runInfo.setOut_tds_quality("中");
		}else{
			runInfo.setOut_tds_quality("差");
		}
		
		
		//色度
		if (inSd >= 0 && inSd <= 1.0){
			runInfo.setIn_sd_quality("纯净");
		}else {
			runInfo.setIn_sd_quality("较纯");
		}
		
		if (outZd >= 0 && outZd <= 1.0){
			runInfo.setOut_sd_quality("纯净");
		}else {
			runInfo.setOut_sd_quality("较纯");
		}		

		
		//浊度
		if (inZd >= 0 && inZd <= 1.0){
			runInfo.setIn_zd_quality("澄清");
		}else {
			runInfo.setIn_zd_quality("较清");
		}
		
		if (outZd >= 0 && outZd <= 1.0){
			runInfo.setOut_zd_quality("澄清");
		}else {
			runInfo.setOut_zd_quality("较清");
		}					
		
				
		result = JSONObject.fromObject(runInfo);
	
//		return result;
		resultVO.setCode(200);
		resultVO.setObj(result);
		return resultVO;
		
	}	
	
	
	
	
	//test end
	
	/**
	 *  
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/getDeviceList", method = RequestMethod.GET)
	public JSONObject getDeviceList(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		JSONObject result = new JSONObject();
		
		List<String> deviceList = new ArrayList<>();
		
//		String deviceId = request.getParameter("deviceId");
		
		List<Device> devices = deviceService.getDeviceList(user.getRegion_id(), user.getId(), 0); //正在管理中的设备
		for (Device device : devices) {
			System.out.println("deviceId="+device.getDeviceId());
			deviceList.add(device.getDeviceId());
		}
		JSONArray jsonArray = JSONArray.fromObject(deviceList);
		result.put("devices", jsonArray);
		
		
		return result;
	
	}
	
	@ResponseBody
	@RequestMapping(value = "/getDeviceSummary", method = RequestMethod.GET)
	public ResultVO getDeviceSummary(HttpServletRequest request) {
	
	
		HttpSession session = request.getSession();
		
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(500);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		User user = (User)session.getAttribute("user");
		if (null == user) {
			resultVO.setMsg("用户为空");
			return resultVO;
		}
		
		
//		List<Device> devTotals = service.getDeviceListByUser(user.getId(), 0);
		List<Device> devTotals = service.getDeviceListByUser(user.getId());
		
		if (null == devTotals) {
			resultVO.setMsg("查询设备数量出错");
			return resultVO;
		}
		
		List<Device> devFilterReplace = service.getDevFilterReplace(user.getId());
		
		if (null == devFilterReplace) {
			resultVO.setMsg("查询换芯设备出错");
			return resultVO;
		}
		
//		List<Device> devBreakdowns = service.getDeviceListByRunState(user.getId(), 0, 1);
		List<Device> devBreakdowns = service.getDeviceListByRunState(user.getId(), 1);
		
		if (null == devBreakdowns) {
			resultVO.setMsg("查询故障设备出错");
			return resultVO;
		}
		
		resultMap.put("devTotal", devTotals.size());
		resultMap.put("replaceCoreDev", devFilterReplace.size());
		resultMap.put("breakdownDev", devBreakdowns.size());
		
		JSONObject json = new JSONObject();
		json = JSONObject.fromObject(resultMap);
		resultVO.setCode(200);
		resultVO.setMsg("请求成功");
		resultVO.setObj(json);
		return resultVO;
		
	}	
	
	
	
		
	

}
