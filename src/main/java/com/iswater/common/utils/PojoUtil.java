package com.iswater.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.iswater.map.pojos.Device;

/**
 * POJO工具类
 * 
 * @author lmx
 *
 */
public class PojoUtil {

	private Logger logger = Logger.getLogger(PojoUtil.class);

	private static PojoUtil pojo = null;

	public static PojoUtil getInstance() {
		if (pojo == null) {
			pojo = new PojoUtil();
		}
		return pojo;
	}
	
	
	public String getClassName(Object o){
		return o.getClass().getName();
	}

	
	/**
	 * 获取属性类的所有名称
	 * 
	 * @param obj
	 * @return
	 */
	public static String[] getfieldNames(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}
	
	/**
	 * 获取属性类的所有名称
	 * 
	 * @param obj
	 * @return
	 */
	public String[] getFiledNames(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	/**
	 * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 * 
	 * @param o
	 * @return
	 */
	public List<Map<String, Object>> getFiledsMsg(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		List<Map<String, Object>> list = Lists.newArrayList();
		Map<String, Object> infoMap = null;
		for (int i = 0; i < fields.length; i++) {
			infoMap = new HashMap<String, Object>();
			infoMap.put("type", fields[i].getType().toString());
			infoMap.put("name", fields[i].getName());
			infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
			list.add(infoMap);
		}
		return list;
	}

	/**
	 * 根据属性名获取属性值
	 * 
	 * @param fieldName
	 * @param o
	 * @return
	 */
	/*
	public String getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value == null ? "" : String.valueOf(value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	*/
	
	/**
	 * 根据属性名获取属性值
	 * 
	 * @param fieldName
	 * @param o
	 * @return
	 */
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
		//	logger.error(e.getMessage(), e);
			return null;
		}
	}	
	

	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(PojoUtil.getInstance().getFiledNames(new Device())));
	}
}
