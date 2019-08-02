package com.xiaohe.mapshow.modules.sys.controller;

import com.xiaohe.mapshow.config.annotation.DemoIntercept;
import com.xiaohe.mapshow.modules.sys.entity.SysDeptEntity;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.modules.untils.Constant;
import com.xiaohe.mapshow.modules.untils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
	@Autowired
	private SysDeptService sysDeptService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:dept:list")
	public R list(){
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

		return R.ok().put("deptList",deptList);
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	//TODO 数据库未加权限
//	@RequiresPermissions("sys:dept:select")
	public R select(){
		List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

		//添加一级部门
		if(getUserId() == Constant.SUPER_ADMIN){
			SysDeptEntity root = new SysDeptEntity();
			root.setDeptId(0L);
			root.setName("顶级级经销商");
			root.setParentId(-1L);
			root.setOpen(true);
			deptList.add(root);
		}

		return R.ok().put("deptList", deptList);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	@RequiresPermissions("sys:dept:list")
	public R info(){
		long deptId = 0;
		if(getUserId() != Constant.SUPER_ADMIN){
			List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
			Long parentId = null;
			for(SysDeptEntity sysDeptEntity : deptList){
				if(parentId == null){
					parentId = sysDeptEntity.getParentId();
					continue;
				}

				if(parentId > sysDeptEntity.getParentId().longValue()){
					parentId = sysDeptEntity.getParentId();
				}
			}
			deptId = parentId;
		}

		return R.ok().put("deptId", deptId);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{deptId}")
	@RequiresPermissions("sys:dept:info")
	public R info(@PathVariable("deptId") Long deptId){
		SysDeptEntity dept = sysDeptService.selectById(deptId);

		//添加部门名称
		Long parentId = dept.getParentId();
		if(0!=parentId){
			SysDeptEntity sysDeptEntity = sysDeptService.selectById(dept.getParentId());
			dept.setParentName(sysDeptEntity==null?null:sysDeptEntity.getName());
		}else {
			dept.setParentName("顶级经销商");
		}

		return R.ok().put("dept", dept);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:dept:save")
	@DemoIntercept
	public R save(@Validated @RequestBody SysDeptEntity dept){
		//校验上级部门ID是否存在
		List<Long> longs = sysDeptService.queryAllDetpIdList();
		if(!longs.contains(dept.getParentId())&&0!=dept.getParentId()){
			return R.error("上级经销商不存在！");
		}
		//经销商名称不能重复
		Map<String, Object> map = new HashMap<>();
		map.put("name", dept.getName());
		List<SysDeptEntity> sysDeptEntities = sysDeptService.selectByMap(map);
		if(!CollectionUtils.isEmpty(sysDeptEntities)){
			return R.error("经销商名称重复！");
		}
		sysDeptService.insert(dept);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:dept:update")
	@DemoIntercept
	public R update(@RequestBody SysDeptEntity dept){
		//不能设置上级经销商为自己 否则无限递归
		if(dept.getDeptId().equals(dept.getParentId())){
			return R.error("不能设置自己为上级经销商");
		}
		sysDeptService.updateById(dept);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:dept:delete")
	@DemoIntercept
	public R delete(long deptId){
		//判断是否有子部门
		List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
		if(deptList.size() > 0){
			return R.error("请先删除子部门");
		}

		sysDeptService.deleteById(deptId);
		
		return R.ok();
	}
	
}
