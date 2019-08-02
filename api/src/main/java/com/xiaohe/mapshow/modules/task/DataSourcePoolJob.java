package com.xiaohe.mapshow.modules.task;

import com.xiaohe.mapshow.config.ApplicationContextUtils;
import com.xiaohe.mapshow.modules.datasource.service.IDynamicDataSource;
import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import com.xiaohe.mapshow.modules.dbinfo.service.DbInfoService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 数据源池监控任务
 *
 * @author Gmq
 * @since 2019-07-17 09:58
 **/
public class DataSourcePoolJob implements Job {
	private static Logger log = LoggerFactory.getLogger(DataSourcePoolJob.class);

	@Value("${ds_base}")
	private String dsBase;

	@Autowired
	private IDynamicDataSource iDynamicDataSource;

	@Override
	public  void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		//判断当前未分配数据库数量
		log.info("dsBase----"+dsBase);
		DbInfoService dbInfoService = ApplicationContextUtils.getDbInfoService();
		List<DbInfo> dbInfos = dbInfoService.queryUnbound(dsBase);
		int size = dbInfos.size();
		LinkedList<DbInfo> dbInfoLinkedList = new LinkedList<>();
		//不足10则补充到10个
		if (!CollectionUtils.isEmpty(dbInfos) && 9 < size) {
			log.info("空闲数据库池为{},无需新增", size);
		} else {
			//新增10-size个数据库
			int addNum = 10 - size;
			//默认数据库连接信息
			for (int i = 0; i < addNum; i++) {
				DbInfo dbInfo = iDynamicDataSource.createDatabase(dsBase);
				dbInfoLinkedList.add(dbInfo);
			}
			//插入dbinfo表
			dbInfoService.addDbInfos(dsBase, dbInfoLinkedList);
			log.info("新增{}个数据库", addNum);
		}

	}
}
