package com.xiaohe.mapshow.modules.changelist.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.config.annotation.DemoIntercept;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.modules.changelist.entity.ChangeList;
import com.xiaohe.mapshow.modules.changelist.entity.QueryChangeList;
import com.xiaohe.mapshow.modules.changelist.service.ChangeListService;
import com.xiaohe.mapshow.modules.device.service.DeviceService;
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
 * ChangeListController层
 *
 * @author hzh
 * @since 2019-04-08
 */
@RestController
@RequestMapping(value = "/changeLists")
public class ChangeListController {
    private static Logger log = LoggerFactory.getLogger(ChangeListController.class);
    @Autowired
    private ChangeListService changeListService;
    @Autowired
    private DeviceService deiceService;

    /**
     * 保存对象<br/>
     *
     * @param changeList 对象
     */
    @PostMapping
//    @RequiresPermissions("sys:change:save")
    @LogOperate(description = "新增ChangeList")
    @DemoIntercept
    public Result save(@Validated @RequestBody ChangeList changeList) {
        Result result = new Result();
        try {
            changeListService.save(changeList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param changeList
     * @return
     */
    @PutMapping(value = "/{id}")
//    @RequiresPermissions("sys:change:update")
    @LogOperate(description = "更新ChangeList")
    @DemoIntercept
    public Result updateBanner(@Validated @RequestBody ChangeList changeList) {
        Result result = new Result();
        try {
            boolean exists = changeListService.existsById(changeList.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            changeListService.save(changeList);
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
//    @RequiresPermissions("sys:change:delete")
    @LogOperate(description = "删除ChangeList")
    @DemoIntercept
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            changeListService.deleteInBatch(StringUtil.stringToIntegerList(ids));
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
     * @return ChangeList 对象
     */
    @GetMapping(value = "/{id}")
//    @RequiresPermissions("sys:change:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(changeListService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<ChangeList> 对象
     */
    @PostMapping(value = "/pages")
//    @RequiresPermissions("sys:change:list")
    public Result findByPage(@RequestBody QueryChangeList queryChangeList) {

        CommonResult result = new CommonResult();
        try {
            int page = queryChangeList.getPage();
            int pageSize = queryChangeList.getPageSize();
            Page<ChangeList> all = changeListService.findAll(page - 1, pageSize, queryChangeList);
            PageBean<ChangeList> pageBean = new PageBean<>();
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

     * @throws IOException IO异常
     */
    @GetMapping(value = "/export")
    public void prjExport(HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            //获取数据库数据
            Workbook workbook;
            List<ChangeList> list = changeListService.getAll();
            workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "滤芯管理"),
                    ChangeList.class, list);
            //保存在本地
            String filename = "滤芯管理" + System.currentTimeMillis();
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
