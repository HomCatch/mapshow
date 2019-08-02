package com.xiaohe.mapshow.modules.filterplan.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.modules.device.jpa.DeviceRepository;
import com.xiaohe.mapshow.modules.filterplan.entity.FilterPlan;
import com.xiaohe.mapshow.modules.filterplan.entity.QueryFilterPlan;
import com.xiaohe.mapshow.modules.filterplan.jpa.FilterPlanRepository;
import com.xiaohe.mapshow.modules.filterplan.service.FilterPlanService;
import com.xiaohe.mapshow.modules.sys.service.SysDeptService;
import com.xiaohe.mapshow.utils.DateUtil;
import com.xiaohe.mapshow.utils.Result;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * FilterPlan服务类
 * </p>
 *
 * @author gmq
 * @since 2019-03-29
 */

@Service
@DS("#session.tenantName")
public class FilterPlanServiceImpl implements FilterPlanService {
    private final Logger log = LoggerFactory.getLogger(FilterPlanServiceImpl.class);
    @Autowired
    private FilterPlanRepository filterPlanRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private SysDeptService sysDeptService;
    @Resource
    private HttpServletResponse response;
    @Resource
    private ResourceLoader resourceLoader;

    /**
     * 保存对象
     *
     * @param filterPlan 对象
     *                   持久对象，或者对象集合
     */
    @Override
    public FilterPlan save(FilterPlan filterPlan) {
        return filterPlanRepository.save(filterPlan);
    }

    /**
     * 删除对象
     *
     * @param filterPlan 对象
     */
    @Override
    public void delete(FilterPlan filterPlan) {
        filterPlanRepository.delete(filterPlan);
    }

    @Override
    public void deleteAll(List<FilterPlan> list) {
        filterPlanRepository.deleteAll(list);
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids
     */
    @Override
    public void deleteInBatch(List<Integer> ids) {
        filterPlanRepository.deleteInBatch(filterPlanRepository.findAllById(ids));
    }

    /**
     * 通过id判断是否存在
     *
     * @param id
     */
    @Override
    public boolean existsById(Integer id) {
        return filterPlanRepository.existsById(id);
    }

    /**
     * 返回可用实体的数量
     */
    @Override
    public long count() {
        return filterPlanRepository.count();
    }

    /**
     * 通过id查询
     *
     * @param id id
     * @return FilterPlan对象
     */
    @Override
    public FilterPlan findById(Integer id) {
        Optional<FilterPlan> optional = filterPlanRepository.findById(id);
        boolean present = optional.isPresent();
        return present ? optional.get() : null;
    }

    /**
     * 分页查询
     * id处字符串为需要排序的字段，可以传多个，比如 "id","createTime",...
     *
     * @param page     页面
     * @param pageSize 页面大小
     * @return Page<FilterPlan>对象
     */
    @Override
    public Page<FilterPlan> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        return filterPlanRepository.findAll(pageable);
    }

    @Override
    public Page<FilterPlan> findAll(int page, int pageSize, QueryFilterPlan queryFilterPlan) {
        //过滤自己的广告
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        //查询条件构造
        Integer bindedUserId = queryFilterPlan.getUserId();
        //如果是超管  不过滤
        List<String> deviceIdByUserId = null;
        Specification<FilterPlan> spec = null;
        if (bindedUserId != null && !bindedUserId.equals(1)) {
            List<Long> allSubUserId = sysDeptService.findSelfAndSubUserId();
            List<Integer> ids = allSubUserId.stream().map(Long::intValue).collect(Collectors.toList());
            deviceIdByUserId = deviceRepository.findDeviceIdByUserIdIn(ids);
        }
        List<String> finalDeviceIdByUserId = deviceIdByUserId;
        if (CollectionUtils.isEmpty(finalDeviceIdByUserId) && 1 != bindedUserId) {
            return null;
        } else {
            spec = (Specification<FilterPlan>) (root, query, cb) -> {
                Predicate predicate = cb.conjunction();
                if (StringUtils.isNotBlank(queryFilterPlan.getDeviceId())) {
                    predicate.getExpressions().add(cb.like(root.get("deviceId").as(String.class), "%" + queryFilterPlan.getDeviceId() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterPlan.getPreviousRemark())) {
                    predicate.getExpressions().add(cb.like(root.get("previousRemark").as(String.class), "%" + queryFilterPlan.getPreviousRemark() + "%"));
                }
                if (StringUtils.isNotBlank(queryFilterPlan.getNextRemark())) {
                    predicate.getExpressions().add(cb.like(root.get("nextRemark").as(String.class), "%" + queryFilterPlan.getNextRemark() + "%"));
                }
                //不是超管默认按经销商的所属设备编号过滤
                if (1 != bindedUserId) {
                    CriteriaBuilder.In<String> in = cb.in(root.get("deviceId"));
                    for (String s : finalDeviceIdByUserId) {
                        in.value(s);
                    }
                    predicate.getExpressions().add(cb.and(in));
                }
                return predicate;
            };
        }
        return filterPlanRepository.findAll(spec, pageable);
    }

    /**
     * 根据Id查询list
     *
     * @param ids id集合
     * @return list
     */
    @Override
    public List<FilterPlan> findAllById(List<Integer> ids) {
        return filterPlanRepository.findAllById(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result batchImport(MultipartFile file) throws Exception {
        Result result = new Result();
        List<FilterPlan> filterInfos = new ArrayList<>();

        String fileName = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return result.error(1008, "EXCEL导入失败");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is;
        Workbook wb;

        is = file.getInputStream();
        if (!isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getPhysicalNumberOfRows();
        int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
        if (rowNum == 1) {
            //只有标题行
            return result.error(1009, "EXCEL为空");
        }
        if (coloumNum != 5) {
            //不是5列
            return result.error(1009, "格式错误！");
        }
        FilterPlan filterInfo;
        Date date = new Date();
        //导入失败的记录
        List<String> erroList = new LinkedList<>();
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            row.getCell(0).setCellType(CellType.STRING);
            String filterCode = row.getCell(0).getStringCellValue();
            Date lastTime = StringUtils.isNotBlank(row.getCell(1).getStringCellValue()) ? DateUtil.parse(row.getCell(1).getStringCellValue()) : null;
            String lastRemark = row.getCell(2).getStringCellValue();
            Date nextTime = StringUtils.isNotBlank(row.getCell(3).getStringCellValue()) ? DateUtil.parse(row.getCell(1).getStringCellValue()) : null;
            ;
            String nextRemark = row.getCell(4).getStringCellValue();
            if (filterCode == null || filterCode.isEmpty()) {
                log.info("导入失败(第" + (r + 1) + "行,滤芯编码未填写)");
                continue;
            }

            filterInfo = new FilterPlan();
            filterInfo.setDeviceId(filterCode);
            filterInfo.setPreviousTime(lastTime);
            filterInfo.setPreviousRemark(lastRemark);
            filterInfo.setNextTime(nextTime);
            filterInfo.setNextRemark(nextRemark);
            filterInfos.add(filterInfo);
        }

        for (FilterPlan info : filterInfos) {
            String filterCode = info.getDeviceId();
            boolean b = filterPlanRepository.existsByDeviceId(filterCode);
            if (b) {
                log.info(filterCode + "已经存在，不能导入");
                erroList.add(filterCode);
                continue;
            }
            save(info);
        }
        result.setRet(erroList.size() > 0 ? -1 : 0);
        result.setMsg("成功导入" + (filterInfos.size() - erroList.size()) + "条,导入失败" + erroList.size() + "条");
        return result;
    }

    /**
     * 批量导出
     *
     * @param ids
     * @throws Exception
     */
    @Override
    public void batchExport(List<Integer> ids) throws Exception {

        //创建HSSFWorkbook对象(excel的文档对象)
        XSSFWorkbook wb = new XSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        XSSFSheet sheet = wb.createSheet("滤芯更换信息表");
        setTitle(wb, sheet);

        List<FilterPlan> filterInfos;
        if (CollectionUtils.isEmpty(ids)) {
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            filterInfos = filterPlanRepository.findAll(sort);
        } else {
            filterInfos = filterPlanRepository.findAllByIdIn(ids);
        }
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        for (FilterPlan filterInfo : filterInfos) {
            XSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(filterInfo.getDeviceId());
            row.createCell(1).setCellValue(DateUtil.format(filterInfo.getPreviousTime()));
            row.createCell(2).setCellValue(filterInfo.getPreviousRemark());
            row.createCell(3).setCellValue(DateUtil.format(filterInfo.getNextTime()));
            row.createCell(4).setCellValue(filterInfo.getNextRemark());
            rowNum++;
        }
        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=filterInfo.xlsx");
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();

    }


    /**
     * 下载滤芯模板
     */
    @Override
    public void downloadTemplate() {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String fileName = "滤芯编码导入模板.xlsx";
            String path = "templates/filterInfoModel.xlsx";
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:" + path);

            response.setContentType("application/msexcel");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + encodeName);

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 设置表头
     * @param workbook workbook
     * @param sheet  XSSFSheet
     */
    private void setTitle(XSSFWorkbook workbook, XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(0, 60 * 256);

        //设置为居中加粗
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        style.setAlignment(HorizontalAlignment.CENTER);
        //sheet页对象
        //设置一下上下左右的格式
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setFont(font);
        XSSFCell cell;
        XSSFCell cell1;
        XSSFCell cell2;
        XSSFCell cell3;
        XSSFCell cell4;
        cell = row.createCell(0);
        cell1 = row.createCell(1);
        cell2 = row.createCell(2);
        cell3 = row.createCell(3);
        cell4 = row.createCell(4);
        cell.setCellValue("滤芯编号");
        cell1.setCellValue("上次滤芯更换时间");
        cell2.setCellValue("上次滤芯更换备注");
        cell3.setCellValue("下次滤芯更换时间");
        cell4.setCellValue("下次滤芯更换备注");
        cell.setCellStyle(style);
        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);

    }

}


