package com.xiaohe.mapshow.modules.devicestatic.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.devicestatic.service.DeviceStaticService;
import com.xiaohe.mapshow.modules.devicestatic.entity.DeviceStatic;
import com.xiaohe.mapshow.modules.devicestatic.entity.QueryDeviceStatic;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.poi.ss.usermodel.Workbook;
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

import java.util.ArrayList;

/**
 * DeviceStaticController层
 *
 * @author wzq
 * @since 2019-06-04
 */
@RestController
@RequestMapping(value = "/deviceStatics")
public class DeviceStaticController {
    private static Logger log = LoggerFactory.getLogger(DeviceStaticController.class);
    @Autowired
    private DeviceStaticService deviceStaticService;


    /**
     * 保存对象<br/>
     *
     * @param deviceStatic 对象
     */
    @PostMapping
//    @RequiresPermissions("sys:deviceStatic:save")
    @LogOperate(description = "新增DeviceStatic")
    public Result save(@Validated @RequestBody DeviceStatic deviceStatic) {
        Result result = new Result();
        try {
            deviceStaticService.save(deviceStatic);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param deviceStatic
     * @return
     */
    @PutMapping(value = "/{id}")
//    @RequiresPermissions("sys:deviceStatic:update")
    @LogOperate(description = "更新DeviceStatic")
    public Result update(@Validated @RequestBody DeviceStatic deviceStatic) {
        Result result = new Result();
        try {
            boolean exists = deviceStaticService.existsById(deviceStatic.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            deviceStaticService.save(deviceStatic);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2002, "修改失败");
        }
        return result.ok();
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids id集合
     */
    @DeleteMapping(value = "/{id}")
//    @RequiresPermissions("sys:deviceStatic:delete")
    @LogOperate(description = "删除DeviceStatic")
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            deviceStaticService.deleteInBatch(StringUtil.stringToIntegerList(ids));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2003, "删除失败");
        }
        return result.ok();
    }


    /**
     * 通过id查找对象
     *
     * @param id id
     * @return DeviceStatic 对象
     */
    @GetMapping(value = "/{id}")
//    @RequiresPermissions("sys:deviceStatic:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(deviceStaticService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<DeviceStatic> 对象
     */
    @PostMapping(value = "/pages")
//    @RequiresPermissions("sys:deviceStatic:list")
    public Result findByPage(@RequestBody QueryDeviceStatic queryDeviceStatic) {

        CommonResult result = new CommonResult();
        try {
            int page = queryDeviceStatic.getPage();
            int pageSize = queryDeviceStatic.getPageSize();
            Page<DeviceStatic> all = deviceStaticService.findAll(page - 1, pageSize, queryDeviceStatic);
            PageBean<DeviceStatic> pageBean = new PageBean<>();
            if (all == null) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        return result.ok();
    }

    /**
     * EXCEL导出
     *
     * @param response re
     * @param ids      id逗号隔开
     * @throws IOException IO异常
     */
    @GetMapping(value = "/export/{id}")
    public void prjExport(HttpServletResponse response, @PathVariable("id") String ids) throws IOException {
        OutputStream os = null;
        try {
            //获取数据库数据
            Workbook workbook;
            List<DeviceStatic> list = deviceStaticService.findAllById(StringUtil.stringToIntegerList(ids));
            workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "device_static"), DeviceStatic.class, list);
            //保存在本地
            String filename = "ter_daily_report" + System.currentTimeMillis();
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
            log.info(e.getMessage());
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }
}
