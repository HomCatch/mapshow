package com.xiaohe.mapshow.modules.history.jpa;

import com.xiaohe.mapshow.modules.history.entity.HalfMonthRecord;
import com.xiaohe.mapshow.modules.history.entity.HistoryBrokenLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Gmq
 * @since 2019-04-11 18:54
 **/
public interface HalfMonthRecordRepository extends JpaRepository<HalfMonthRecord, Integer> {

	/**
	 * 根据用户Id集合及时间范围查询水量
	 * @param userId 用户ID
	 * @param begin 开始时间
	 * @param end 截止时间
	 * @return
	 */
	List<HalfMonthRecord> findAllByUserIdInAndRecordDateBetween(List<Integer> userId, String begin, String end);
	/**
	 * 根据时间范围查询水量
	 * @param begin 开始时间
	 * @param end 截止时间
	 * @return
	 */
	List<HalfMonthRecord> findAllByRecordDateBetween(String begin, String end);
}
