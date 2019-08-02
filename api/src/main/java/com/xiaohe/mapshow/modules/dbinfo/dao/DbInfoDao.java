package com.xiaohe.mapshow.modules.dbinfo.dao;

import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 *  DbInfoDao层
 *
 * @author gmq
 * @since 2019-07-10
 */
public interface DbInfoDao extends BaseMapper<DbInfo> {

	/**
	 * 查询未绑定公司的数据库
	 * @return
	 */
	List<DbInfo> queryUnbound();
	/**
	 * 查询已绑定公司的数据库
	 * @return
	 */
	List<DbInfo> queryBound();

	/**
	 * 批量插入dbinfo
	 * @param dbInfos
	 */
	void addDbInfos(@Param("list")List<DbInfo> dbInfos);

}
