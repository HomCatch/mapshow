package com.xiaohe.mapshow.modules.device.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.config.annotation.DemoIntercept;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
import com.xiaohe.mapshow.modules.sys.controller.AbstractController;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.modules.sys.service.SysUserService;
import com.xiaohe.mapshow.modules.userinfo.entity.UserInfo;
import com.xiaohe.mapshow.modules.userinfo.service.UserInfoService;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.device.service.DeviceService;
import com.xiaohe.mapshow.modules.device.entity.Device;
import com.xiaohe.mapshow.modules.device.entity.QueryDevice;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DeviceController层
 *
 * @author gmq
 * @since 2019-03-28
 */
@RestController
@RequestMapping(value = "/devices")
public class DeviceController extends AbstractController {
	private static Logger log = LoggerFactory.getLogger(DeviceController.class);
	private final DeviceService deviceService;
	private final SysDeptService sysDeptService;
	private final UserInfoService userInfoService;
	@Value("${ds_base}")
	private String dsBase;

	public DeviceController(DeviceService deviceService, SysDeptService sysDeptService, UserInfoService userInfoService) {
		this.deviceService = deviceService;
		this.sysDeptService = sysDeptService;
		this.userInfoService = userInfoService;
	}


	/**
	 * 保存对象<br/>
	 *
	 * @param device 对象
	 */
	@PostMapping
//    @RequiresPermissions("sys:device:save")
	@LogOperate(description = "新增Device")
	@DemoIntercept
	public Result save(@Validated @RequestBody Device device) {
		Result result = new Result();
		try {
			device.setBindedUserId(getUserId().intValue());
			deviceService.save(device);
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2001, "新增失败");
		}
		return result.ok();
	}

	/**
	 * 更新
	 *
	 * @param device
	 * @return
	 */
	@PutMapping(value = "/{id}")
//    @RequiresPermissions("sys:device:update")
	@LogOperate(description = "更新Device")
	@DemoIntercept
	public Result updateBanner(@Validated @RequestBody Device device) {
		Result result = new Result();
		try {
			boolean exists = deviceService.existsById(device.getId());
			if (!exists) {
				return result.error(2002, "修改失败，未找到");
			}
			deviceService.save(device);
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2002, "修改失败");
		}
		return result.ok();
	}

	/**
	 * 通过id集合删除对象
	 *
	 * @param ids id集合
	 */
	@DeleteMapping(value = "/{id}")
//    @RequiresPermissions("sys:device:delete")
	@LogOperate(description = "删除Device")
	@DemoIntercept
	public Result deleteById(@PathVariable("id") String ids) {
		Result result = new Result();
		try {
			deviceService.deleteInBatch(StringUtil.stringToIntegerList(ids));
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2003, "删除失败");
		}
		return result.ok();
	}


	/**
	 * 通过id查找对象
	 *
	 * @param id id
	 * @return Device 对象
	 */
	@GetMapping(value = "/{id}")
//    @RequiresPermissions("sys:device:info")
	public Result findById(@PathVariable("id") Integer id) {
		CommonResult result = new CommonResult();
		try {
			result.setDatas(deviceService.findById(id));
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2004, "不存在");
		}
		return result.ok();
	}

	/**
	 * 分页查询
	 *
	 * @return Page<Device> 对象
	 */
	@PostMapping(value = "/pages")
//    @RequiresPermissions("sys:device:list")
	public Result findByPage(@RequestBody QueryDevice queryDevice) {

		CommonResult result = new CommonResult();
		try {
			int page = queryDevice.getPage();
			int pageSize = queryDevice.getPageSize();
			//赋值用户ID
			queryDevice.setBindedUserId(getUserId().intValue());
			Page<Device> all = deviceService.findAll(page - 1, pageSize, queryDevice);
			PageBean<Device> pageBean = new PageBean<>();
			if (all == null||0==all.getTotalElements()) {
				pageBean.setList(new ArrayList<>());
				result.setDatas(pageBean);
				return result.ok();
			}
			List<Device> content = all.getContent();
			List<Integer> appuserIds = content.stream().map(Device::getBindedAppId).collect(Collectors.toList());
			List<UserInfo> userInfos = userInfoService.findAllById(appuserIds);
			for (Device device : content) {
				 Integer bindedUserId = device.getBindedUserId();
				Integer bindedAppId = device.getBindedAppId();
				device.setDeptId(bindedUserId !=null? bindedUserId.toString():"");
				device.setBindStatus(bindedAppId !=null?1:0);
				for (UserInfo userInfo : userInfos) {
					if(bindedAppId!=null&& bindedAppId.equals(userInfo.getId())){
						device.setBindAccount(userInfo.getNickName());
					}
				}
			}
			pageBean.setCurPage(page);
			pageBean.setItemCounts((int) all.getTotalElements());
			pageBean.setPageSize(pageSize);
			pageBean.setList(content);
			result.setDatas(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return result.error(2005, "查询出错");
		}
		return result.ok();
	}

	/**
	 * EXCEL导出
	 *
	 * @param response re
	 * @throws IOException IO异常
	 */
	@GetMapping(value = "/export")
	public void prjExport(HttpServletResponse response) throws IOException {
		OutputStream os = null;
		try {
			//获取数据库数据
			Workbook workbook;
			List<Device> list = deviceService.getAll();
			workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "device"),
					Device.class, list);
			//保存在本地
			String filename = "device" + System.currentTimeMillis();
			os = response.getOutputStream();
			// 清空输出流
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");
			response.setContentType("application/msexcel");
			if (workbook != null) {
				workbook.write(os);
				log.info("导出成功.....");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			if (os != null) {
				os.flush();
				os.close();
			}
		}
	}

	/**
	 * 设备总览
	 */
	@PostMapping(value = "/pandect")
	public Result pandect(@RequestBody DashboardProperty dashboardProperty) {
		CommonResult result = new CommonResult();
		try {
			//设备总数 演示模拟
			long count = deviceService.count();
			//在线数量 演示模拟
			long countOnline = 251L;
			if (1 == dashboardProperty.getDataModel()) {
				//按经销商分类
				List<Long> allSubUserId = new ArrayList<>();
				if (1 == getUserId()) {
					allSubUserId.add(1L);
				} else {
					allSubUserId = sysDeptService.findSelfAndSubUserId();
				}
				List<Integer> ids = allSubUserId.stream().map(Long::intValue).collect(Collectors.toList());
				count = deviceService.countByBindedUserIdIn(ids);
				countOnline = deviceService.countByRunStateEqualsAndBindedUserIdIn(1, ids);
			}
			Map<String, Object> stringObjectHashMap = new HashMap<>();
			stringObjectHashMap.put("total", count);
			stringObjectHashMap.put("online", countOnline);
			stringObjectHashMap.put("offline", count - countOnline);
			result.setDatas(stringObjectHashMap);
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2003, "删除失败");
		}
		return result.ok();
	}

	/**
	 * 地图显示
	 *
	 * @param province 省份
	 * @param city     城市
	 * @return 结果
	 */
	@GetMapping(value = "/deviceMap")
	public Result findDeviceListByProvince(@RequestParam(value = "province", required = false) String province,
	                                       @RequestParam(value = "city", required = false) String city) {
		CommonResult commonResult = new CommonResult();
		List<Device> list = deviceService.findDeviceListByArea(province, city);
		commonResult.setDatas(list);
		return commonResult.ok();
	}

}
