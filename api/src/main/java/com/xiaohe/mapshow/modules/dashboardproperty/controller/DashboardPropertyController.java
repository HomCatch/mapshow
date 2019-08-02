package com.xiaohe.mapshow.modules.dashboardproperty.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.dashboardproperty.service.DashboardPropertyService;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.QueryDashboardProperty;
import org.apache.commons.lang.StringUtils;
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

/**
 * 仪表盘属性 DashboardPropertyController层
 *
 * @author gmq
 * @since 2019-04-01
 */
@RestController
@RequestMapping(value = "/dashboardPropertys")
public class DashboardPropertyController {
    private static Logger log = LoggerFactory.getLogger(DashboardPropertyController.class);
    private final DashboardPropertyService dashboardPropertyService;

    @Autowired
    public DashboardPropertyController(DashboardPropertyService dashboardPropertyService) {
        this.dashboardPropertyService = dashboardPropertyService;
    }


    /**
     * 保存对象<br/>
     *
     * @param dashboardProperty 对象
     */
    @PostMapping
//    @RequiresPermissions("sys:dashboardProperty:save")
    @LogOperate(description = "新增DashboardProperty")
    public Result save(@Validated @RequestBody DashboardProperty dashboardProperty) {
        Result result = new Result();
        try {
            dashboardPropertyService.save(dashboardProperty);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param dashboardProperty
     * @return
     */
    @PutMapping(value = "/{id}")
//    @RequiresPermissions("sys:dashboardProperty:update")
    @LogOperate(description = "更新DashboardProperty")
    public Result updateBanner(@Validated @RequestBody DashboardProperty dashboardProperty) {
        Result result = new Result();
        try {
            boolean exists = dashboardPropertyService.existsById(dashboardProperty.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            dashboardPropertyService.save(dashboardProperty);
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
//    @RequiresPermissions("sys:dashboardProperty:delete")
    @LogOperate(description = "删除DashboardProperty")
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            dashboardPropertyService.deleteInBatch(StringUtil.stringToIntegerList(ids));
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
     * @return DashboardProperty 对象
     */
    @GetMapping(value = "/{id}")
//    @RequiresPermissions("sys:dashboardProperty:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(dashboardPropertyService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<DashboardProperty> 对象
     */
    @PostMapping(value = "/pages")
//    @RequiresPermissions("sys:dashboardProperty:list")
    public Result findByPage(@RequestBody QueryDashboardProperty queryDashboardProperty) {

        CommonResult result = new CommonResult();
        try {
            int page = queryDashboardProperty.getPage();
            int pageSize = queryDashboardProperty.getPageSize();
            Page<DashboardProperty> all = dashboardPropertyService.findAll(page - 1, pageSize, queryDashboardProperty);
            PageBean<DashboardProperty> pageBean = new PageBean<>();
            if (all == null||0==all.getTotalElements()) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            List<DashboardProperty> content = all.getContent();
            for (DashboardProperty dashboardProperty : content) {
                if(StringUtils.isNotBlank(dashboardProperty.getStyleData())){
                    dashboardProperty.setStyleJson(JSON.parse(dashboardProperty.getStyleData()));
                }
            }
            pageBean.setList(content);
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        return result.ok();
    }

    /**
     * 查询所有
     *
     * @return Page<DashboardProperty> 对象
     */
    @GetMapping(value = "/list")
//    @RequiresPermissions("sys:dashboardProperty:list")
    public Result findAll() {

        CommonResult result = new CommonResult();
        try {

            List<DashboardProperty> list = dashboardPropertyService.findAll();
            result.setDatas(list);
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
            List<DashboardProperty> list = dashboardPropertyService.findAllById(StringUtil.stringToIntegerList(ids));
            workbook = ExcelExportUtil.exportExcel(new ExportParams("仪表盘属性表", "dashboard_property"),
                    DashboardProperty.class, list);
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
