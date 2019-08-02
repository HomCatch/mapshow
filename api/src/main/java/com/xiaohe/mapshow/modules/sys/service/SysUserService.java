package com.xiaohe.mapshow.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.untils.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 */
public interface SysUserService extends IService<SysUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 保存用户
     */
    int save(SysUserEntity user);
    /**
     * 新增管理员 不支持事务
     */
    int saveSys(SysUserEntity user);

    /**
     * 修改用户
     */
    int update(SysUserEntity user);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);


    SysUserEntity findByUsername(String username);

    /**
     * 查询所有用户
     * @return
     */
    List<SysUserEntity> findAll();
    /**
     * 通过代理商id查询所有用户
     *
     * @param subDeptIdList 代理商id集合
     * @return 所有符合条件的用户id
     */
    List<Long> findUserByDeptIds(List<Long> subDeptIdList);

    /**
     * 修改用户状态
     * @param username 账户
     * @return 1
     */
    int updateUserStatus(String username,Integer status);

}
