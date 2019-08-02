package com.xiaohe.mapshow.modules.history.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
import com.xiaohe.mapshow.modules.history.entity.*;
import com.xiaohe.mapshow.modules.history.jpa.HalfMonthRecordRepository;
import com.xiaohe.mapshow.modules.history.jpa.HistoryBrokenLineRepository;
import com.xiaohe.mapshow.modules.history.jpa.HistoryRepository;
import com.xiaohe.mapshow.modules.history.service.HistoryService;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.modules.sys.shiro.ShiroUtils;
import com.xiaohe.mapshow.utils.DateUtil;
import com.xiaohe.mapshow.utils.EntityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * History服务类
 * </p>
 *
 * @author gmq
 * @since 2019-03-29
 */

@Service
@DS("#session.tenantName")
public class HistoryServiceImpl implements HistoryService {

    @Resource
    private HistoryRepository historyRepository;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private HistoryBrokenLineRepository lineRepository;
    @Autowired
    private HalfMonthRecordRepository halfMonthRecordRepository;

    /**
     * 保存对象
     *
     * @param history 对象
     *                持久对象，或者对象集合
     */
    @Override
    public History save(History history) {
        return historyRepository.save(history);
    }

    /**
     * 删除对象
     *
     * @param history 对象
     */
    @Override
    public void delete(History history) {
        historyRepository.delete(history);
    }

    @Override
    public void deleteAll(List<History> list) {
        historyRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        historyRepository.deleteInBatch(historyRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return historyRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return historyRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return History对象
     */
    @Override
    public History findById(Integer id) {
        Optional<History> optional = historyRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<History>对象
     */
    @Override
    public Page<History> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "historyId");
        return historyRepository.findAll(pageable);
    }

    @Override
    public Page<History> findAll(int page, int pageSize, QueryHistory queryHistory) {
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        Long[] selfAndSubDeptIds = sysDeptService.getSelfAndSubDeptIds();
        //如果没有此经销商 分页会很慢 先判断是否存在
        Integer bindedUserId = queryHistory.getBindedUserId();
        if ((bindedUserId != null && !bindedUserId.equals(1)) && 1 != userEntity.getDemoFlag()) {
            List<Long> longs = Arrays.asList(selfAndSubDeptIds);
            List<Integer> collect = longs.stream().map(Long::intValue).collect(Collectors.toList());
            boolean b = historyRepository.existsAllByBindedSubUserIdIn(collect);
            //不存在就无需构建sql
            if (!b) {
                return null;
            }
        }
        //过滤自己的广告
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "recordTime");
        Specification<History> spec = null;
        //查询条件构造
        spec = (Specification<History>) (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (StringUtils.isNotBlank(queryHistory.getDeviceId())) {
                predicate.getExpressions().add(cb.like(root.get("deviceId").as(String.class), "%" + queryHistory.getDeviceId() + "%"));
            }
            if (StringUtils.isNotBlank(queryHistory.getStartTime())) {
                predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("recordTime").as(String.class), queryHistory.getStartTime()));
            }
            if (StringUtils.isNotBlank(queryHistory.getEndTime())) {
                predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("recordTime").as(String.class), queryHistory.getEndTime()));
            }

            //如果是超管  不过滤  按经销商ID过滤  历史表 bindedUserId 是经销商ID
            if ((bindedUserId != null && !bindedUserId.equals(1)) && 1 != userEntity.getDemoFlag()) {
                CriteriaBuilder.In<Integer> in = cb.in(root.get("bindedUserId"));
                for (Long s : selfAndSubDeptIds) {
                    in.value(s.intValue());
                }
                predicate.getExpressions().add(cb.and(in));
            }
            return predicate;
        };
        return historyRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<History> findAllById(List<Integer> ids) {
        return historyRepository.findAllById(ids);
    }

    @Override
    public List<HalfMonthRecord> getRecord(DashboardProperty dashboardProperty) {
        List<HalfMonthRecord> list = new LinkedList<>();
        try {
            //获取15天前和昨天的日期
            LocalDate localDate = LocalDate.now();
            LocalDate localDate1 = localDate.minusDays(1);
            LocalDate localDate15 = localDate.minusDays(15);

            //先构建一个15天的日期集合
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
            List<String> strings = new ArrayList<>();
            strings.add(localDate.minusDays(1).toString());
            strings.add(localDate.minusDays(2).toString());
            strings.add(localDate.minusDays(3).toString());
            strings.add(localDate.minusDays(4).toString());
            strings.add(localDate.minusDays(5).toString());
            strings.add(localDate.minusDays(6).toString());
            strings.add(localDate.minusDays(7).toString());
            strings.add(localDate.minusDays(8).toString());
            strings.add(localDate.minusDays(9).toString());
            strings.add(localDate.minusDays(10).toString());
            strings.add(localDate.minusDays(11).toString());
            strings.add(localDate.minusDays(12).toString());
            strings.add(localDate.minusDays(13).toString());
            strings.add(localDate.minusDays(14).toString());
            strings.add(localDate.minusDays(15).toString());
            //演示数据
            if (0 == dashboardProperty.getDataModel()) {
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(1)), 2547.5412, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(2)), 1684.1541, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(3)), 2174.1541, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(4)), 2342.1541, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(5)), 2641.1254, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(6)), 2112.1215, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(7)), 2267.8815, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(8)), 2081.1512, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(9)), 2455.1541, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(10)), 1865.1251, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(11)), 2313.1512, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(12)), 2281.1514, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(13)), 2131.1211, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(14)), 2441.1541, 0));
                list.add(new HalfMonthRecord(formatter.format(localDate.minusDays(15)), 2124.1552, 0));
            } else {
                //获取自己及下面所有代理的用户iD
                List<Long> selfAndSubUserId = sysDeptService.findSelfAndSubUserId();
                List<Integer> ids = selfAndSubUserId.stream().map(Long::intValue).collect(Collectors.toList());
                List<HalfMonthRecord> record;
                //超管查所有
                if (ids.contains(1)) {
                    record = halfMonthRecordRepository.findAllByRecordDateBetween(localDate15.toString(), localDate1.toString());
                } else {
                    //赋值用户ID
                    record = halfMonthRecordRepository.findAllByUserIdInAndRecordDateBetween(ids, localDate15.toString(), localDate1.toString());
                }
                //求水量总和
                List<HalfMonthRecord> record2 = new LinkedList<>();
                if (!CollectionUtils.isEmpty(record)) {
                    Map<String, Double> collect1 = record.stream().collect(Collectors.groupingBy(HalfMonthRecord::getRecordDate, Collectors.summingDouble(HalfMonthRecord::getWaterAmount)));
                    Iterator iter = collect1.entrySet().iterator(); // 获得map的Iterator
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        HalfMonthRecord halfMonthRecord = new HalfMonthRecord();
                        halfMonthRecord.setRecordDate(entry.getKey().toString());
                        halfMonthRecord.setWaterAmount(entry.getValue() != null ? (double) entry.getValue() : 0.00);
                        record2.add(halfMonthRecord);
                    }
                }
                //初步查询记录
                List<String> dateStr = new LinkedList<>();
                if (!CollectionUtils.isEmpty(record)) {
                    for (HalfMonthRecord historyBrokenLine : record) {
                        dateStr.add(historyBrokenLine.getRecordDate());
                    }
                }
                //待插入的数据
                List<HalfMonthRecord> brokenLines = new ArrayList<>();
                //没有日期补全
                for (String string : strings) {
                    //记录为空
                    if (CollectionUtils.isEmpty(record2)) {
                        HalfMonthRecord historyBrokenLine1 = new HalfMonthRecord();
                        historyBrokenLine1.setRecordDate(string);
                        brokenLines.add(historyBrokenLine1);
                    } else {
                        if (!dateStr.contains(string)) {
                            HalfMonthRecord historyBrokenLine1 = new HalfMonthRecord();
                            historyBrokenLine1.setRecordDate(string);
                            brokenLines.add(historyBrokenLine1);
                        }

                    }
                }
                //拼接list 并排序
                if (0 == brokenLines.size()) {
                    list = record;
                } else if (0 == record.size()) {
                    list = brokenLines;
                } else {
                    brokenLines.addAll(record);
                    list = brokenLines;
                }
                for (HalfMonthRecord historyBrokenLine : list) {
                    LocalDate date = LocalDate.parse(historyBrokenLine.getRecordDate());
                    historyBrokenLine.setRecordDate(formatter.format(date));
                }
            }
            list = list.stream().sorted(Comparator.comparing(HalfMonthRecord::getRecordDate)).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<HistoryBrokenLine> getTdsRecord(DashboardProperty dashboardProperty) {
        return getTdsAndTbdtAndColor(dashboardProperty);
    }

    @Override
    public List<HistoryBrokenLine> getTbdtRecord(DashboardProperty dashboardProperty) {
        return getTdsAndTbdtAndColor(dashboardProperty);
    }

    @Override
    public List<HistoryBrokenLine> getColorRecord(DashboardProperty dashboardProperty) {
        return getTdsAndTbdtAndColor(dashboardProperty);
    }

    @Override
    public List<HistoryBrokenLine> getTdsAndTbdtAndColor(DashboardProperty dashboardProperty) {
        List<HistoryBrokenLine> list = new LinkedList<>();
        try {
            //获取7天前和昨天的日期
            LocalDate localDate = LocalDate.now();
            LocalDate localDate1 = localDate.minusDays(1);
            LocalDate localDate7 = localDate.minusDays(7);
            LocalDate localDate2 = localDate.minusDays(2);
            LocalDate localDate3 = localDate.minusDays(3);
            LocalDate localDate4 = localDate.minusDays(4);
            LocalDate localDate5 = localDate.minusDays(5);
            LocalDate localDate6 = localDate.minusDays(6);

            //先构建一个七天的日期集合
            List<String> strings = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
            strings.add(localDate1.toString());
            strings.add(localDate2.toString());
            strings.add(localDate3.toString());
            strings.add(localDate4.toString());
            strings.add(localDate5.toString());
            strings.add(localDate6.toString());
            strings.add(localDate7.toString());
            //演示数据
            if (0 == dashboardProperty.getDataModel()) {
                list.add(new HistoryBrokenLine(formatter.format(localDate1), new BigDecimal(121.71), new BigDecimal(10.03), 0.87972, 0.4132, 0.36023, 0.10639, 0));
                list.add(new HistoryBrokenLine(formatter.format(localDate2), new BigDecimal(181.67), new BigDecimal(13.11), 0.97564, 0.4232, 0.37429, 0.11632, 0));
                list.add(new HistoryBrokenLine(formatter.format(localDate3), new BigDecimal(152.58), new BigDecimal(11.05), 0.89564, 0.4142, 0.39782, 0.10731, 0));
                list.add(new HistoryBrokenLine(formatter.format(localDate4), new BigDecimal(160.27), new BigDecimal(14.33), 0.96671, 0.4242, 0.39574, 0.11539, 0));
                list.add(new HistoryBrokenLine(formatter.format(localDate5), new BigDecimal(143.58), new BigDecimal(10.53), 0.88898, 0.4562, 0.38571, 0.10839, 0));
                list.add(new HistoryBrokenLine(formatter.format(localDate6), new BigDecimal(159.49), new BigDecimal(13.63), 0.89595, 0.4462, 0.37023, 0.11839, 0));
                list.add(new HistoryBrokenLine(formatter.format(localDate7), new BigDecimal(173.36), new BigDecimal(13.23), 0.87595, 0.4192, 0.39023, 0.12639, 0));
            } else {
                //获取自己及下面所有代理的用户iD
                List<Long> selfAndSubUserId = sysDeptService.findSelfAndSubUserId();
                List<Integer> ids = selfAndSubUserId.stream().map(Long::intValue).collect(Collectors.toList());
                List<HistoryBrokenLine> record;
                //超管查所有
                if (ids.contains(1)) {
                    record = lineRepository.findAllByRecordDateBetween(localDate7.toString(), localDate1.toString());
                } else {
                    //赋值用户ID
                    record = lineRepository.findAllByUserIdInAndRecordDateBetween(ids, localDate7.toString(), localDate1.toString());
                }
                //赋值用户ID
                //初步查询记录
                List<String> dateStr = new LinkedList<>();
                if (!CollectionUtils.isEmpty(record)) {
                    for (HistoryBrokenLine historyBrokenLine : record) {
                        dateStr.add(historyBrokenLine.getRecordDate());
                    }
                }
                //待插入的数据
                List<HistoryBrokenLine> brokenLines = new ArrayList<>();
                //没有日期补全
                for (String string : strings) {
                    //记录为空
                    if (CollectionUtils.isEmpty(record)) {
                        HistoryBrokenLine historyBrokenLine1 = new HistoryBrokenLine();
                        historyBrokenLine1.setRecordDate(string);
                        brokenLines.add(historyBrokenLine1);
                    } else {
                        if (!dateStr.contains(string)) {
                            HistoryBrokenLine historyBrokenLine1 = new HistoryBrokenLine();
                            historyBrokenLine1.setRecordDate(string);
                            brokenLines.add(historyBrokenLine1);
                        }
                    }
                }
                //拼接list 并排序
                if (0 == brokenLines.size()) {
                    list = record;
                } else if (0 == record.size()) {
                    list = brokenLines;
                } else {
                    brokenLines.addAll(record);
                    list = brokenLines;
                }
                for (HistoryBrokenLine historyBrokenLine : list) {
                    LocalDate date = LocalDate.parse(historyBrokenLine.getRecordDate());
                    historyBrokenLine.setRecordDate(formatter.format(date));
                }
            }
            list = list.stream().sorted(Comparator.comparing(HistoryBrokenLine::getRecordDate)).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<HistoryBrokenLine> getYestRecord() {
        List<HistoryBrokenLine> brokenLinesList = new LinkedList<>();
        List<Object[]> record = historyRepository.getYestRecord();
        for (Object[] objects : record) {
            String format = DateUtil.format((Date) objects[0], DateUtil.DATE_FORMAT);
            objects[0] = format;
        }
        List<HistoryBrokenLineVo> historyBrokenLineVos = EntityUtils.castEntity(record, HistoryBrokenLineVo.class, new HistoryBrokenLineVo());
        for (HistoryBrokenLineVo historyBrokenLineVo : historyBrokenLineVos) {
            HistoryBrokenLine historyBrokenLine = new HistoryBrokenLine(historyBrokenLineVo.getRecordDate(), historyBrokenLineVo.getTds(), historyBrokenLineVo.getPurifyTds(), historyBrokenLineVo.getColor(), historyBrokenLineVo.getPurifyColor(), historyBrokenLineVo.getTbdt(), historyBrokenLineVo.getPurifyTbdt(), historyBrokenLineVo.getUserId());
            brokenLinesList.add(historyBrokenLine);
        }
        return brokenLinesList;
    }

    /**
     * 查询昨日水量记录
     *
     * @return
     */
    @Override
    @DS("#tenantName")
    public List<HalfMonthRecord> getYestWaterRecord(String tenantName) {
        List<HalfMonthRecord> halfMonthRecords = new LinkedList<>();
        List<Object[]> record = historyRepository.getYestWaterRecord();
        for (Object[] objects : record) {
            String format = DateUtil.format((Date) objects[0], DateUtil.DATE_FORMAT);
            objects[0] = format;
        }
        List<HalfMonthRecordVo> halfMonthRecords1 = EntityUtils.castEntity(record, HalfMonthRecordVo.class, new HalfMonthRecordVo());
        for (HalfMonthRecordVo halfMonthRecord : halfMonthRecords1) {
            HalfMonthRecord halfMonthRecord1 = new HalfMonthRecord(halfMonthRecord.getRecordDate(), halfMonthRecord.getWaterAmount(), halfMonthRecord.getUserId());
            halfMonthRecords.add(halfMonthRecord1);
        }
        return halfMonthRecords;
    }

    @Override
    public List<HistoryBrokenLine> findAllByUserIdInAndRecordDateBetween(List<Integer> userId, String begin, String end) {
        return lineRepository.findAllByUserIdInAndRecordDateBetween(userId, begin, end);
    }

    @Override
    public List<HalfMonthRecord> findAllByUserIdInAndRecordDateBetweenWater(List<Integer> userId, String begin, String end) {
        return halfMonthRecordRepository.findAllByUserIdInAndRecordDateBetween(userId, begin, end);
    }

    @Override
    @DS("#dbName")
    public void updateToday(int count, String dbName) {
        historyRepository.updateToday(count);
    }

    @Override
    public List<History> getAll() {
        return historyRepository.getAll();
    }
}


