package com.xiaohe.mapshow.modules.history.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Maps;
import com.xiaohe.mapshow.config.annotation.DemoIntercept;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
import com.xiaohe.mapshow.modules.history.entity.*;
import com.xiaohe.mapshow.modules.sys.controller.AbstractController;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.history.service.HistoryService;
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
import java.util.*;

/**
 * HistoryController层
 *
 * @author gmq
 * @since 2019-03-29
 */
@RestController
@RequestMapping(value = "/historys")
public class HistoryController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(HistoryController.class);
    private final HistoryService historyService;
    private final SysDeptService sysDeptService;

    public HistoryController(HistoryService historyService, SysDeptService sysDeptService) {
        this.historyService = historyService;
        this.sysDeptService = sysDeptService;
    }


    /**
     * 保存对象<br/>
     *
     * @param history 对象
     */
    @PostMapping
//    @RequiresPermissions("sys:history:save")
    @LogOperate(description = "新增History")
    @DemoIntercept
    public Result save(@Validated @RequestBody History history) {
        Result result = new Result();
        try {
            history.setBindedUserId(getUserId().intValue());
            historyService.save(history);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param history
     * @return
     */
    @PutMapping(value = "/{id}")
//    @RequiresPermissions("sys:history:update")
    @LogOperate(description = "更新History")
    @DemoIntercept
    public Result updateBanner(@Validated @RequestBody History history) {
        Result result = new Result();
        try {
            boolean exists = historyService.existsById(history.getHistoryId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            historyService.save(history);
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
//    @RequiresPermissions("sys:history:delete")
    @LogOperate(description = "删除History")
    @DemoIntercept
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            historyService.deleteInBatch(StringUtil.stringToIntegerList(ids));
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
     * @return History 对象
     */
    @GetMapping(value = "/{id}")
//    @RequiresPermissions("sys:history:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(historyService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<History> 对象
     */
    @PostMapping(value = "/pages")
//    @RequiresPermissions("sys:history:list")
    public Result findByPage(@RequestBody QueryHistory queryHistory) {
        CommonResult result = new CommonResult();
        try {
            int page = queryHistory.getPage();
            int pageSize = queryHistory.getPageSize();
            //赋值用户ID
            queryHistory.setBindedUserId(getUserId().intValue());
            Page<History> all = historyService.findAll(page - 1, pageSize, queryHistory);
            PageBean<History> pageBean = new PageBean<>();
            if (all == null || 0 == all.getTotalElements()) {
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
     * @throws IOException IO异常
     */
    @GetMapping(value = "/export")
    public void prjExport(HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            //获取数据库数据
            Workbook workbook;
            List<History> list =historyService.getAll();

            workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "history"), History.class, list);
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
     * 最近15天记录
     *
     * @return Page<History> 对象
     */
    @PostMapping(value = "/record")
    public Result getRecord(@RequestBody DashboardProperty dashboardProperty) {

        CommonResult result = new CommonResult();
        try {
            //赋值用户ID
            List<HalfMonthRecord> record = historyService.getRecord(dashboardProperty);
            result.setDatas(record);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        logger.info("最近15天记录");
        return result.ok();
    }

    /**
     * 最近7天TDS记录
     *
     * @return Page<History> 对象
     */
    @PostMapping(value = "/tdsRecord")
    public Result getTdsRecord(@RequestBody DashboardProperty dashboardProperty) {

        CommonResult result = new CommonResult();
        try {
            List<HistoryBrokenLine> record = historyService.getTdsRecord(dashboardProperty);
            result.setDatas(record);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        logger.info("最近7天TDS记录");
        return result.ok();
    }

    /**
     * 最近7天浊度记录
     *
     * @return Page<History> 对象
     */
    @PostMapping(value = "/tbdtRecord")
    public Result getTbdtRecord(@RequestBody DashboardProperty dashboardProperty) {

        CommonResult result = new CommonResult();
        try {
            //赋值用户ID
            List<HistoryBrokenLine> record = historyService.getTbdtRecord(dashboardProperty);
            result.setDatas(record);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        logger.info("最近7天浊度记录");
        return result.ok();
    }

    /**
     * 最近7天色度记录
     *
     * @return Page<History> 对象
     */
    @PostMapping(value = "/colorRecord")
    public Result getColorRecord(@RequestBody DashboardProperty dashboardProperty) {

        CommonResult result = new CommonResult();
        try {
            //赋值用户ID
            List<HistoryBrokenLine> record = historyService.getColorRecord(dashboardProperty);
            result.setDatas(record);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        logger.info("最近7天色度记录");
        return result.ok();
    }

}
