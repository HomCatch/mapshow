package com.xiaohe.mapshow.modules.datasource.service;

import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;

/**
 * @author Gmq
 * @since 2019-07-12 17:01
 **/
public interface IDynamicDataSource {
	/**
	 * 新增数据源
	 * @param dbInfo
	 * @return
	 */
	boolean addDataSource(DbInfo dbInfo);

	/**
	 * 自定义脚本生成数据库 并添加数据源
	 * @return
	 */
	DbInfo createDatabase(String dsBase);

}
