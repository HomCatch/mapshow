package com.xiaohe.mapshow.modules.task;

import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import com.xiaohe.mapshow.modules.dbinfo.service.DbInfoService;
import com.xiaohe.mapshow.modules.device.entity.Device;
import com.xiaohe.mapshow.modules.device.service.DeviceService;
import com.xiaohe.mapshow.modules.devicestatic.entity.DeviceStatic;
import com.xiaohe.mapshow.modules.devicestatic.service.DeviceStaticService;
import com.xiaohe.mapshow.modules.history.service.HistoryService;
import com.xiaohe.mapshow.modules.portraitstatic.entity.PortraitStatic;
import com.xiaohe.mapshow.modules.portraitstatic.service.PortraitStaticService;
import com.xiaohe.mapshow.modules.userinfo.entity.UserInfo;
import com.xiaohe.mapshow.modules.userinfo.service.UserInfoService;
import com.xiaohe.mapshow.modules.userstatic.entity.UserStatic;
import com.xiaohe.mapshow.modules.userstatic.service.UserStaticService;
import com.xiaohe.mapshow.modules.waterstatic.entity.WaterStatic;
import com.xiaohe.mapshow.modules.waterstatic.service.WaterStaticService;
import com.xiaohe.mapshow.utils.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import java.util.*;


public class JsJob implements Job {
    private static Logger log = LoggerFactory.getLogger(JsJob.class);

    @Value("${ds_base}")
    private String dsBase;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceStaticService deviceStaticService;

    @Autowired
    private WaterStaticService waterStaticService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserStaticService userStaticService;

    @Autowired
    private PortraitStaticService portraitStaticService;


    @Autowired
    private DbInfoService dbInfoService;


    private Random random = new Random();



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("JsJob开始执行:" + DateUtil.formatNow());

        List<DbInfo> dbInfos = dbInfoService.queryBound(dsBase);
        for (int i = 0; i < dbInfos.size(); i++) {
            String dbName = dbInfos.get(i).getDbName();
            //设备统计
            devStatic(dbName);
            //水质统计
            waterStatic(dbName);
            //用户统计
            List userInfoList = userStatic(dbName);
            //用户画像统计
            portraitStatic(userInfoList,dbName);
        }

    }

    public void devStatic(String dbName) {
        //全部设备设置为离线
        deviceService.updateDevOffline(dbName);
        //随机在线几台设备
        int onlineCount = random.nextInt(20)+3;
        //随机故障设备
        int faultCount = random.nextInt(5);
        //随机激活设备数量
        int activeDev = random.nextInt(5)+1;
        //激活设备
        List<Integer> userIds = userInfoService.getIds(dbName);
        for (int i = 0; i < activeDev; i++) {
            int n = random.nextInt(userIds.size());
            Integer id = userIds.get(n);
            String deviceId = "X2-11-1907" + (random.nextInt(90000) + 9999);
            Device device = new Device();
            device.setBindedAppId(id);
            device.setBindTime(new Date());
            device.setUpdateTime(new Date());
            device.setInsertDeviceTime(new Date());
            device.setDeviceId(deviceId);
            deviceService.saveDev(device,dbName);
        }
        //设备总数
        long totalDev = deviceService.getCount(dbName);
        int total = (int) totalDev;
        //离线数量
        int offlineCount = total - onlineCount;
        //激活总数
        int activeTotal = deviceService.findActive(dbName);
        //随机激活滤芯数量
        int activeFilter = random.nextInt(4)+1;
        //随机到期滤芯数量
        int maturityFilter = random.nextInt(4)+1;

        deviceService.updateDevOnline(onlineCount,dbName);


        //统计
        Page one = deviceStaticService.findAll(0, 10,dbName);
        List<DeviceStatic> list = one.getContent();
        DeviceStatic deviceStatic = list.get(0);
        deviceStatic.setActiveDeviceCount(activeTotal);
        deviceStatic.setOnlineCount(onlineCount);
        deviceStatic.setOfflineCount(offlineCount);
        deviceStatic.setFaultDeviceCount(faultCount);
        deviceStatic.setActiveFilterCount(deviceStatic.getActiveFilterCount() + activeFilter);
        deviceStatic.setMaturityFilterCount(maturityFilter);
        deviceStatic.setAddDeviceCount(activeDev);
        deviceStatic.setDate(new Date());
        deviceStaticService.save(dbName,deviceStatic);
    }

    public void waterStatic(String dbName) {
        //随机刷新100条为当天水质数据
//        historyService.updateToday(100,dbName);

        //更新每天统计
        Page one = waterStaticService.findAll(0, 10,dbName);
        List<WaterStatic> list = one.getContent();
        WaterStatic waterStatic = list.get(0);
        waterStatic.setDate(new Date());

        waterStaticService.save(waterStatic,dbName);
    }

    public List userStatic(String dbName) {

        //每天随机插入用户
        String provinceValue = "广东,江苏,山东,浙江,辽宁,四川,湖北,湖南,河北,河南,福建,贵州,云南,广西";

        String[] provinceList = provinceValue.split(",");

        int count = random.nextInt(4) + 1;
        List<UserInfo> userInfos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int n = random.nextInt(provinceList.length);
            String province = provinceList[n];
            String phoneNumber = "1708046" + (random.nextInt(8999) + 1000);
            UserInfo userInfo = new UserInfo();
            userInfo.setSex(random.nextInt(2));
            userInfo.setBirth(random.nextInt(50) + 1965 + "年");
            userInfo.setPhoneNumber(phoneNumber);
            userInfo.setPassword("e10adc3949ba59abbe56e057f20f883e");
            if ((i & 1) == 1) {
                userInfo.setLastLoginOs("ios");
            } else {
                userInfo.setLastLoginOs("android");
            }
            userInfo.setRegisteredTime(new Date());
            userInfo.setNickName(phoneNumber);
            userInfo.setLivePlace(province);
            userInfoService.save(userInfo,dbName);
            userInfos.add(userInfo);
        }


        int updateActiveCount = random.nextInt(10);

        userInfoService.updateUserActive(dbName, updateActiveCount);

        userInfoService.updateUserActive(dbName,updateActiveCount);


        int userCount = (int) userInfoService.count(dbName);
        int weekActive = userInfoService.getWeekActive(dbName);
        int monthActive = userInfoService.getMonthActive(dbName);

        Page all = userStaticService.findAll(0, 10,dbName);
        List<UserStatic> list = all.getContent();
        UserStatic userStatic = list.get(0);

        userStatic.setUserId(1);
        userStatic.setAddCount(count);
        userStatic.setRegistCount(count);
        userStatic.setActiveToday(updateActiveCount);
        userStatic.setActiveWeek(weekActive);
        userStatic.setActiveMonth(monthActive);
        userStatic.setTotalCount(userCount);
        userStatic.setDate(new Date());
        userStaticService.save(userStatic,dbName);

        return userInfos;
    }

    public void portraitStatic(List<UserInfo> userInfos,String dbName) {

        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);

        Page all = portraitStaticService.findAll(0, 10,dbName);
        List<PortraitStatic> list = all.getContent();
        PortraitStatic portraitStatic = list.get(0);

        int male = 0;
        int female = 0;
        int child = 0;
        int teen = 0;
        int young = 0;
        int middle = 0;
        int old = 0;
        int apple = 0;
        int huawei = 0;
        int xiaomi = 0;
        int samsong = 0;
        int other = 0;
        int guangdong = 0;
        int jiangsu = 0;
        int shandong = 0;
        int zhejiang = 0;
        int liaoning = 0;
        int sichuan = 0;
        int hubei = 0;
        int hunan = 0;
        int hebei = 0;
        int henan = 0;
        int fujian = 0;
        int guizhou = 0;
        int yunnan = 0;
        int guangxi = 0;

        for (UserInfo userInfo : userInfos) {
            //sex
            switch (userInfo.getSex()) {
                case 0:
                    female += 1;
                    break;
                case 1:
                    male += 1;
                    break;
                default:
                    break;
            }
            //age
            switch (getAge(year - Integer.parseInt(userInfo.getBirth().replace("年", "")))) {
                case 1:
                    child += 1;
                    break;
                case 2:
                    teen += 1;
                    break;
                case 3:
                    young += 1;
                    break;
                case 4:
                    middle += 1;
                    break;
                case 5:
                    old += 1;
                    break;
                default:
                    break;
            }
            //phone
            if (userInfo.getLastLoginOs().equals("android")) {
                int phoneType = random.nextInt(4);
                if (phoneType == 0) {
                    huawei += 1;
                }
                if (phoneType == 1) {
                    xiaomi += 1;
                }
                if (phoneType == 2) {
                    samsong += 1;
                }
                if (phoneType == 3) {
                    other += 1;
                }
            } else {
                apple += 1;
            }

            //province
            switch (userInfo.getLivePlace()) {
                case "广东":
                    guangdong += 1;
                    break;
                case "江苏":
                    jiangsu += 1;
                    break;
                case "山东":
                    shandong += 1;
                    break;
                case "浙江":
                    zhejiang += 1;
                    break;
                case "辽宁":
                    liaoning += 1;
                    break;
                case "四川":
                    sichuan += 1;
                    break;
                case "湖北":
                    hubei += 1;
                    break;
                case "湖南":
                    hunan += 1;
                    break;
                case "河北":
                    hebei += 1;
                    break;
                case "河南":
                    henan += 1;
                    break;
                case "福建":
                    fujian += 1;
                    break;
                case "贵州":
                    guizhou += 1;
                    break;
                case "云南":
                    yunnan += 1;
                    break;
                case "广西":
                    guangxi += 1;
                    break;
                default:
                    break;
            }
        }
        portraitStatic.setMale(portraitStatic.getMale() + male);
        portraitStatic.setFemale(portraitStatic.getFemale() + female);
        portraitStatic.setChildCount(portraitStatic.getChildCount() + child);
        portraitStatic.setTeenCount(portraitStatic.getTeenCount() + teen);
        portraitStatic.setYoungCount(portraitStatic.getYoungCount() + young);
        portraitStatic.setMiddleCount(portraitStatic.getMiddleCount() + middle);
        portraitStatic.setOldCount(portraitStatic.getOldCount() + old);
        portraitStatic.setHuawei(portraitStatic.getHuawei() + huawei);
        portraitStatic.setApple(portraitStatic.getApple() + apple);
        portraitStatic.setXiaomi(portraitStatic.getXiaomi() + xiaomi);
        portraitStatic.setSamSong(portraitStatic.getSamSong() + samsong);
        portraitStatic.setOther(portraitStatic.getOther() + other);
        portraitStatic.setGuangDong(portraitStatic.getGuangDong() + guangdong);
        portraitStatic.setJiangSu(portraitStatic.getJiangSu() + jiangsu);
        portraitStatic.setShanDong(portraitStatic.getShanDong() + shandong);
        portraitStatic.setZheJiang(portraitStatic.getZheJiang() + zhejiang);
        portraitStatic.setLiaoNing(portraitStatic.getLiaoNing() + liaoning);
        portraitStatic.setSiChuan(portraitStatic.getSiChuan() + sichuan);
        portraitStatic.setHuBei(portraitStatic.getHuBei() + hubei);
        portraitStatic.setHuNan(portraitStatic.getHuNan() + hunan);
        portraitStatic.setHeNan(portraitStatic.getHeNan() + henan);
        portraitStatic.setHeBei(portraitStatic.getHeBei() + hebei);
        portraitStatic.setFuJian(portraitStatic.getFuJian() + fujian);
        portraitStatic.setGuiZhou(portraitStatic.getGuiZhou() + guizhou);
        portraitStatic.setYunNan(portraitStatic.getYunNan() + yunnan);
        portraitStatic.setGuangXi(portraitStatic.getGuangXi() + guangxi);
        portraitStatic.setDate(new Date());

        portraitStaticService.save(portraitStatic,dbName);
    }

    public static int getAge(int value) {
        if (value <= 19) {
            return 1;
        }
        if (19 < value || value < 30) {
            return 2;
        }
        if (29 < value || value < 40) {
            return 3;
        }
        if (39 < value || value < 50) {
            return 4;
        }
        if (value >= 50) {
            return 5;
        }
        return 0;
    }


}
