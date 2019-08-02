package com.xiaohe.mapshow.modules.userinfo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.config.annotation.DemoIntercept;
import com.xiaohe.mapshow.modules.device.entity.Device;
import com.xiaohe.mapshow.modules.userinfo.entity.OrderDevice;
import com.xiaohe.mapshow.modules.userinfo.entity.QueryOrderDevice;
import com.xiaohe.mapshow.modules.userinfo.entity.QueryUserInfo;
import com.xiaohe.mapshow.modules.userinfo.entity.UserInfo;
import com.xiaohe.mapshow.modules.userinfo.service.OrderDeviceService;
import com.xiaohe.mapshow.modules.userinfo.service.UserInfoService;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息表 HxclUserInfoController层
 *
 * @author gmq
 * @since 2019-04-01
 */
@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController {
    private static Logger log = LoggerFactory.getLogger(UserInfoController.class);
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private OrderDeviceService orderDeviceService;

    /**
     * 保存对象<br/>
     *
     * @param hxclUserInfo 对象
     */
    @PostMapping
    @DemoIntercept
    public Result save(@Validated @RequestBody UserInfo hxclUserInfo) {
        Result result = new Result();
        try {
            userInfoService.save(hxclUserInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param hxclUserInfo
     * @return
     */
    @PutMapping(value = "/{id}")
    @DemoIntercept
    public Result updateBanner(@Validated @RequestBody UserInfo hxclUserInfo) {
        Result result = new Result();
        try {
            boolean exists = userInfoService.existsById(hxclUserInfo.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            userInfoService.save(hxclUserInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2002, "修改失败");
        }
        return result.ok();
    }


    /**
     * 通过id查找对象
     *
     * @param id id
     * @return UserInfo 对象
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult commonResult = new CommonResult();
        try {
            commonResult.setDatas(userInfoService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return commonResult.error(2004, "不存在");
        }
        return commonResult.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<UserInfo> 对象
     */
    @PostMapping(value = "/pages")
//    @RequiresPermissions(value = "sys:find:list")
    public CommonResult findByPage(@RequestBody QueryUserInfo queryHxclUserInfo) {

        CommonResult result = new CommonResult();
        result.setRet(0);
        result.setMsg("success");
        try {
            int page = queryHxclUserInfo.getPage();
            int pageSize = queryHxclUserInfo.getPageSize();
            Page<UserInfo> all = userInfoService.findAll(page - 1, pageSize, queryHxclUserInfo);
            PageBean<UserInfo> pageBean = new PageBean<>();
            if (all == null || 0 == all.getTotalElements()) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result;
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setRet(2005);
            result.setMsg("查询出错");
            return result;
        }
        return result;
    }


    /**
     * 查看设备
     * <p>
     * CommonResult
     */
    @PostMapping(value = "/findDevInfo")
    @RequiresPermissions(value = "sys:find:dev")
    public CommonResult findDevInfo(@RequestBody QueryOrderDevice queryOrderDevice) {
        CommonResult result = new CommonResult();
        result.ok();
        try {
            int page = queryOrderDevice.getPage();
            int pageSize = queryOrderDevice.getPageSize();
            Page<OrderDevice> all = orderDeviceService.findAll(page - 1, pageSize, queryOrderDevice);
            PageBean<OrderDevice> pageBean = new PageBean<>();
            if (all == null || 0 == all.getTotalElements()) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result;
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.setRet(2005);
            result.setMsg("查询出错");
            return result;
        }
        return result;
    }

    /**
     * EXCEL导出
     *
     * @param response re
     * @throws IOException IO异常
     */
    @GetMapping(value = "/export")

    public void prjExport(HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            //获取数据库数据
            Workbook workbook;
//            List<UserInfo> list = userInfoService.findAllById(StringUtil.stringToIntegerList(ids));
            List<UserInfo> list = userInfoService.getAll();
            workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "user_info"), UserInfo.class, list);
            //保存在本地
            String filename = "用户管理" + System.currentTimeMillis();
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");
            response.setContentType("application/msexcel");
            if (workbook != null) {
                workbook.write(os);
                log.info("导出成功.....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }
}
