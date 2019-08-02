
package com.xiaohe.mapshow.modules.sys.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaohe.mapshow.modules.sys.dao.SysUserDao;
import com.xiaohe.mapshow.modules.sys.entity.SysDeptEntity;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.entity.SysUserRoleEntity;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.modules.sys.service.SysUserRoleService;
import com.xiaohe.mapshow.modules.sys.service.SysUserService;
import com.xiaohe.mapshow.modules.sys.shiro.ShiroUtils;
import com.xiaohe.mapshow.modules.untils.Constant;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.Query;
import com.xiaohe.mapshow.modules.untils.annotation.DataFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 */
@Service
@DS("#session.tenantName")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public List<Long> findUserByDeptIds(List<Long> subDeptIdList) {
        return baseMapper.findUserByDeptIds(subDeptIdList);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        //如果是体验用户 只看到自己账户
        SysUserEntity user = ShiroUtils.getUserEntity();
        if(1==user.getDemoFlag()){
            username = user.getUsername();
        }
        Page<SysUserEntity> page = this.selectPage(
                new Query<SysUserEntity>(params).getPage(),
                new EntityWrapper<SysUserEntity>()
                        .like(StringUtils.isNotBlank(username),"username", username)
                        .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );
        for(SysUserEntity sysUserEntity : page.getRecords()){
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
            sysUserEntity.setDeptName(sysDeptEntity==null?null:sysDeptEntity.getName());
        }

        return new PageUtils(page);
    }

    @Override
    public int save(SysUserEntity user) {
        user.setCreateTime(new Date());

        //TODO 暂时去掉加盐
        //sha256加密
        /*
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		*/

        //检查账户名是否存在
        SysUserEntity byUsername = this.findByUsername(user.getUsername());
        if (byUsername != null) {
            return 1;
        }
        this.insert(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
//        //保存用户与设备关系
//        deviceService.saveOrUpdate(user.getUserId(), user.getDeviceIdList());
        return 0;
    }

    @Override
    public int saveSys(SysUserEntity user) {
//        user.setCreateTime(new Date());

        //TODO 暂时去掉加盐
        //sha256加密
        /*
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		*/

        //检查账户名是否存在
        SysUserEntity byUsername = this.findByUsername(user.getUsername());
        if (byUsername != null) {
            return 1;
        }

        sysUserDao.saveOrUpdate(user);
        //保存管理员角色为1
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        sysUserRoleEntity.setUserId(user.getUserId());
        sysUserRoleEntity.setRoleId(1L);
        sysUserRoleService.saveOrUpdate(sysUserRoleEntity);
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            //TODO 暂时去掉加盐
            //user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        }
        SysUserEntity byUsername = this.findByUsername(user.getUsername());
        if (byUsername != null) {
            int i = byUsername.getUserId().compareTo(user.getUserId());
            if (i != 0) {
                return 1;
            }
        }
        this.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
//        //保存用户与设备关系
//        deviceService.saveOrUpdate(user.getUserId(), user.getDeviceIdList());
        return 0;
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

    @Override
    public SysUserEntity findByUsername(String username) {
        return baseMapper.findByUsername(username);
    }

    /**
     * 查询所有经销商（用户）
     *
     * @return
     */
    @Override
    public List<SysUserEntity> findAll() {
        return baseMapper.selectList(new EntityWrapper<>());
    }

    @Override
    public int updateUserStatus(String username,Integer status) {
        return baseMapper.updateUserStatus(username,status);
    }
}
