package com.xiaohe.mapshow.modules.untils.query;

/**
 * 排序对象
 *
 * @author hzh
 * @version 1.0
 * @date 2018/12/28 15:02
 */
public class SortVo {

    private String direction = Direction.DESC.name();
    private String[] orders;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getOrders() {
        return orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }

}
