package com.xiaohe.mapshow.modules.portraitstatic.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */


public class QueryPortraitStatic implements Serializable {


    private Integer id;
    private Integer userId;
    private Integer male;
    private Integer female;
    private Integer childCount;
    private Integer teenCount;
    private Integer youngCount;
    private Integer middleCount;
    private Integer oldCount;
    private Integer Huawei;
    private Integer Apple;
    private Integer xiaomi;
    private Integer SamSong;
    private Integer other;
    private Integer GuangDong;
    private Integer JiangSu;
    private Integer ShanDong;
    private Integer ZheJiang;
    private Integer LiaoNing;
    private Integer SiChuan;
    private Integer HuBei;
    private Integer HuNan;
    private Integer HeBei;
    private Integer HeNan;
    private Integer FuJian;
    private Integer GuiZhou;
    private Integer YunNan;
    private Integer XiZan;
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Integer page = 1;

    private Integer pageSize = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMale() {
        return male;
    }

    public void setMale(Integer male) {
        this.male = male;
    }

    public Integer getFemale() {
        return female;
    }

    public void setFemale(Integer female) {
        this.female = female;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public Integer getTeenCount() {
        return teenCount;
    }

    public void setTeenCount(Integer teenCount) {
        this.teenCount = teenCount;
    }

    public Integer getYoungCount() {
        return youngCount;
    }

    public void setYoungCount(Integer youngCount) {
        this.youngCount = youngCount;
    }

    public Integer getMiddleCount() {
        return middleCount;
    }

    public void setMiddleCount(Integer middleCount) {
        this.middleCount = middleCount;
    }

    public Integer getOldCount() {
        return oldCount;
    }

    public void setOldCount(Integer oldCount) {
        this.oldCount = oldCount;
    }

    public Integer getHuawei() {
        return Huawei;
    }

    public void setHuawei(Integer Huawei) {
        this.Huawei = Huawei;
    }

    public Integer getApple() {
        return Apple;
    }

    public void setApple(Integer Apple) {
        this.Apple = Apple;
    }

    public Integer getXiaomi() {
        return xiaomi;
    }

    public void setXiaomi(Integer xiaomi) {
        this.xiaomi = xiaomi;
    }

    public Integer getSamSong() {
        return SamSong;
    }

    public void setSamSong(Integer SamSong) {
        this.SamSong = SamSong;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public Integer getGuangDong() {
        return GuangDong;
    }

    public void setGuangDong(Integer GuangDong) {
        this.GuangDong = GuangDong;
    }

    public Integer getJiangSu() {
        return JiangSu;
    }

    public void setJiangSu(Integer JiangSu) {
        this.JiangSu = JiangSu;
    }

    public Integer getShanDong() {
        return ShanDong;
    }

    public void setShanDong(Integer ShanDong) {
        this.ShanDong = ShanDong;
    }

    public Integer getZheJiang() {
        return ZheJiang;
    }

    public void setZheJiang(Integer ZheJiang) {
        this.ZheJiang = ZheJiang;
    }

    public Integer getLiaoNing() {
        return LiaoNing;
    }

    public void setLiaoNing(Integer LiaoNing) {
        this.LiaoNing = LiaoNing;
    }

    public Integer getSiChuan() {
        return SiChuan;
    }

    public void setSiChuan(Integer SiChuan) {
        this.SiChuan = SiChuan;
    }

    public Integer getHuBei() {
        return HuBei;
    }

    public void setHuBei(Integer HuBei) {
        this.HuBei = HuBei;
    }

    public Integer getHuNan() {
        return HuNan;
    }

    public void setHuNan(Integer HuNan) {
        this.HuNan = HuNan;
    }

    public Integer getHeBei() {
        return HeBei;
    }

    public void setHeBei(Integer HeBei) {
        this.HeBei = HeBei;
    }

    public Integer getHeNan() {
        return HeNan;
    }

    public void setHeNan(Integer HeNan) {
        this.HeNan = HeNan;
    }

    public Integer getFuJian() {
        return FuJian;
    }

    public void setFuJian(Integer FuJian) {
        this.FuJian = FuJian;
    }

    public Integer getGuiZhou() {
        return GuiZhou;
    }

    public void setGuiZhou(Integer GuiZhou) {
        this.GuiZhou = GuiZhou;
    }

    public Integer getYunNan() {
        return YunNan;
    }

    public void setYunNan(Integer YunNan) {
        this.YunNan = YunNan;
    }

    public Integer getXiZan() {
        return XiZan;
    }

    public void setXiZan(Integer XiZan) {
        this.XiZan = XiZan;
    }


}