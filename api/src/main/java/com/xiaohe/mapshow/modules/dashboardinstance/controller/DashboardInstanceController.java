package com.xiaohe.mapshow.modules.dashboardinstance.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.modules.dashboardinstance.entity.DashboardAddVo;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
import com.xiaohe.mapshow.modules.dashboardproperty.service.DashboardPropertyService;
import com.xiaohe.mapshow.modules.sys.controller.AbstractController;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.dashboardinstance.service.DashboardInstanceService;
import com.xiaohe.mapshow.modules.dashboardinstance.entity.DashboardInstance;
import com.xiaohe.mapshow.modules.dashboardinstance.entity.QueryDashboardInstance;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 仪表盘实例 DashboardInstanceController层
 *
 * @author gmq
 * @since 2019-04-01
 */
@RestController
@RequestMapping(value = "/dashboardInstances")
public class DashboardInstanceController extends AbstractController {
	private static Logger log = LoggerFactory.getLogger(DashboardInstanceController.class);
	private final DashboardInstanceService dashboardInstanceService;
	private final DashboardPropertyService dashboardPropertyService;

	@Autowired
	public DashboardInstanceController(DashboardInstanceService dashboardInstanceService, DashboardPropertyService dashboardPropertyService) {
		this.dashboardInstanceService = dashboardInstanceService;
		this.dashboardPropertyService = dashboardPropertyService;
	}


	/**
	 * 拖动保存对象<br/>
	 *
	 * @param dashboardInstanceList 对象
	 */
	@PostMapping
//    @RequiresPermissions("sys:dashboardInstance:save")
	@LogOperate(description = "新增DashboardInstance")
	public Result save(@Validated @RequestBody List<DashboardInstance> dashboardInstanceList) {
		Result result = new Result();
		try {
			for (DashboardInstance dashboardInstance : dashboardInstanceList) {
				dashboardInstance.setUserId(getUserId());
			}
			dashboardInstanceService.saveAll(dashboardInstanceList);
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2001, "新增失败");
		}
		return result.ok();
	}

	/**
	 * 点击保存实例<br/>
	 *
	 * @param dashboardAddVo 对象
	 */
	@PostMapping("/saveClick")
	public Result saveClick(@Validated @RequestBody DashboardAddVo dashboardAddVo) {
		Result result = new Result();
		try {
			DashboardInstance instance = new DashboardInstance();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("x" , dashboardAddVo.getX());
			jsonObject.put("y" , dashboardAddVo.getY());
			jsonObject.put("w" , dashboardAddVo.getW());
			jsonObject.put("h" , dashboardAddVo.getH());
			instance.setPosition(JSON.toJSONString(jsonObject));
			instance.setUserId(getUserId());
			instance.setName(dashboardAddVo.getName());
			int typeId = dashboardAddVo.getTypeId();
			boolean b = dashboardPropertyService.existsById(typeId);
			if(!b){
				return result.error(2001, "新增失败,类型ID不存在");
			}
			instance.setDashboardPropertyId(typeId);
			dashboardInstanceService.save(instance);
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2001, "新增失败");
		}
		return result.ok();
	}


	/**
	 * 更新
	 *
	 * @param dashboardInstance
	 * @return
	 */
	@PutMapping(value = "/{id}")
//    @RequiresPermissions("sys:dashboardInstance:update")
	@LogOperate(description = "更新DashboardInstance")
	public Result updateBanner(@Validated @RequestBody DashboardInstance dashboardInstance) {
		Result result = new Result();
		try {
			boolean exists = dashboardInstanceService.existsById(dashboardInstance.getId());
			if (!exists) {
				return result.error(2002, "修改失败，未找到");
			}
			dashboardInstanceService.save(dashboardInstance);
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2002, "修改失败");
		}
		return result.ok();
	}

	/**
	 * 通过id删除对象
	 *
	 * @param id id集合
	 */
	@DeleteMapping(value = "/{id}")
//    @RequiresPermissions("sys:dashboardInstance:delete")
	@LogOperate(description = "删除DashboardInstance")
	public Result deleteById(@PathVariable("id") int id) {
		Result result = new Result();
		try {
			List<Integer> integers = new ArrayList<>();
			integers.add(id);
			dashboardInstanceService.deleteInBatch(integers);
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
	 * @return DashboardInstance 对象
	 */
	@GetMapping(value = "/{id}")
	@RequiresPermissions("sys:dashboardInstance:info")
	public Result findById(@PathVariable("id") Integer id) {
		CommonResult result = new CommonResult();
		try {
			result.setDatas(dashboardInstanceService.findById(id));
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2004, "不存在");
		}
		return result.ok();
	}

	/**
	 * 分页查询
	 *
	 * @return Page<DashboardInstance> 对象
	 */
	@PostMapping(value = "/pages")
//    @RequiresPermissions("sys:dashboardInstance:list")
	public Result findByPage(@RequestBody QueryDashboardInstance queryDashboardInstance) {

		CommonResult result = new CommonResult();
		try {
			int page = queryDashboardInstance.getPage();
			int pageSize = queryDashboardInstance.getPageSize();
			//关联用户ID
			queryDashboardInstance.setUserId(getUserId());
			Page<DashboardInstance> all = dashboardInstanceService.findAll(page - 1, pageSize, queryDashboardInstance);
			PageBean<DashboardInstance> pageBean = new PageBean<>();
			if (all == null || all.getContent().size() == 0) {
				//用户默认初始化参数
				String jsonArr = "[{\"id\":1,\"boardPosition\":{\"w\":12,\"moved\":false,\"x\":0,\"h\":11,\"i\":1,\"y\":12},\"position\":\"{\\\"i\\\":1,\\\"x\\\":0,\\\"y\\\":12,\\\"w\\\":12,\\\"h\\\":11,\\\"moved\\\":false}\",\"dashboardPropertyId\":3,\"name\":\"近半月的用水量\",\"backgroundColor\":null,\"userId\":1,\"dashboardProperty\":{\"id\":3,\"type\":\"最近15日用水量\",\"styleData\":\"{\\\"name\\\":\\\"张三\\\",\\\"age\\\":15}\",\"styleJson\":{\"name\":\"张三\",\"age\":15}}},{\"id\":2,\"boardPosition\":{\"w\":6,\"moved\":false,\"x\":6,\"h\":12,\"i\":2,\"y\":0},\"position\":\"{\\\"i\\\":2,\\\"x\\\":6,\\\"y\\\":0,\\\"w\\\":6,\\\"h\\\":12,\\\"moved\\\":false}\",\"dashboardPropertyId\":2,\"name\":\"TDS\",\"backgroundColor\":null,\"userId\":1,\"dashboardProperty\":{\"id\":2,\"type\":\"TDS近7日趋势图\",\"styleData\":\"{\\\"name\\\":\\\"张三\\\",\\\"age\\\":15}\",\"styleJson\":{\"name\":\"张三\",\"age\":15}}},{\"id\":3,\"boardPosition\":{\"w\":6,\"moved\":false,\"x\":0,\"h\":12,\"i\":3,\"y\":0},\"position\":\"{\\\"i\\\":3,\\\"x\\\":0,\\\"y\\\":0,\\\"w\\\":6,\\\"h\\\":12,\\\"moved\\\":false}\",\"dashboardPropertyId\":1,\"name\":\"设备总览\",\"backgroundColor\":null,\"userId\":1,\"dashboardProperty\":{\"id\":1,\"type\":\"设备总览\",\"styleData\":\"{\\\"name\\\":\\\"张三\\\",\\\"age\\\":15}\",\"styleJson\":{\"name\":\"张三\",\"age\":15}}}]";
                List<DashboardInstance> instanceList = JSONArray.parseArray(jsonArr, DashboardInstance.class);
				pageBean.setList(instanceList);
				pageBean.setCurPage(1);
				pageBean.setItemCounts(3);
				result.setDatas(pageBean);
				return result.ok();
			}
			pageBean.setCurPage(page);
			pageBean.setItemCounts((int) all.getTotalElements());
			pageBean.setPageSize(pageSize);
			List<DashboardInstance> content = all.getContent();
			//查询dashBoardproperty表  匹配对应的风格属性表信息
			List<DashboardProperty> proList = dashboardPropertyService.findAll();
			for (DashboardProperty dashboardProperty : proList) {
				for (DashboardInstance instance : content) {
					if (dashboardProperty.getId().equals(instance.getDashboardPropertyId())) {
						instance.setDashboardProperty(dashboardProperty);
						if (StringUtils.isNotBlank(instance.getPosition())) {
							instance.setBoardPosition(JSON.parse(instance.getPosition()));
						}
					}
				}
			}

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
	 * @param ids      id逗号隔开
	 * @throws IOException IO异常
	 */
	@GetMapping(value = "/export/{id}")
	public void prjExport(HttpServletResponse response, @PathVariable("id") String ids) throws IOException {
		OutputStream os = null;
		try {
			//获取数据库数据
			Workbook workbook;
			List<DashboardInstance> list = dashboardInstanceService.findAllById(StringUtil.stringToIntegerList(ids));
			workbook = ExcelExportUtil.exportExcel(new ExportParams("仪表盘实例表" , "dashboard_instance"),
					DashboardInstance.class, list);
			//保存在本地
			String filename = "ter_daily_report" + System.currentTimeMillis();
			os = response.getOutputStream();
			// 清空输出流
			response.reset();
			response.setHeader("Content-disposition" , "attachment; filename=" + filename + ".xls");
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
}
