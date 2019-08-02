package com.xiaohe.mapshow.modules.task;

import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import com.xiaohe.mapshow.modules.dbinfo.service.DbInfoService;
import com.xiaohe.mapshow.modules.history.service.HistoryService;
import com.xiaohe.mapshow.utils.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class HistoryJob implements Job {
    private static Logger log = LoggerFactory.getLogger(HistoryJob.class);
    @Autowired
    private HistoryService historyService;
    @Autowired
    private DbInfoService dbInfoService;
    @Value("${ds_base}")
    private String dsBase;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("HistoryJob开始执行" + DateUtil.formatNow());
        List<DbInfo> dbInfos = dbInfoService.queryBound(dsBase);

        for (int i = 0; i < dbInfos.size(); i++) {
            String dbName = dbInfos.get(i).getDbName();
            historyService.updateToday(10,dbName);
        }
    }
}
