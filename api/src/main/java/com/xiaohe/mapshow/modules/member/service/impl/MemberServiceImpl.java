package com.xiaohe.mapshow.modules.member.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.untils.PageUtils;
import com.xiaohe.mapshow.modules.untils.query.QueryVo;
import com.xiaohe.mapshow.modules.untils.query.QueryToWrapper;
import com.xiaohe.mapshow.modules.member.service.MemberService;
import com.xiaohe.mapshow.modules.member.entity.Member;
import com.xiaohe.mapshow.modules.member.dao.MemberDao;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.Serializable;


/**
 * MemberServiceImpl层
 *
 * @author gmq
 * @since 2019-07-10
 */
@Service("memberService")
@DS("#session.dsBase")
public class MemberServiceImpl extends ServiceImpl<MemberDao, Member> implements MemberService {
	@Autowired
	private MemberDao memberDao;
	@Override
	public PageUtils queryPage(QueryVo queryVo) {
		EntityWrapper<Member> entityWrapper = new EntityWrapper<>();
		//查询前预处理
		QueryToWrapper.convert(entityWrapper, queryVo);
		Page<Member> page = this.selectPage(
				new Page<>(queryVo.getPage(), queryVo.getPageSize()),
				entityWrapper
		);
		page.setTotal(this.selectCount(null));
		return new PageUtils(page);
	}

	/**
	 * 根据用户名查询所属数据库名称(session取值)
	 * @param username 用户名
	 * @return dbname
	 */
	@Override
	public String queryDbNameByUserName(String username) {
		return memberDao.queryDbNameByUserName(username);
	}
	/**
	 * 根据用户名查询所属数据库名称(session取值)
	 * @param username 用户名
	 * @return dbname
	 */
	@Override
	@DS("#dsBase")
	public String queryDbNameByUserName2(String dsBase,String username) {
		return memberDao.queryDbNameByUserName(username);
	}

	@Override
	public Integer save(Member entity) {
		return baseMapper.insert(entity);
	}
}
