package com.xiaohe.mapshow.modules.company.controller;

import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.config.validate.XException;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.query.QueryVo;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.modules.company.service.CompanyService;
import com.xiaohe.mapshow.modules.company.entity.Company;
import com.xiaohe.mapshow.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * CompanyController层
 *
 * @author gmq
 * @since 2019-07-10
 */
@RestController
@RequestMapping(value = "/companys")
public class CompanyController {
	private static Logger log = LoggerFactory.getLogger(CompanyController.class);
	@Autowired
	private CompanyService companyService;

	/**
	 * 保存对象<br/>
	 *
	 * @param company 对象
	 */
	@PostMapping
	@RequiresPermissions("sys:company:save")
	@LogOperate(description = "新增Company")
	public Result save(@Validated @RequestBody Company company) {
		Result result = new Result();
		try {
			companyService.insert(company);
		} catch (XException e) {
			log.error(e.getMessage());
			return result.error(e.getRet(), e.getMsg());
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2001, "新增失败");
		}
		return result.ok();
	}

	/**
	 * 更新
	 *
	 * @param company
	 * @return
	 */
	@PutMapping(value = "/{id}")
	@RequiresPermissions("sys:company:update")
	@LogOperate(description = "更新Company")
	public Result update(@Validated @RequestBody Company company, @PathVariable("id") Integer id) {
		Result result = new Result();
		try {
			companyService.updateById(company);
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
	@RequiresPermissions("sys:company:delete")
	@LogOperate(description = "删除Company")
	public Result deleteById(@PathVariable("id") String ids) {
		Result result = new Result();
		try {
			companyService.deleteBatchIds(StringUtil.stringToIntegerList(ids));
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
	 * @return RepairRecord 对象
	 */
	@GetMapping(value = "/{id}")
	@RequiresPermissions("sys:company:info")
	public Result findById(@PathVariable("id") Integer id) {
		CommonResult result = new CommonResult();
		try {
			result.setDatas(companyService.selectById(id));
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2004, "不存在");
		}
		return result.ok();
	}

	/**
	 * 分页查询
	 *
	 * @return Page<RepairRecord> 对象
	 */
	@PostMapping(value = "/pages")
	@RequiresPermissions("sys:company:list")
	public Result findByPage(@RequestBody QueryVo queryVo) {
		PageUtils page = companyService.queryPage(queryVo);
		CommonResult result = new CommonResult();
		try {
			PageBean<Company> pageBean = new PageBean<>();
			pageBean.setCurPage(page.getCurrPage());
			pageBean.setItemCounts(page.getTotalCount());
			pageBean.setPageSize(page.getTotalPage());
			List<Company> companyList = page.getList() != null ? (List<Company>) page.getList() : null;
			pageBean.setList(companyList);
			result.setDatas(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return result.error(2005, "查询出错");
		}
		return result.ok();
	}
}
