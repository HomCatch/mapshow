package com.xiaohe.mapshow.modules.datasource.service.impl;

import com.baomidou.dynamic.datasource.DynamicDataSourceCreator;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.xiaohe.mapshow.modules.datasource.service.IDynamicDataSource;
import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import com.xiaohe.mapshow.modules.dbinfo.service.DbInfoService;
import com.xiaohe.mapshow.utils.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Gmq
 * @since 2019-07-12 17:05
 **/
@Service
public class DynamicDataSourceImpl implements IDynamicDataSource {
	private static Logger log = LoggerFactory.getLogger(IDynamicDataSource.class);
	@Autowired
	private DynamicRoutingDataSource dynamicRoutingDataSource;
	@Autowired
	private DynamicDataSourceCreator createDruidDataSource;
	@Autowired
	private DbInfoService dbInfoService;
	@Autowired
	private IDynamicDataSource iDynamicDataSource;

	@Override
	public boolean addDataSource(DbInfo dbInfo) {
		try {
			String dbName = dbInfo.getDbName();
			DataSourceProperty dataSourceProperty=new DataSourceProperty();
			dataSourceProperty.setUrl("jdbc:mysql://"+dbInfo.getIp()+":"+dbInfo.getPort()+"/"+ dbName +"?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull");
			dataSourceProperty.setDriverClassName("com.mysql.jdbc.Driver");
			dataSourceProperty.setUsername(dbInfo.getUsername());
			dataSourceProperty.setPassword(dbInfo.getPassword());
			DataSource druidDataSource = createDruidDataSource.createDruidDataSource(dataSourceProperty);
			dynamicRoutingDataSource.addDataSource(dbName,druidDataSource);
			log.info("新增:{}数据源成功！",dbName);
		}catch (Exception e){
			return false;
		}
		return true;
	}

	@Override
	public DbInfo createDatabase(String dsBase) {
		DbInfo dbInfo = new DbInfo("iotsvr.he-live.com", "3309", "root", "iswater", "mysql");
		//以js_saas_+时间戳毫秒值为名称创建数据库
		long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
		dbInfo.setDbName("js_saas_" + milliSecond);
		//判断是否存在
		if (!dbInfoService.exist(dsBase, dbInfo)) {
			//执行生成脚本
			SqlUtil.getConn(dbInfo);
			//添加数据源
			iDynamicDataSource.addDataSource(dbInfo);
		}
		return dbInfo;
	}
}
