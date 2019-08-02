
package com.xiaohe.mapshow.modules.sys.controller;

import com.xiaohe.mapshow.config.annotation.DemoIntercept;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.config.validate.ValidatorUtils;
import com.xiaohe.mapshow.modules.sys.entity.SysDeptEntity;
import com.xiaohe.mapshow.modules.sys.entity.SysRoleEntity;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.modules.sys.service.SysRoleDeptService;
import com.xiaohe.mapshow.modules.sys.service.SysRoleMenuService;
import com.xiaohe.mapshow.modules.sys.service.SysRoleService;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;
    @Autowired
    private SysDeptService sysDeptService;


    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    @LogOperate(description = "角色列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    @LogOperate(description = "角色列表")
    public R select() {
        List<SysRoleEntity> list = sysRoleService.selectList(null);

        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    @LogOperate(description = "角色信息")
    public R info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.selectById(roleId);

        //添加部门名称
        SysDeptEntity sysDeptEntity = sysDeptService.selectById(role.getDeptId());
        role.setDeptName(sysDeptEntity==null?null:sysDeptEntity.getName());

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的部门
        List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
        role.setDeptIdList(deptIdList);


        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    @LogOperate(description = "保存角色")
    @DemoIntercept
    public R save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        sysRoleService.save(role);

        return R.ok();
    }

    /**
     * 修改角色
     */

    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    @LogOperate(description = "修改角色")
    @DemoIntercept
    public R update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        sysRoleService.update(role);

        return R.ok();
    }

    /**
     * 删除角色
     */

    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    @LogOperate(description = "删除角色")
    @DemoIntercept
    public R delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);

        return R.ok();
    }
}
