package com.xiaohe.mapshow.modules.member.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaohe.mapshow.modules.company.entity.Company;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.query.QueryVo;
import com.xiaohe.mapshow.modules.member.entity.Member;

import java.util.Map;

/**
 * MemberService层
 *
 * @author gmq
 * @since 2019-07-10
 */
public interface MemberService extends IService<Member> {

	PageUtils queryPage(QueryVo queryVo);

	/**
	 * 根据用户名查询所属数据库名称
	 *
	 * @param username 用户名
	 * @return dbname
	 */
	String queryDbNameByUserName(String username);
	/**
	 * 根据用户名查询所属数据库名称
	 * @param dsBase 基础库名
	 * @param username 用户名
	 * @return dbname
	 */
	String queryDbNameByUserName2(String dsBase,String username);

	Integer save(Member entity);

}

