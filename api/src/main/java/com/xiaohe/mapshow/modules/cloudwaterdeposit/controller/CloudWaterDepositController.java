package com.xiaohe.mapshow.modules.cloudwaterdeposit.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.config.annotation.DemoIntercept;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.service.CloudWaterDepositService;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.entity.CloudWaterDeposit;
import com.xiaohe.mapshow.modules.cloudwaterdeposit.entity.QueryCloudWaterDeposit;
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
 * CloudWaterDepositController层
 *
 * @author gmq
 * @since 2019-04-19
 */
@RestController
@RequestMapping(value = "/cloudWaterDeposits")
public class CloudWaterDepositController {
    private static Logger log = LoggerFactory.getLogger(CloudWaterDepositController.class);
    @Autowired
    private CloudWaterDepositService cloudWaterDepositService;


    /**
     * 保存对象<br/>
     *
     * @param cloudWaterDeposit 对象
     */
    @PostMapping
    @RequiresPermissions("sys:cloudWaterDeposit:save")
    @LogOperate(description = "新增CloudWaterDeposit")
    @DemoIntercept
    public Result save(@Validated @RequestBody CloudWaterDeposit cloudWaterDeposit) {
        Result result = new Result();
        try {
            cloudWaterDepositService.save(cloudWaterDeposit);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param cloudWaterDeposit
     * @return
     */
    @PutMapping(value = "/{id}")
    @DemoIntercept
    @RequiresPermissions("sys:cloudWaterDeposit:update")
    @LogOperate(description = "更新CloudWaterDeposit")
    public Result update(@Validated @RequestBody CloudWaterDeposit cloudWaterDeposit) {
        Result result = new Result();
        try {
            boolean exists = cloudWaterDepositService.existsById(cloudWaterDeposit.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            cloudWaterDepositService.save(cloudWaterDeposit);
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
    @RequiresPermissions("sys:cloudWaterDeposit:delete")
    @LogOperate(description = "删除CloudWaterDeposit")
    @DemoIntercept
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            cloudWaterDepositService.deleteInBatch(StringUtil.stringToIntegerList(ids));
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
     * @return CloudWaterDeposit 对象
     */
    @GetMapping(value = "/{id}")
    @RequiresPermissions("sys:cloudWaterDeposit:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(cloudWaterDepositService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<CloudWaterDeposit> 对象
     */
    @PostMapping(value = "/pages")
    @RequiresPermissions("sys:cloudWaterDeposit:list")
    public Result findByPage(@RequestBody QueryCloudWaterDeposit queryCloudWaterDeposit) {

        CommonResult result = new CommonResult();
        try {
            int page = queryCloudWaterDeposit.getPage();
            int pageSize = queryCloudWaterDeposit.getPageSize();
            Page<CloudWaterDeposit> all = cloudWaterDepositService.findAll(page - 1, pageSize, queryCloudWaterDeposit);
            PageBean<CloudWaterDeposit> pageBean = new PageBean<>();
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
     * @throws IOException IO异常
     */
    @GetMapping(value = "/export")
    @LogOperate(description = "导出押金订单")
    public void prjExport(HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            //获取数据库数据
            Workbook workbook;
            List<CloudWaterDeposit> list = cloudWaterDepositService.getAll();
            workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "押金订单"), CloudWaterDeposit.class, list);
            //保存在本地
            String filename = "押金订单" + System.currentTimeMillis();
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
