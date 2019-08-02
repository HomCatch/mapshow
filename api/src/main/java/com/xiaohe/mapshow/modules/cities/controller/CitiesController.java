package com.xiaohe.mapshow.modules.cities.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.modules.cities.entity.Cities;
import com.xiaohe.mapshow.modules.cities.entity.QueryCities;
import com.xiaohe.mapshow.modules.cities.service.CitiesService;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 行政区域地州市信息表 CitiesController层
 *
 * @author hzh
 * @since 2019-04-08
 */
@RestController
@RequestMapping(value = "/citiess")
public class CitiesController {
	private static Logger log = LoggerFactory.getLogger(CitiesController.class);
	@Autowired
	private CitiesService citiesService;


	/**
	 * 保存对象<br/>
	 *
	 * @param cities 对象
	 */
	@PostMapping
	@RequiresPermissions("sys:cities:save")
	@LogOperate(description = "新增Cities")
	public Result save(@Validated @RequestBody Cities cities) {
		Result result = new Result();
		try {
			citiesService.save(cities);
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2001, "新增失败");
		}
		return result.ok();
	}

	/**
	 * 更新
	 *
	 * @param cities
	 * @return
	 */
	@PutMapping(value = "/{id}")
	@RequiresPermissions("sys:cities:update")
	@LogOperate(description = "更新Cities")
	public Result updateBanner(@Validated @RequestBody Cities cities) {
		Result result = new Result();
		try {
			boolean exists = citiesService.existsById(cities.getId());
			if (!exists) {
				return result.error(2002, "修改失败，未找到");
			}
			citiesService.save(cities);
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
	@RequiresPermissions("sys:cities:delete")
	@LogOperate(description = "删除Cities")
	public Result deleteById(@PathVariable("id") String ids) {
		Result result = new Result();
		try {
			citiesService.deleteInBatch(StringUtil.stringToIntegerList(ids));
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
	 * @return Cities 对象
	 */
	@GetMapping(value = "/{id}")
	@RequiresPermissions("sys:cities:info")
	public Result findById(@PathVariable("id") Integer id) {
		CommonResult result = new CommonResult();
		try {
			result.setDatas(citiesService.findById(id));
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2004, "不存在");
		}
		return result.ok();
	}

	/**
	 * 分页查询
	 *
	 * @return Page<Cities> 对象
	 */
	@PostMapping(value = "/pages")
	@RequiresPermissions("sys:cities:list")
	public Result findByPage(@RequestBody QueryCities queryCities) {

		CommonResult result = new CommonResult();
		try {
			int page = queryCities.getPage();
			int pageSize = queryCities.getPageSize();
			Page<Cities> all = citiesService.findAll(page - 1, pageSize, queryCities);
			PageBean<Cities> pageBean = new PageBean<>();
			if (all == null||0==all.getTotalElements()) {
				pageBean.setList(new ArrayList<>());
				result.setDatas(pageBean);
				return result.ok();
			}
			pageBean.setCurPage(page);
			pageBean.setItemCounts((int) all.getTotalElements());
			pageBean.setPageSize(pageSize);
			pageBean.setList(all.getContent());
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
			List<Cities> list = citiesService.findAllById(StringUtil.stringToIntegerList(ids));
			workbook = ExcelExportUtil.exportExcel(new ExportParams("行政区域地州市信息表表", "cities"),
					Cities.class, list);
			//保存在本地
			String filename = "ter_daily_report" + System.currentTimeMillis();
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
}
