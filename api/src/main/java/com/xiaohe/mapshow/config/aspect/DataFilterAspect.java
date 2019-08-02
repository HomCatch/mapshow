package com.xiaohe.mapshow.config.aspect;


import com.xiaohe.mapshow.config.validate.XException;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.modules.sys.service.SysRoleDeptService;
import com.xiaohe.mapshow.modules.sys.service.SysUserRoleService;
import com.xiaohe.mapshow.modules.sys.shiro.ShiroUtils;
import com.xiaohe.mapshow.modules.untils.Constant;
import com.xiaohe.mapshow.modules.untils.annotation.DataFilter;
import com.xiaohe.mapshow.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Aspect
public class DataFilterAspect {
    public static String apendSql = "";
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    @Pointcut("@annotation(com.xiaohe.mapshow.modules.untils.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) throws Throwable {
        Object params = point.getArgs()[0];
        if(params != null && params instanceof Map){
            SysUserEntity user = ShiroUtils.getUserEntity();

            //如果不是超级管理员，则进行数据过滤
            if(user.getUserId() != Constant.SUPER_ADMIN){
                Map map = (Map)params;
                map.put(Constant.SQL_FILTER, getSQLFilter(user, point));
            }

            return ;
        }else if(params != null && params instanceof Object){
            SysUserEntity user = ShiroUtils.getUserEntity();

            //如果不是超级管理员，则进行数据过滤
            if(user.getUserId() != Constant.SUPER_ADMIN){
                 getSQLFilter(user, point);
            }

            return ;

        }

        throw new XException("数据权限接口，只能是Map类型参数，且不能为NULL");
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getSQLFilter(SysUserEntity user, JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataFilter dataFilter = signature.getMethod().getAnnotation(DataFilter.class);
        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if(StringUtils.isNotBlank(tableAlias)){
            tableAlias +=  ".";
        }

        //部门ID列表
        Set<Long> deptIdList = new HashSet<>();

        //用户角色对应的部门ID列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
        if(roleIdList.size() > 0){
            List<Long> userDeptIdList = sysRoleDeptService.queryDeptIdList(roleIdList.toArray(new Long[roleIdList.size()]));
            deptIdList.addAll(userDeptIdList);
        }

        //用户子部门ID列表
        if(dataFilter.subDept()){
            List<Long> subDeptIdList = sysDeptService.getSubDeptIdList(user.getDeptId());
            deptIdList.addAll(subDeptIdList);
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        if(deptIdList.size() > 0){
            sqlFilter.append(tableAlias).append(dataFilter.deptId()).append(" in(").append(StringUtils.join(deptIdList, ",")).append(")");
        }

        //没有本部门数据权限，也能查询本人数据
        if(dataFilter.user()){
            if(deptIdList.size() > 0){
                sqlFilter.append(" or ");
            }
            sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(user.getUserId());
        }

        sqlFilter.append(")");
        apendSql = sqlFilter.toString();
        return apendSql;
    }

    /**
     * 转化部门列表
     * @param apendSql
     * @return
     */
    public static List<Integer> sqlToDeptList(String apendSql ) throws Exception{
        List<Integer> deptIdList=null;
        List<Integer> list=new LinkedList<>();
        if(apendSql.contains("=")){
            String substring = apendSql.substring(apendSql.indexOf("="), apendSql.indexOf("）"));
            list.add(Integer.valueOf(substring));
        }else if(apendSql.contains("in")) {
            String substring = apendSql.substring(0, apendSql.lastIndexOf(")"));
            String substring2 = apendSql.substring(substring.indexOf("in(") + 3, substring.lastIndexOf(")"));
            deptIdList.add(15);
            deptIdList = StringUtil.stringToIntegerList(substring2);
        }
        return deptIdList==null?list:deptIdList;
    }
}

