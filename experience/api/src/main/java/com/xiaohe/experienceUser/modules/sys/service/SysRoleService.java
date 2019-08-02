
package com.xiaohe.experienceUser.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.xiaohe.experienceUser.modules.sys.entity.SysRoleEntity;
import com.xiaohe.experienceUser.modules.untils.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * 角色
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(SysRoleEntity role);

    void update(SysRoleEntity role);

    void deleteBatch(Long[] roleIds);

    List<SysRoleEntity> selectByUserId(Long userId);

}
