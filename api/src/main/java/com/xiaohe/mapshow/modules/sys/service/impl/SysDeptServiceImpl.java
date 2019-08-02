

package com.xiaohe.mapshow.modules.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaohe.mapshow.modules.sys.dao.SysDeptDao;
import com.xiaohe.mapshow.modules.sys.dao.SysUserDao;
import com.xiaohe.mapshow.modules.sys.entity.SysDeptEntity;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.modules.sys.service.SysRoleDeptService;
import com.xiaohe.mapshow.modules.sys.service.SysUserRoleService;
import com.xiaohe.mapshow.modules.sys.shiro.ShiroUtils;
import com.xiaohe.mapshow.modules.untils.Constant;
import com.xiaohe.mapshow.modules.untils.annotation.DataFilter;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service("sysDeptService")
@DS("#session.tenantName")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	@DataFilter(subDept = true, user = false)
	public List<SysDeptEntity> queryList(Map<String, Object> params){
		List<SysDeptEntity> deptList =
			this.selectList(new EntityWrapper<SysDeptEntity>()
			.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER)));

		for(SysDeptEntity sysDeptEntity : deptList){
			SysDeptEntity parentDeptEntity =  this.selectById(sysDeptEntity.getParentId());
			if(parentDeptEntity != null){
				sysDeptEntity.setParentName(parentDeptEntity.getName());
			}
		}
		return deptList;
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return baseMapper.queryDetpIdList(parentId);
	}

	@Override
	public List<Long> getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}

	@Override
	public Long[] getSelfAndSubDeptIds() {
		SysUserEntity user = ShiroUtils.getUserEntity();
		//部门ID列表
		Set<Long> deptIdList = new HashSet<>();

		//用户角色对应的部门ID列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
		if (roleIdList.size() > 0) {
			List<Long> userDeptIdList = sysRoleDeptService.queryDeptIdList(roleIdList.toArray(new Long[roleIdList.size()]));
			deptIdList.addAll(userDeptIdList);
		}

		//用户子部门ID列表
		List<Long> subDeptIdList = getSubDeptIdList(user.getDeptId());
		if (!CollectionUtils.isEmpty(subDeptIdList)) {
			deptIdList.addAll(subDeptIdList);
		}
		deptIdList.add(user.getDeptId());
		//根据deptId获取用户ID
		Long[] longs = new Long[deptIdList.size()];
		deptIdList.toArray(longs);
		return longs;
	}

	@Override
	public List<String> findSelfAndSubUserName() {
		return null;
	}
	/**
	 * 获取用户角色部门及子部门部门用户ID集合
	 */
	@Override
	public List<Long> findSelfAndSubUserId() {
		return sysUserDao.queryAllSubUserId(getSelfAndSubDeptIds());
	}

	@Override
	public List<Long> queryAllDetpIdList() {
		return baseMapper.queryAllDetpIdList();
	}
}
