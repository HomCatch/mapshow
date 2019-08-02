package com.xiaohe.mapshow.modules.waterstatic.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.waterstatic.service.WaterStaticService;
import com.xiaohe.mapshow.modules.waterstatic.entity.WaterStatic;
import com.xiaohe.mapshow.modules.waterstatic.entity.QueryWaterStatic;
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
 * WaterStaticController层
 *
 * @author wzq
 * @since 2019-06-04
 */
@RestController
@RequestMapping(value = "/waterStatics")
public class WaterStaticController {
    private static Logger log = LoggerFactory.getLogger(WaterStaticController.class);
    @Autowired
    private WaterStaticService waterStaticService;


    /**
     * 保存对象<br/>
     *
     * @param waterStatic 对象
     */
    @PostMapping
//@RequiresPermissions("sys:waterStatic:save")
    @LogOperate(description = "新增WaterStatic")
    public Result save(@Validated @RequestBody WaterStatic waterStatic) {
        Result result = new Result();
        try {
            waterStaticService.save(waterStatic);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param waterStatic
     * @return
     */
    @PutMapping(value = "/{id}")
//@RequiresPermissions("sys:waterStatic:update")
    @LogOperate(description = "更新WaterStatic")
    public Result update(@Validated @RequestBody WaterStatic waterStatic) {
        Result result = new Result();
        try {
            boolean exists = waterStaticService.existsById(waterStatic.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            waterStaticService.save(waterStatic);
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
//@RequiresPermissions("sys:waterStatic:delete")
    @LogOperate(description = "删除WaterStatic")
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            waterStaticService.deleteInBatch(StringUtil.stringToIntegerList(ids));
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
     * @return WaterStatic 对象
     */
    @GetMapping(value = "/{id}")
//@RequiresPermissions("sys:waterStatic:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(waterStaticService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<WaterStatic> 对象
     */
    @PostMapping(value = "/pages")
//@RequiresPermissions("sys:waterStatic:list")
    public Result findByPage(@RequestBody QueryWaterStatic queryWaterStatic) {

        CommonResult result = new CommonResult();
        try {
            int page = queryWaterStatic.getPage();
            int pageSize = queryWaterStatic.getPageSize();
            Page<WaterStatic> all = waterStaticService.findAll(page - 1, pageSize, queryWaterStatic);
            PageBean<WaterStatic> pageBean = new PageBean<>();
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
            List<WaterStatic> list = waterStaticService.findAllById(StringUtil.stringToIntegerList(ids));
            workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "water_static"), WaterStatic.class, list);
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
