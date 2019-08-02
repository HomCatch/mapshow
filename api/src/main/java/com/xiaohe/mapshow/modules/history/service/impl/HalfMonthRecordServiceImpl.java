package com.xiaohe.mapshow.modules.history.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.history.entity.HalfMonthRecord;
import com.xiaohe.mapshow.modules.history.jpa.HalfMonthRecordRepository;
import com.xiaohe.mapshow.modules.history.service.HalfMonthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Gmq
 * @since 2019-07-15 11:05
 **/
@Service
public class HalfMonthRecordServiceImpl implements HalfMonthRecordService {

	@Autowired
	private HalfMonthRecordRepository halfMonthRecordRepository;

	@Override
	@DS("tenantName")
	public List<HalfMonthRecord> saveList(String tenantName, List<HalfMonthRecord> halfMonthRecords) {
		return halfMonthRecordRepository.saveAll(halfMonthRecords);
	}

}
