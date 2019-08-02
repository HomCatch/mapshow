package com.xiaohe.mapshow.modules.filterlist.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.xiaohe.mapshow.config.annotation.DemoIntercept;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.modules.device.entity.Device;
import com.xiaohe.mapshow.modules.device.service.DeviceService;
import com.xiaohe.mapshow.modules.filterlist.entity.FilterList;
import com.xiaohe.mapshow.modules.filterlist.entity.QueryFilterList;
import com.xiaohe.mapshow.modules.filterlist.service.FilterListService;
import com.xiaohe.mapshow.modules.untils.NetResult;
import com.xiaohe.mapshow.utils.*;
import org.apache.http.HttpEntity;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * FilterListController层
 *
 * @author hzh
 * @since 2019-04-08
 */
@RestController
@RequestMapping(value = "/filterLists")
public class FilterListController {
    private static Logger log = LoggerFactory.getLogger(FilterListController.class);
    @Autowired
    private FilterListService filterListService;
    @Autowired
    private DeviceService deviceService;

    private static String falseStatus = "1";
    /**
     * 保存对象<br/>
     *
     * @param filterList 对象
     */
    @PostMapping
    @RequiresPermissions("sys:filterList:save")
    @LogOperate(description = "新增FilterList")
    @DemoIntercept
    public Result save(@Validated @RequestBody FilterList filterList) {
        Result result = new Result();
        try {
            filterListService.save(filterList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2001, "新增失败");
        }
        return result.ok();
    }

    /**
     * 更新
     *
     * @param filterList
     * @return
     */
    @PutMapping(value = "/{id}")
    @RequiresPermissions("sys:filterList:update")
    @LogOperate(description = "更新FilterList")
    @DemoIntercept
    public Result updateBanner(@Validated @RequestBody FilterList filterList) {
        Result result = new Result();
        try {
            boolean exists = filterListService.existsById(filterList.getId());
            if (!exists) {
                return result.error(2002, "修改失败，未找到");
            }
            filterListService.save(filterList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2002, "修改失败");
        }
        return result.ok();
    }

    /**
     * 通过id集合删除对象
     *
     * @param ids id集合
     */
    @DeleteMapping(value = "/{id}")
    @RequiresPermissions("sys:filterList:delete")
    @LogOperate(description = "删除FilterList")
    @DemoIntercept
    public Result deleteById(@PathVariable("id") String ids) {
        Result result = new Result();
        try {
            filterListService.deleteInBatch(StringUtil.stringToIntegerList(ids));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2003, "删除失败");
        }
        return result.ok();
    }


    /**
     * 通过id查找对象
     *
     * @param id id
     * @return FilterList 对象
     */
    @GetMapping(value = "/{id}")
    @RequiresPermissions("sys:filterList:info")
    public Result findById(@PathVariable("id") Integer id) {
        CommonResult result = new CommonResult();
        try {
            result.setDatas(filterListService.findById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            return result.error(2004, "不存在");
        }
        return result.ok();
    }

    /**
     * 分页查询
     *
     * @return Page<FilterList> 对象
     */
    @PostMapping(value = "/pages")
    @RequiresPermissions("sys:filterList:list")
    public Result findByPage(@RequestBody QueryFilterList queryFilterList) {

        CommonResult result = new CommonResult();
        try {
            int page = queryFilterList.getPage();
            int pageSize = queryFilterList.getPageSize();
            Page<FilterList> all = filterListService.findAll(page - 1, pageSize, queryFilterList);
            PageBean<FilterList> pageBean = new PageBean<>();
            if (all == null||0==all.getTotalElements()) {
                pageBean.setList(new ArrayList<>());
                result.setDatas(pageBean);
                return result.ok();
            }
            pageBean.setCurPage(page);
            pageBean.setItemCounts((int) all.getTotalElements());
            pageBean.setPageSize(pageSize);
            pageBean.setList(all.getContent());
            result.setDatas(pageBean);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return result.error(2005, "查询出错");
        }
        return result.ok();
    }

    @RequestMapping(value = "/set_replace_date")
    public Result setReplaceDate(@RequestParam(value="device_id",required = true) String deviceId,
                                 @RequestParam(value="set_date",required = true) String setDate) {

        CommonResult commonResult = new CommonResult();
        //||customer =="" || address =="" || tel ==""
        if (deviceId == "" || setDate == ""){
            commonResult.setMsg("参数非法");
            commonResult.setRet(1);
            return commonResult;
        }
        int selectDevice = filterListService.selectDevice(deviceId);
        if(selectDevice > 0){
            commonResult.setMsg("该设备已生成");
            commonResult.setRet(2);
            return commonResult;
        }


        String firstFilter = "";
        String secondFilter = "";
        String thirdFilter = "";
        String fourthFilter = "";

        String customer = "";
        String phone_number = "";
        String address = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(setDate);
            Date now = new Date();
            Timestamp timestamp = new Timestamp(now.getTime());
            Date sDate = date;
            Calendar cal = Calendar.getInstance();
            cal.setTime(sDate);
            cal.add(Calendar.MONTH,8);
            firstFilter =  sdf.format(cal.getTime());
            cal.add(Calendar.MONTH,8);
            secondFilter = sdf.format(cal.getTime());
            cal.add(Calendar.MONTH,8);
            thirdFilter = sdf.format(cal.getTime());
            fourthFilter = sdf.format(cal.getTime());

            //查找客户信息
            Device custInfo = deviceService.findCustInfoByDevId(deviceId);
            if(custInfo != null) {
//                address = custInfo.getAddress();
//                phone_number = custInfo.getPhoneNumber();
//                customer = custInfo.getCustomer();
            }


            filterListService.updateSetDateByDeviceId(setDate,deviceId);

            filterListService.insertFilterList(deviceId,firstFilter,secondFilter,thirdFilter,fourthFilter,customer,address,phone_number);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        commonResult.setMsg("成功");
        commonResult.setRet(0);
        return commonResult;
    }

    /**
     * EXCEL导出
     *
     * @param response re
     * @param ids      id逗号隔开
     * @throws IOException IO异常
     */
    @GetMapping(value = "/export/{id}")
    public void prjExport(HttpServletResponse response, @PathVariable("id") String ids) throws IOException {
        OutputStream os = null;
        try {
            //获取数据库数据
            Workbook workbook;
            List<FilterList> list = filterListService.findAllById(StringUtil.stringToIntegerList(ids));
            workbook = ExcelExportUtil.exportExcel(new ExportParams("表", "filter_list"), FilterList.class, list);
            //保存在本地
            String filename = "ter_daily_report" + System.currentTimeMillis();
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");
            response.setContentType("application/msexcel");
            if (workbook != null) {
                workbook.write(os);
                log.info("导出成功.....");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }
    //生成当月滤芯更换计划
    @RequestMapping(value = "/gen_replace_plan")
    @DemoIntercept
    public Result genReplacePlan(){
        CommonResult commonResult = new CommonResult();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String now = sdf.format(date);
        String thisYear = now.substring(0,4);
        String thisMonth = now.substring(5,7);

        int firstStatus;
        int secondStatus;
        int thirdStatus;
        int fourthStatus;

        List<FilterList> filterList =  filterListService.findFilter();
        long count;
        Integer successCount = 0;
        Integer page = 1;
        page--;
        count = filterList.toArray().length;

        //判断有无重复
        for( int i = 0 ; i < count ; i++) {
            FilterList allFilter = filterList.get(i);
            Boolean back = false;
            List<String> changeList =  filterListService.findChangeList(allFilter.getDeviceId());
            if(!changeList.isEmpty()){
                for(String needTime : changeList) {
                    String needTimeYear = needTime.substring(0,4);
                    String needTimeMonth = needTime.substring(5,7);
                    if(needTimeYear.equals(thisYear) && needTimeMonth.equals(thisMonth)){
                        back = true;
                        continue;
                    }
                }
                if(back){
                    continue;
                }
            }

            //月份差(和当前月份比较 -- 1:小与  3:等于  -1:大于)
            int firstFilter = now.compareTo(allFilter.getFirstFilter().substring(0,7));
            int secondFilter = now.compareTo(allFilter.getSecondFilter().substring(0,7));
            //3,4相同
            int thirdFilter = now.compareTo(allFilter.getThirdFilter().substring(0,7));

            if(firstFilter == 3){
                if(secondFilter == 3){
                    if(thirdFilter == 3){
                        firstStatus = 0;
                        secondStatus = 0;
                        thirdStatus = 0;
                        fourthStatus = 0;
                        filterListService.insertChangeList(allFilter.getDeviceId(),firstStatus,secondStatus,thirdStatus,fourthStatus,allFilter.getCust(),allFilter.getAddress(),allFilter.getTel(), allFilter.getFirstFilter(),0);
                        successCount++;
                        continue;
                    }
                    firstStatus = 0;
                    secondStatus = 0;
                    thirdStatus = 1;
                    fourthStatus = 1;
                    filterListService.insertChangeList(allFilter.getDeviceId(),firstStatus,secondStatus,thirdStatus,fourthStatus,allFilter.getCust(),allFilter.getAddress(),allFilter.getTel(), allFilter.getFirstFilter(),0);
                    successCount++;
                    continue;
                }
                firstStatus = 0;
                secondStatus = 1;
                thirdStatus = 1;
                fourthStatus = 1;
                filterListService.insertChangeList(allFilter.getDeviceId(),firstStatus,secondStatus,thirdStatus,fourthStatus,allFilter.getCust(),allFilter.getAddress(),allFilter.getTel(), allFilter.getFirstFilter(),0);
                successCount++;
                continue;
            }
        }

        commonResult.setRet(0);
        commonResult.setMsg("success");
        PageBean<FilterList> datas = new PageBean<FilterList>();
        datas.setCurPage(page + 1);
        datas.setItemCounts((int)count);
        commonResult.setDatas("count:"+count+",successCount:"+successCount);
        return commonResult;
    }

    //回写滤芯更换信息
    @RequestMapping(value = "/set_repair_info")
    public Result setRepairInfo(@RequestParam(value="device_id",required = true) String deviceId,
                                @RequestParam(value="plan_replace_time",required = true) String planReplaceTime,
                                @RequestParam(value ="real_replace_time",required = true)String realReplaceTime,
                                @RequestParam(value="replace_first_filter",required = true) Integer replaceFirstFilter,
                                @RequestParam(value="replace_second_filter",required = true) Integer replaceSecondFilter,
                                @RequestParam(value="replace_third_filter",required = true) Integer replaceThirdFilter,
                                @RequestParam(value="replace_fourth_filter",required = true) Integer replaceFourthFilter,
                                @RequestParam(value="repairer",required = false) String repairer,
                                @RequestParam(value="repairer_phone_number",required = false) String  repairerPhoneNumber,
                                @RequestParam(value="remark",required = false) String remark){
        CommonResult commonResult = new CommonResult();

        if (deviceId == "" || planReplaceTime == ""){
            commonResult.setMsg("参数非法");
            commonResult.setRet(2007);
            return commonResult;
        }



        int replaceFilterInfi = filterListService.selectReplaceFilterInfo(deviceId);
        if(replaceFilterInfi <= 0){
            commonResult.setMsg("没有对应换芯计划");
            commonResult.setRet(2);
            return commonResult;
        }

        String firstFilter = "";
        String secondFilter = "";
        String thirdFilter = "";
        String fourthFilter = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = sdf.parse(realReplaceTime);
            Date sDate = date;
            Calendar cal = Calendar.getInstance();
            cal.setTime(sDate);
            if(replaceFirstFilter == 0){
                cal.add(Calendar.MONTH,8);
                firstFilter =  sdf.format(cal.getTime());
                filterListService.updateFirstFilterByDevId(deviceId,firstFilter);
            }
            if(replaceSecondFilter == 0){
                cal.add(Calendar.MONTH,8);
                secondFilter = sdf.format(cal.getTime());
                filterListService.updateSecondFilterByDevId(deviceId,secondFilter);
            }
            if(replaceThirdFilter == 0){
                cal.add(Calendar.MONTH,8);
                thirdFilter = sdf.format(cal.getTime());
                filterListService.updateThirdFilterByDevId(deviceId,thirdFilter);
            }
            if(replaceFourthFilter == 0){
                //cal.add(Calendar.MONTH,8);
                fourthFilter = sdf.format(cal.getTime());
                filterListService.updateFourthFilterByDevId(deviceId,fourthFilter);
            }
            if(repairer!=null || repairerPhoneNumber!=null){
                filterListService.updateRepairerInfo(repairer,repairerPhoneNumber,deviceId);
            }
            filterListService.updateChangeList(repairer,repairerPhoneNumber,remark,deviceId,planReplaceTime,realReplaceTime);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        commonResult.setRet(0);
        commonResult.setMsg("success");
        commonResult.setDatas("");
        return commonResult;
    }

    @RequestMapping(value = "/AppGetFilterDate")
    public NetResult appGetFilterDate(@PathVariable @RequestParam(value = "device") String device) throws ParseException {
        NetResult result = new NetResult();
        if (device.isEmpty()) {
            result.setResult("0");
            result.setMessage("设备号不能为空");
            return result;
        }

        Date now = new Date();
        SimpleDateFormat postSdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //pp棉
        Date pp = null;
        //椰壳炭
        Date ykt = null;
        //RO膜
        Date ro = null;
        //后置炭
        Date hz = null;
        //安装时间
        Date installDate = null;


        int ppDay;
        int yktDay;
        int roDay;
        int hzDay;
        String pppercent;
        String yktpercent;
        String ropercent;
        String hztPercent;
        int ppSum = 0;
        int yktSum = 0;
        int roSum = 0;
        int hztSum = 0;

        FilterList filter = filterListService.findByDevId(device);
        Device deviceEntity = deviceService.findCustInfoByDevId(device);
        if (filter != null&&deviceEntity!=null) {

//            installDate = deviceEntity.getSetDate();
            Date ppTime = addMonth(installDate, 8);
            Date yktTime = addMonth(installDate, 16);
            Date roTime = addMonth(installDate, 24);
            Date hztTime = addMonth(installDate, 24);

            pp = (filter.getFirstFilter() == null) ? ppTime :  sdf.parse(filter.getFirstFilter());
            ykt = (filter.getSecondFilter() == null) ? yktTime : sdf.parse( filter.getSecondFilter());
            ro = (filter.getThirdFilter() == null) ? roTime : sdf.parse( filter.getThirdFilter());
            hz = (filter.getFourthFilter() == null) ? hztTime :sdf.parse(  filter.getFourthFilter());

            ppSum = daysBetween(installDate, ppTime);
            yktSum = daysBetween(installDate, yktTime);
            roSum = daysBetween(installDate, roTime);
            hztSum = daysBetween(installDate, hztTime);
        }
//        else {
//            String smsUrl = "http://120.25.74.107/api/webapi/waterQuality/getFirstRecordDate?deviceId";
//            List<NameValuePair> pairs = new ArrayList<>();
//            pairs.add(new BasicNameValuePair("deviceId", device));
//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpPost post = new HttpPost(smsUrl);
//            JSONObject jsonResult;
//            String s = "";
//            CloseableHttpResponse response;
//            try {
//                post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
//                response = httpClient.execute(post);
//                if (response != null && response.getStatusLine().getStatusCode() == 200) {
//                    HttpEntity entity = response.getEntity();
//                    s = entityToString(entity);
//                }
//
//                jsonResult = JSONObject.parseObject(s);
//                System.out.println(s);
//
//                String ret = jsonResult.getString("result");
//
//                if (ret.equals(falseStatus)) {
//                    result.setMessage("查询失败");
//                    result.setResult("1");
//                    return result;
//                }
//
//
//                String date = jsonResult.get("date").toString();
//                Date waterInfo = postSdf.parse(date);
//                pp = addMonth(waterInfo, 8);
//                ykt = addMonth(waterInfo, 16);
//                ro = addMonth(waterInfo, 24);
//                hz = addMonth(waterInfo, 24);
//
//                ppSum = daysBetween(waterInfo, pp);
//                yktSum = daysBetween(waterInfo, ykt);
//                roSum = daysBetween(waterInfo, ro);
//                hztSum = daysBetween(waterInfo, hz);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        ppDay = daysBetween(now, pp) + 1;
        yktDay = daysBetween(now, ykt) + 1;
        roDay = daysBetween(now, ro) + 1;
        hzDay = daysBetween(now, hz) + 1;

        DecimalFormat df = new DecimalFormat("0.00");
        pppercent = df.format((float) ppDay * 100 / ppSum);
        yktpercent = df.format((float) yktDay * 100 / yktSum);
        ropercent = df.format((float) roDay * 100 / roSum);
        hztPercent = df.format((float) hzDay * 100 / hztSum);

        result.setPpm(Double.parseDouble(pppercent) <= 0 ? "0%" : pppercent + "%");
        result.setYkt(Double.parseDouble(yktpercent) <= 0 ? "0%" : yktpercent + "%");
        result.setRom(Double.parseDouble(ropercent) <= 0 ? "0%" : ropercent + "%");
        result.setHzc(Double.parseDouble(hztPercent) <= 0 ? "0%" : hztPercent + "%");
        result.setResult("0");
        result.setMessage("查询成功");

        return result;
    }


    private static Date addMonth(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, value);
        return now.getTime();
    }

    private static int daysBetween(Date dateOld, Date dateNew) {
        return (int) getDateInterval(dateOld, dateNew) / 60 / 60 / 24;
    }

    private static long getDateInterval(Date dateOld, Date dateNew) {
        long interval;
        interval = (dateNew.getTime() - dateOld.getTime()) / 1000;
        return interval;
    }

    private static String entityToString(HttpEntity entity) throws IOException {
        String result = null;
        if (entity != null) {
            long lenth = entity.getContentLength();
            if (lenth != -1 && lenth < 2048) {
                result = EntityUtils.toString(entity, "UTF-8");
            } else {
                InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
                CharArrayBuffer buffer = new CharArrayBuffer(2048);
                char[] tmp = new char[1024];
                int l;
                while ((l = reader1.read(tmp)) != -1) {
                    buffer.append(tmp, 0, l);
                }
                result = buffer.toString();
            }
        }
        return result;
    }



}
