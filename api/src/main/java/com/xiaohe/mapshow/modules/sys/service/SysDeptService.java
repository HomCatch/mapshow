

package com.xiaohe.mapshow.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.xiaohe.mapshow.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
public interface SysDeptService extends IService<SysDeptEntity> {

	List<SysDeptEntity> queryList(Map<String, Object> map);

	/**
	 * 查询子部门ID列表
	 * @param parentId  上级部门ID
	 */
	List<Long> queryDetpIdList(Long parentId);

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	List<Long> getSubDeptIdList(Long deptId);

	/**
	 * 获取用户角色部门及子部门部门ID集合
	 */
	Long[] getSelfAndSubDeptIds();
	/**
	 * 获取用户角色部门及子部门部门用户名集合
	 */
	List<String> findSelfAndSubUserName();
	/**
	 * 获取用户角色部门及子部门部门用户ID集合
	 */
	List<Long> findSelfAndSubUserId();

	/**
	 * 查询所有部门ID
	 *
	 */
	List<Long> queryAllDetpIdList();

}
