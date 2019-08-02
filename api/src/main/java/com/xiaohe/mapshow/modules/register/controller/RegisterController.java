package com.xiaohe.mapshow.modules.register.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.service.SysUserService;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.register.service.RegisterService;
import com.xiaohe.mapshow.modules.register.entity.Register;
import com.xiaohe.mapshow.modules.register.entity.QueryRegister;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.util.ArrayList;

/**
 * RegisterController层
 *
 * @author gmq
 * @since 2019-04-22
 */
@RestController
@RequestMapping(value = "/registers")
public class RegisterController {
	private static Logger log = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	private RegisterService registerService;
	@Autowired
	private SysUserService sysUserService;



	/**
	 * 通过id集合删除对象
	 *
	 * @param userId id集合
	 */
	@DeleteMapping(value = "/{userId}")
	@RequiresPermissions("sys:register:delete")
	@LogOperate(description = "删除Register")
	@Transactional(rollbackFor = Exception.class)
	public Result deleteById(@PathVariable("userId") String userId) {
		Result result = new Result();
		try {
			List<Integer> userIds = StringUtil.stringToIntegerList(userId);
			//删除用户表信息
			sysUserService.deleteBatchIds(userIds);
			//删除注册表信息
			registerService.deleteRegisterByUserIdIn(userIds);
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
	 * @return Register 对象
	 */
	@GetMapping(value = "/{id}")
	@RequiresPermissions("sys:register:info")
	public Result findById(@PathVariable("id") Integer id) {
		CommonResult result = new CommonResult();
		try {
			Register byId = registerService.findById(id);
			result.setDatas(sysUserService.selectById(byId.getUserId()));
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2004, "不存在");
		}
		return result.ok();
	}

	/**
	 * 分页查询
	 *
	 * @return Page<Register> 对象
	 */
	@PostMapping(value = "/pages")
	@RequiresPermissions("sys:register:list")
	public Result findByPage(@RequestBody QueryRegister queryRegister) {

		CommonResult result = new CommonResult();
		try {
			int page = queryRegister.getPage();
			int pageSize = queryRegister.getPageSize();
			Page<Register> all = registerService.findAll(page - 1, pageSize, queryRegister);
			PageBean<SysUserEntity> pageBean = new PageBean<>();
			if (all == null||0==all.getTotalElements()) {
				pageBean.setList(new ArrayList<>());
				result.setDatas(pageBean);
				return result.ok();
			}
			List<Register> content = all.getContent();
			//查询用户具体信息
			List<Long> userIds = new LinkedList<>();
			for (Register register : content) {
				userIds.add(Long.valueOf(register.getUserId()));
			}
			//根据用户ID查询用户信息
			List<SysUserEntity> sysUserEntities = sysUserService.selectBatchIds(userIds);
			pageBean.setCurPage(page);
			pageBean.setItemCounts((int) all.getTotalElements());
			pageBean.setPageSize(pageSize);
			pageBean.setList(sysUserEntities);
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
			List<Register> list = registerService.findAllById(StringUtil.stringToIntegerList(ids));
			workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "register"),
					Register.class, list);
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
