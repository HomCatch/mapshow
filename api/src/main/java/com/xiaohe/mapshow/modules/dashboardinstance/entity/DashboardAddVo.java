package com.xiaohe.mapshow.modules.dashboardinstance.entity;

import javax.validation.constraints.NotNull;

/**
 * @program: mapShow
 * @description:
 * @author: Gmq
 * @date: 2019-04-03 17:29
 **/
public class DashboardAddVo {
    @NotNull(message = "x坐标不能为空")
    private int x;

    @NotNull(message = "x坐标不能为空")
    private int y;

    @NotNull(message = "x坐标不能为空")
    private int w;

    @NotNull(message = "x坐标不能为空")
    private int h;

    @NotNull(message = "typeId不能为空")
    private int typeId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return y;
    }

    public void setW(int w) {
        this.w = w;
    }
    public int getW() {
        return w;
    }

    public void setH(int h) {
        this.h = h;
    }
    public int getH() {
        return h;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public int getTypeId() {
        return typeId;
    }
}
