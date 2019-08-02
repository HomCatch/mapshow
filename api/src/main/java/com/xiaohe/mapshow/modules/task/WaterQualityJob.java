package com.xiaohe.mapshow.modules.task;


import com.xiaohe.mapshow.modules.history.entity.HistoryBrokenLine;
import com.xiaohe.mapshow.modules.history.jpa.HistoryBrokenLineRepository;
import com.xiaohe.mapshow.modules.history.service.HistoryService;
import com.xiaohe.mapshow.utils.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 水质7日定时统计任务
 * 每日凌晨2点10分执行
 */
public class WaterQualityJob implements Job {

	private static Logger log = LoggerFactory.getLogger(WaterQualityJob.class);
	@Autowired
	private HistoryService historyService;
	@Autowired
	private HistoryBrokenLineRepository lineRepository;


	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info("WaterQualityJob开始执行:" + DateUtil.formatNow());
		try {
			//查询昨日数据
			List<HistoryBrokenLine> record = historyService.getYestRecord();
			//插入近7日数据统计表
			lineRepository.saveAll(record);
			log.info("WaterQualityJob执行成功，数据已插入:" + DateUtil.formatNow());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("WaterQualityJob执行异常:", e);
		}
	}
}
