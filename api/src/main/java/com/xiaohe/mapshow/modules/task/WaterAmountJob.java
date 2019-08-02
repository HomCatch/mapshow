package com.xiaohe.mapshow.modules.task;


import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import com.xiaohe.mapshow.modules.dbinfo.service.DbInfoService;
import com.xiaohe.mapshow.modules.history.entity.HalfMonthRecord;
import com.xiaohe.mapshow.modules.history.jpa.HalfMonthRecordRepository;
import com.xiaohe.mapshow.modules.history.service.HalfMonthRecordService;
import com.xiaohe.mapshow.modules.history.service.HistoryService;
import com.xiaohe.mapshow.utils.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




/**
 * 水量定时统计任务
 * 每日凌晨2点执行
 */
public class WaterAmountJob implements Job {
	private static Logger log = LoggerFactory.getLogger(WaterAmountJob.class);
	@Value("${ds_base}")
	private String dsBase;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private HalfMonthRecordService monthRecordService;
    @Autowired
    private DbInfoService dbInfoService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	    log.info("WaterAmountJob开始执行:" + DateUtil.formatNow());
	    try {
	    	//查询所有已绑定库 循环插入
		    List<DbInfo> dbInfos = dbInfoService.queryBound(dsBase);
		    for (DbInfo dbInfo : dbInfos) {
			    String dbName = dbInfo.getDbName();
			    //查询昨日水量数据
			    List<HalfMonthRecord> record = historyService.getYestWaterRecord(dbName);
			    //插入近7日数据统计表
			    monthRecordService.saveList(dbName,record);
			    log.info("WaterAmountJob执行成功，数据已插入:" + DateUtil.formatNow());
		    }
	    } catch (Exception e) {
		    e.printStackTrace();
		    log.error("WaterAmountJob执行出错:", e);
	    }
    }
}
