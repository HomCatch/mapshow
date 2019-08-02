package com.xiaohe.mapshow.modules.member.controller;

import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.config.validate.XException;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.query.QueryVo;
import com.xiaohe.mapshow.utils.CommonResult;
import com.xiaohe.mapshow.utils.PageBean;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.modules.member.service.MemberService;
import com.xiaohe.mapshow.modules.member.entity.Member;
import com.xiaohe.mapshow.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * MemberController层
 *
 * @author gmq
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
			memberService.insert(member);
		} catch (XException e) {
			log.error(e.getMessage());
			return result.error(e.getRet(), e.getMsg());
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
	public Result update(@Validated @RequestBody Member member, @PathVariable("id") Integer id) {
		Result result = new Result();
		try {
			memberService.updateById(member);
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
			memberService.deleteBatchIds(StringUtil.stringToIntegerList(ids));
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
	 * @return RepairRecord 对象
	 */
	@GetMapping(value = "/{id}")
	@RequiresPermissions("sys:member:info")
	public Result findById(@PathVariable("id") Integer id) {
		CommonResult result = new CommonResult();
		try {
			result.setDatas(memberService.selectById(id));
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(2004, "不存在");
		}
		return result.ok();
	}

	/**
	 * 分页查询
	 *
	 * @return Page<RepairRecord> 对象
	 */
	@PostMapping(value = "/pages")
	@RequiresPermissions("sys:member:list")
	public Result findByPage(@RequestBody QueryVo queryVo) {
		PageUtils page = memberService.queryPage(queryVo);
		CommonResult result = new CommonResult();
		try {
			PageBean<Member> pageBean = new PageBean<>();
			pageBean.setCurPage(page.getCurrPage());
			pageBean.setItemCounts(page.getTotalCount());
			pageBean.setPageSize(page.getTotalPage());
			List<Member> memberList = page.getList() != null ? (List<Member>) page.getList() : null;
			pageBean.setList(memberList);
			result.setDatas(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return result.error(2005, "查询出错");
		}
		return result.ok();
	}
}
