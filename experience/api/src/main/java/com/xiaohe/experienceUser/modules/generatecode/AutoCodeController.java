package com.xiaohe.experienceUser.modules.generatecode;

import com.xiaohe.experienceUser.config.aspect.LogOperate;
import com.xiaohe.experienceUser.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import static com.xiaohe.experienceUser.utils.JPAGeneratorUtil.getColumnNames;

/**
 * @program: ysjsApi
 * @description: 自动生成代码
 * @author: Gmq
 * @date: 2018-11-27 13:54
 **/
@RestController
@RequestMapping(value = "/autoCode")
public class AutoCodeController {
	private static final Logger log = LoggerFactory.getLogger(AutoCodeController.class);

	private static Properties rb = null;

	static {
		rb = new Properties();
		/// code/src/main/resources/jpa.properties
		try {
			//打成jar时使用
			InputStream resourceAsStream = AutoCodeController.class.getResourceAsStream("/application.properties");
			//本地调用
			File file = new File("src/main/resources/application.properties");
			if (resourceAsStream == null) {
				FileInputStream in = new FileInputStream(file);
				rb.load(in);
			} else {
				rb.load(resourceAsStream);
			}
			// FileInputStream in = new FileInputStream(args[0].toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	@GetMapping("/run")
	@LogOperate(description = "代码生成")
	public Result jpaAutoCode(@RequestParam(value = "tableName", required = false, defaultValue = "") String tables,
	                          @RequestParam(value = "model", required = false, defaultValue = "1") Integer model) {
		Result result = new Result();
		try {
			if (StringUtils.isBlank(tables)) {
				return result.error(2222, "表名和菜单名要完整");
			}
			//增加新菜单脚本
			String table = null;
			if (tables.contains("tb_")) {
				table = StringUtil.lineToHump(tables.replace("tb_", ""));
			} else {
				table = StringUtil.lineToHump(tables);
			}
			String url = table.toLowerCase();
			//设置模块包名
			rb.setProperty("parent", rb.getProperty("parent") + "." + url);
			//设置表名
			rb.setProperty("include", tables);
			//设置js路径
			rb.setProperty("js", "js/" + url);
			//设置模块名
			rb.setProperty("model", url);
			JPAGeneratorUtil.autoCode(rb, model, null);
			rb.setProperty("parent", rb.getProperty("parent").replace("." + url, ""));
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2222, "代码生成失败");
		}
		return result.ok();
	}


	@PostMapping("/run")
	public Result jpaAutoCode(@RequestBody QueryAutoVo queryAutoVo) {
		List<AutoVo> data = queryAutoVo.getData();
//        Object[] data = queryAutoVo.getData();
//        if (data.length > 0) {
//            return new Result().ok();
//        }
		String tables = queryAutoVo.getTable();
		Result result = new Result();
		try {
			if (StringUtils.isBlank(tables)) {
				return result.error(2222, "表名和菜单名要完整");
			}
			//增加新菜单脚本
			String table = null;
			if (tables.contains("tb_")) {
				table = StringUtil.lineToHump(tables.replace("tb_", ""));
			} else {
				table = StringUtil.lineToHump(tables);
			}
			String url = table.toLowerCase();
			//设置模块包名
			rb.setProperty("parent", rb.getProperty("parent") + "." + url);
			//设置表名
			rb.setProperty("include", tables);
			//设置js路径
			rb.setProperty("js", "js/" + url);
			//设置模块名
			rb.setProperty("model", url);
			JPAGeneratorUtil.autoCode(rb, 1, data);
			rb.setProperty("parent", rb.getProperty("parent").replace("." + url, ""));
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2222, "代码生成失败");
		}
		return result.ok();
	}

	@GetMapping("/")
	public Result getTableList(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
	                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
	                           @RequestParam(value = "tableName", required = false, defaultValue = "") String tableName) {
		CommonResult result = new CommonResult();
		try {
			List<String> tableList = JPAGeneratorUtil.getTableList(rb);
			int total = tableList.size();
			Collections.sort(tableList);
			PageBean<Object> pageBean = new PageBean<>();
			if (CollectionUtils.isEmpty(tableList)) {
				pageBean.setList(new ArrayList<>());
				result.setDatas(pageBean);
				return result.ok();
			}
			if (StringUtils.isNotBlank(tableName)) {
				List<Object> list = new ArrayList<>();
				for (String table : tableList) {
					if (table.contains(tableName)) {
						Map<String, String> stringMap = new HashMap<>();
						stringMap.put("table", table);
						list.add(stringMap);
					}
				}
				if (list.size() != 0) {
					pageBean.setList(list);
					result.setDatas(pageBean);
					return result.ok();
				}
				pageBean.setList(new ArrayList<>());
				result.setDatas(pageBean);
				return result.ok();
			}
			List<String> list = tableList.subList((page - 1) * pageSize, page <= (total / pageSize) ? page * pageSize : total);
			List<Object> list1 = new LinkedList<>();
			for (String s : list) {
				Map<String, String> stringMap = new HashMap<>();
				stringMap.put("table", s);
				list1.add(stringMap);
			}
			pageBean.setCurPage(page);
			pageBean.setItemCounts(total);
			pageBean.setPageSize(pageSize);
			pageBean.setList(list1);
			result.setDatas(pageBean);
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2222, "获取表列表失败");
		}
		return result.ok();
	}

	/**
	 * 得到表的字段名
	 *
	 * @param table
	 * @return
	 */
	@GetMapping("/columnNames")
	public Result getName(String table) {
		CommonResult result = new CommonResult();
		List<String> columnNames = getColumnNames(table, rb);
		result.ok();
		result.setDatas(columnNames);
		return result;
	}
}
