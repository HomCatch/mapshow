

package com.xiaohe.experienceUser.utils;

import java.util.HashMap;


/**
 * Map工具类
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.0.0
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
