
package com.xiaohe.mapshow.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 系统用户
 * 
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	SysUserEntity  findByUsername(String username);

	/**
	 * 查询所有子部门用户ID
	 * @param longs 部门ID
	 * @return 用户ID集合
	 */
	List<Long> queryAllSubUserId(Long[] longs);
	/**
	 * 查询所有子部门用户名
	 * @param longs 部门ID
	 * @return 用户名集合
	 */
	List<String> queryAllSubUserName(Long[] longs);

	List<Long> findUserByDeptIds(List<Long> subDeptIdList);

	/**
	 * 修改用户状态
	 * @param username 账户
	 * @return 1
	 */
	int updateUserStatus(@Param("username") String username,@Param("status") Integer status);

	/**
	 * 保存或更新用户
	 */
	int saveOrUpdate(SysUserEntity sysUserEntity);
}
