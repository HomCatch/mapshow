package com.xiaohe.mapshow.modules.history.jpa;

import com.xiaohe.mapshow.modules.history.entity.HistoryBrokenLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Gmq
 * @since 2019-04-11 18:25
 **/
@Transactional(rollbackFor = Exception.class)
public interface HistoryBrokenLineRepository extends JpaRepository<HistoryBrokenLine, Integer> {
	/**
	 * 根据用户Id集合及时间范围查询
	 * @param userId 用户ID
	 * @param begin 开始时间
	 * @param end 截止时间
	 * @return
	 */
	List<HistoryBrokenLine> findAllByUserIdInAndRecordDateBetween(List<Integer> userId,String begin,String end);
	/**
	 * 根据时间范围查询
	 * @param begin 开始时间
	 * @param end 截止时间
	 * @return
	 */
	List<HistoryBrokenLine> findAllByRecordDateBetween(String begin,String end);

}
