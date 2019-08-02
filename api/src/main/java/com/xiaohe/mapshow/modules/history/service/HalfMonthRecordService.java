package com.xiaohe.mapshow.modules.history.service;

import com.xiaohe.mapshow.modules.history.entity.HalfMonthRecord;

import java.util.List;

public interface HalfMonthRecordService {

	List<HalfMonthRecord> saveList(String tenantName,List<HalfMonthRecord> halfMonthRecords);
}
