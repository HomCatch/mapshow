package com.xiaohe.experienceUser.modules.member.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.experienceUser.config.aspect.LogOperate;
import com.xiaohe.experienceUser.utils.CommonResult;
import com.xiaohe.experienceUser.utils.PageBean;
import com.xiaohe.experienceUser.utils.Result;
import com.xiaohe.experienceUser.utils.StringUtil;
import com.xiaohe.experienceUser.modules.member.service.MemberService;
import com.xiaohe.experienceUser.modules.member.entity.Member;
import com.xiaohe.experienceUser.modules.member.entity.QueryMember;
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

/**
 * MemberController层
 *
 * @author wzq
 * @since 2019-07-10
 */
@RestController
@RequestMapping(value = "/members")
public class MemberController {
    private static Logger log = LoggerFactory.getLogger(MemberController.class);
    @Autowired
    private MemberService memberService;


    /**
     * 保存对象<br/>
     *
     * @param member 对象
     */
    @PostMapping
    @RequiresPermissions("sys:member:save")
    @LogOperate(description = "新增Member")
    public Result save(@Validated @RequestBody Member member) {
        Result result = new Result();
        try {
            memberService.save(member);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param member
     * @return
     */
    @PutMapping(value = "/{id}")
    @RequiresPermissions("sys:member:update")
    @LogOperate(description = "更新Member")
    public Result update(@Validated @RequestBody Member member) {
        Result result = new Result();
        try {
            boolean exists = memberService.existsById(member.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            memberService.save(member);
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
    @RequiresPermissions("sys:member:delete")
    @LogOperate(description = "删除Member")
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            memberService.deleteInBatch(StringUtil.stringToIntegerList(ids));
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
     * @return Member 对象
     */
    @GetMapping(value = "/{id}")
    @RequiresPermissions("sys:member:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(memberService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<Member> 对象
     */
    @PostMapping(value = "/pages")
    @RequiresPermissions("sys:member:list")
    public Result findByPage(@RequestBody QueryMember queryMember) {

        CommonResult result = new CommonResult();
        try {
            int min = (queryMember.getPage() - 1) * 10;
            int max = queryMember.getPage() * 10;
            String userName = queryMember.getUsername();
            String companyName =queryMember.getCompanyName();
            log.info(userName);
            log.info(companyName);
            List<Member> list = memberService.getAll(min, max,userName,companyName);
            int pageCount =(int) memberService.count();
            int page = queryMember.getPage();
            int pageSize = queryMember.getPageSize();

            PageBean<Member> pageBean = new PageBean<>();
            if (list == null || 0 == list.size()) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts(pageCount);
            pageBean.setPageSize(pageSize);
            pageBean.setList(list);
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
            List<Member> list = memberService.findAllById(StringUtil.stringToIntegerList(ids));
            workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "member"), Member.class, list);
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
