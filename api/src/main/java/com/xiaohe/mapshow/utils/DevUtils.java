package com.xiaohe.mapshow.utils;

import com.xiaohe.mapshow.utils.Result;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hzh
 * @date 2018/9/2614:18
 */
public class DevUtils {

    public static void getResult(Result result, int res) {
        if (0 != res) {
            result.error(1111, "下发命令失败，请稍后重试，错误码：1111");
        }
    }
    /**
     * 通过对象列表获取设备系列号
     *
     * @param objects 被操作对象
     * @return 设备序列号集合
     */
    public static List<String> getDeviceIds(List objects) {
        List<String> ids = new LinkedList<>();
        if (!CollectionUtils.isEmpty(objects)) {
            for (Object object : objects) {
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().contains("deviceId")) {
                        Object fieldValueByName = EntityUtils.getFieldValueByName(field.getName(), object);
                        ids.add((String) fieldValueByName);
                    }
                }
            }
        }
        return ids;
    }

    /**
     * 通过对象列表获取id集合
     *
     * @param objects
     * @return
     */
    public static List<Long> getIds(List objects) {
        List<Long> ids = new LinkedList<>();
        if (!CollectionUtils.isEmpty(objects)) {
            for (Object object : objects) {
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().contains("id")) {
                        Object fieldValueByName = EntityUtils.getFieldValueByName(field.getName(), object);
                        ids.add((Long) fieldValueByName);
                        continue;
                    }
                }
            }
        }
        return ids;
    }
}
