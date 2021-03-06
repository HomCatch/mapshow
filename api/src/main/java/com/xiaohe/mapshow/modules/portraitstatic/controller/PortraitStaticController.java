package com.xiaohe.mapshow.modules.portraitstatic.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.portraitstatic.service.PortraitStaticService;
import com.xiaohe.mapshow.modules.portraitstatic.entity.PortraitStatic;
import com.xiaohe.mapshow.modules.portraitstatic.entity.QueryPortraitStatic;
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
 * PortraitStaticController层
 *
 * @author wzq
 * @since 2019-06-04
 */
@RestController
@RequestMapping(value = "/portraitStatics")
public class PortraitStaticController {
    private static Logger log = LoggerFactory.getLogger(PortraitStaticController.class);
    @Autowired
    private PortraitStaticService portraitStaticService;


    /**
     * 保存对象<br/>
     *
     * @param portraitStatic 对象
     */
    @PostMapping
//@RequiresPermissions("sys:portraitStatic:save")
    @LogOperate(description = "新增PortraitStatic")
    public Result save(@Validated @RequestBody PortraitStatic portraitStatic) {
        Result result = new Result();
        try {
            portraitStaticService.save(portraitStatic);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param portraitStatic
     * @return
     */
    @PutMapping(value = "/{id}")
//@RequiresPermissions("sys:portraitStatic:update")
    @LogOperate(description = "更新PortraitStatic")
    public Result update(@Validated @RequestBody PortraitStatic portraitStatic) {
        Result result = new Result();
        try {
            boolean exists = portraitStaticService.existsById(portraitStatic.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            portraitStaticService.save(portraitStatic);
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
//@RequiresPermissions("sys:portraitStatic:delete")
    @LogOperate(description = "删除PortraitStatic")
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            portraitStaticService.deleteInBatch(StringUtil.stringToIntegerList(ids));
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
     * @return PortraitStatic 对象
     */
    @GetMapping(value = "/{id}")
//@RequiresPermissions("sys:portraitStatic:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(portraitStaticService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<PortraitStatic> 对象
     */
    @PostMapping(value = "/pages")
//    @RequiresPermissions("sys:portraitStatic:list")
    public Result findByPage(@RequestBody QueryPortraitStatic queryPortraitStatic) {

        CommonResult result = new CommonResult();
        try {
            int page = queryPortraitStatic.getPage();
            int pageSize = queryPortraitStatic.getPageSize();
            Page<PortraitStatic> all = portraitStaticService.findAll(page - 1, pageSize, queryPortraitStatic);
            PageBean<PortraitStatic> pageBean = new PageBean<>();
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
            List<PortraitStatic> list = portraitStaticService.findAllById(StringUtil.stringToIntegerList(ids));
            workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "portrait_static"), PortraitStatic.class, list);
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
