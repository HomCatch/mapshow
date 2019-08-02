package com.xiaohe.mapshow.modules.member.dao;

import com.xiaohe.mapshow.modules.member.entity.Member;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

/**
 *
 *  MemberDao层
 *
 * @author gmq
 * @since 2019-07-10
 */
public interface MemberDao extends BaseMapper<Member> {

	/**
	 * 根据用户名查询所属数据库名称
	 * @param username 用户名
	 * @return dbname
	 */
	String queryDbNameByUserName(@Param("username") String username);


}
