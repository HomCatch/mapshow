package com.xiaohe.mapshow.modules.filterplan.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.common.BatchLongReqVo;
import com.xiaohe.mapshow.config.annotation.DemoIntercept;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.modules.sys.controller.AbstractController;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.filterplan.service.FilterPlanService;
import com.xiaohe.mapshow.modules.filterplan.entity.FilterPlan;
import com.xiaohe.mapshow.modules.filterplan.entity.QueryFilterPlan;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import java.util.ArrayList;

/**
 * FilterPlanController层
 *
 * @author gmq
 * @since 2019-03-29
 */
@RestController
@RequestMapping(value = "/filterPlans")
public class FilterPlanController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(FilterPlanController.class);
    @Autowired
    private FilterPlanService filterPlanService;


    /**
     * 保存对象<br/>
     *
     * @param filterPlan 对象
     */
    @PostMapping
    @DemoIntercept
//    @RequiresPermissions("sys:filterPlan:save")
    @LogOperate(description = "新增FilterPlan")
    public Result save(@Validated @RequestBody FilterPlan filterPlan) {
        Result result = new Result();
        try {
            filterPlanService.save(filterPlan);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param filterPlan
     * @return
     */
    @PutMapping(value = "/{id}")
    @DemoIntercept
//    @RequiresPermissions("sys:filterPlan:update")
    @LogOperate(description = "更新FilterPlan")
    public Result updateBanner(@Validated @RequestBody FilterPlan filterPlan) {
        Result result = new Result();
        try {
            boolean exists = filterPlanService.existsById(filterPlan.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            filterPlanService.save(filterPlan);
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
    @DemoIntercept
//    @RequiresPermissions("sys:filterPlan:delete")
    @LogOperate(description = "删除FilterPlan")
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            filterPlanService.deleteInBatch(StringUtil.stringToIntegerList(ids));
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
     * @return FilterPlan 对象
     */
    @GetMapping(value = "/{id}")
//    @RequiresPermissions("sys:filterPlan:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(filterPlanService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<FilterPlan> 对象
     */
    @PostMapping(value = "/pages")
//    @RequiresPermissions("sys:filterPlan:list")
    public Result findByPage(@RequestBody QueryFilterPlan queryFilterPlan) {

        CommonResult result = new CommonResult();
        try {
            int page = queryFilterPlan.getPage();
            int pageSize = queryFilterPlan.getPageSize();
            queryFilterPlan.setUserId(getUserId().intValue());
            Page<FilterPlan> all = filterPlanService.findAll(page - 1, pageSize, queryFilterPlan);
            PageBean<FilterPlan> pageBean = new PageBean<>();
            if (all == null||0==all.getTotalElements()) {
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
     * @param batchReqVo     参数
     * @throws IOException IO异常
     */
    @PostMapping(value = "/export")
    @LogOperate(description = "批量导出滤芯就计划")
    public void prjExport(HttpServletResponse response, @RequestBody BatchLongReqVo batchReqVo) throws IOException {
        OutputStream os = null;
        try {
            //获取数据库数据
            Workbook workbook;
            List<Long> ids = batchReqVo.getIds();
            List<Integer> integers=new LinkedList<>();
            for (Long id : ids) {
                integers.add(id.intValue());
            }
            List<FilterPlan> list = filterPlanService.findAllById(integers);
            workbook = ExcelExportUtil.exportExcel(new ExportParams(), FilterPlan.class, list);
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
    /**
     * 导入滤芯计划
     *
     * @param file ex文件
     * @return Result
     */
    @PostMapping("/import")
    @LogOperate(description = "导入滤芯计划")
    public Result addFilterInfo(@RequestParam("file") MultipartFile file) {
        Result result = new Result();
        try {
            result= filterPlanService.batchImport(file);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new Result().error(1008,"EXCEL导入失败");
        }
        return result;
    }

    /**
     * 下载模板
     */
    @GetMapping("/download")
    @LogOperate(description = "下载滤芯计划模板")
    public Result downloadTemplate() {
        try {
            filterPlanService.downloadTemplate();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new Result().error(1008,"EXCEL导出失败");
        }
        return new Result().ok();
    }
}
