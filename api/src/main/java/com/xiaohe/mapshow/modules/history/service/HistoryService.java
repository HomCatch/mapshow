package com.xiaohe.mapshow.modules.history.service;

import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
import com.xiaohe.mapshow.modules.history.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.history.jpa.HistoryRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * History接口
 * </p>
 *
 * @author gmq
 * @since 2019-03-29
 */

public interface HistoryService extends IBaseServiceTwo<History, Integer> {

	/**
	 * 按条件查询
	 *
	 * @param page     页数
	 * @param pageSize 数量
	 * @param
	 * @return Page
	 */
	Page<History> findAll(int page, int pageSize, QueryHistory queryHistory);

	/**
	 * 根据Id查询list
	 *
	 * @return
	 */
	List<History> findAllById(List<Integer> ids);

	/**
	 * 最近15天用水记录
	 *
	 * @return list
	 */
	List<HalfMonthRecord> getRecord(DashboardProperty dashboardProperty);

	/**
	 * 根据设备ID最近7天Tds、色度、浊度记录
	 *
	 * @return list
	 */
	List<HistoryBrokenLine> getTdsAndTbdtAndColor(DashboardProperty dashboardProperty);

	/**
	 * 最近7天Tds记录
	 *
	 * @return list
	 */
	List<HistoryBrokenLine> getTdsRecord(DashboardProperty dashboardProperty);

	/**
	 * 最近7天浊度记录
	 *
	 * @return list
	 */
	List<HistoryBrokenLine> getTbdtRecord(DashboardProperty dashboardProperty);

	/**
	 * 最近7天色度记录
	 *
	 * @return list
	 */
	List<HistoryBrokenLine> getColorRecord(DashboardProperty dashboardProperty);

	/**
	 * 查询昨日记录
	 *
	 * @return
	 */
	List<HistoryBrokenLine> getYestRecord();

	/**
	 * 查询昨日水量记录
	 *
	 * @return
	 */
	List<HalfMonthRecord> getYestWaterRecord(String tenantName);

	/**
	 * 根据用户Id集合及时间范围查询
	 *
	 * @param userId 用户ID
	 * @param begin  开始时间
	 * @param end    截止时间
	 * @return
	 */
	List<HistoryBrokenLine> findAllByUserIdInAndRecordDateBetween(List<Integer> userId, String begin, String end);

	/**
	 * 根据用户Id集合及时间范围查询水量
	 *
	 * @param userId 用户ID
	 * @param begin  开始时间
	 * @param end    截止时间
	 * @return
	 */
	List<HalfMonthRecord> findAllByUserIdInAndRecordDateBetweenWater(List<Integer> userId, String begin, String end);

	void updateToday(int count,String dbName);


	List<History> getAll();
}


