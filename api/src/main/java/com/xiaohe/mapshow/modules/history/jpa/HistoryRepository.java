package com.xiaohe.mapshow.modules.history.jpa;

import com.xiaohe.mapshow.modules.history.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 *  jpa 接口
 *
 * @author gmq
 * @since 2019-03-29
 */

@Transactional(rollbackFor = Exception.class)
public interface HistoryRepository extends JpaRepository<History, Integer> {

    /**
     * 按条件查询方案
     * @param spec spec
     * @param pageable 分页
     * @return page
     */
    Page<History> findAll(Specification<History> spec, Pageable pageable);

    /**
     * tds色度浊度
     * @return
     */
    @Query(value = "SELECT a.recordDate, ifnull( b.tds, 0) AS tds, ifnull( b.purify_tds, 0 ) AS purifyTds, ifnull( b.color, 0.00 ) AS color, ifnull( b.purify_color, 0.00 ) AS purifyColor, ifnull( b.tbdt, 0.00 ) AS tbdt, ifnull( b.purify_tbdt, 0.00 ) AS purifyTbdt, b.device_id AS deviceId FROM( SELECT curdate( ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 1 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 2 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 3 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 4 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 5 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 6 DAY ) AS recordDate ) a LEFT JOIN ( SELECT date( record_time ) AS datetime, tds,purify_tds,color,purify_color, tbdt,purify_tbdt, device_id FROM history where device_id in ?1 GROUP BY datetime ) b ON a.recordDate = b.datetime ORDER BY recordDate ASC;",nativeQuery = true)
    List<Object[]> getTdsAndTbdtAndColor(List<String> deviceIdList);

    /**
     * tds色度浊度
     * @return
     */
    @Query(value = "SELECT a.recordDate, ifnull( b.tds, 0) AS tds, ifnull( b.purify_tds, 0 ) AS purifyTds, ifnull( b.color, 0.00 ) AS color, ifnull( b.purify_color, 0.00 ) AS purifyColor, ifnull( b.tbdt, 0.00 ) AS tbdt, ifnull( b.purify_tbdt, 0.00 ) AS purifyTbdt, b.device_id AS deviceId FROM( SELECT curdate( ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 1 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 2 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 3 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 4 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 5 DAY ) AS recordDate UNION ALL SELECT date_sub( curdate( ), INTERVAL 6 DAY ) AS recordDate ) a LEFT JOIN ( SELECT date( record_time ) AS datetime, AVG(tds) tds,AVG(purify_tds)  purify_tds,AVG(color )color,AVG(purify_color) purify_color,AVG(tbdt) tbdt,AVG(purify_tbdt) purify_tbdt, device_id FROM history GROUP BY datetime ) b ON a.recordDate = b.datetime ORDER BY recordDate ASC;",nativeQuery = true)
    List<Object[]> getTdsAndTbdtAndColor();

    /**
     * 查询昨日记录
     * @return
     */
    @Query(value = "SELECT date( record_time ) AS record_date, AVG( tds ) tds, AVG( purify_tds ) purify_tds, AVG( color ) color, AVG( purify_color ) purify_color, AVG( tbdt ) tbdt, AVG( purify_tbdt ) purify_tbdt, binded_user_id AS user_id FROM history where date( record_time ) = DATE_SUB(CURDATE(), INTERVAL 1 DAY ) GROUP BY record_date, user_id",nativeQuery = true)
    List<Object[]> getYestRecord();
    /**
     * 查询昨日水量记录
     * @return
     */
    @Query(value = "SELECT aa.record_date, sum( aa.water_amout ) AS water_amount, aa.user_id FROM ( SELECT history_id, date( record_time ) AS record_date, sum( today_amount ) AS water_amout, binded_user_id AS user_id, device_id FROM history WHERE date( record_time ) = DATE_SUB( CURDATE( ), INTERVAL 1 DAY ) GROUP BY user_id, device_id ORDER BY history_id DESC ) aa GROUP BY aa.user_id",nativeQuery = true)
    List<Object[]> getYestWaterRecord();

    /**
     * 查询是否存在经销商集合
     * @param bindUserIds
     * @return
     */
    boolean existsAllByBindedSubUserIdIn(List<Integer> bindUserIds);

    @Modifying
    @Query(value = "update history SET record_time=NOW() where tds>purify_tds ORDER BY rand() LIMIT ?1",nativeQuery = true)
    void updateToday(int count);

    @Query(value = "select * from history",nativeQuery =  true)
    List<History> getAll();
}