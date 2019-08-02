package com.iswater.common.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.iswater.common.vo.FormVO;
import com.iswater.map.pojos.Device;

public class CommonUtil {

	/**
	 * 在某两个值范围内随机生成count个值
	 * 
	 * @param Min
	 * @param Max
	 * @param count
	 * @return
	 */
	public static List<BigDecimal> getRandomValue(double Min, double Max, int count) {
		List<BigDecimal> list = Lists.newLinkedList();
		for (int i = 0; i < count; i++) {
			BigDecimal db = new BigDecimal(Math.random() * (Max - Min) + Min);
			db.setScale(30, BigDecimal.ROUND_HALF_UP);
			list.add(db);
		}
		return list;
	}

	/**
	 * 在某两个值范围内随机生成1个值
	 * 
	 * @param Min
	 * @param Max
	 * @return
	 */
	public static Float getRandomValue(double Min, double Max) {
		BigDecimal db = new BigDecimal(Math.random() * (Max - Min) + Min);
		db.setScale(30, BigDecimal.ROUND_HALF_UP);
		return db.floatValue();
	}
	
	public static Integer getRandomIntValue(int Min,int Max){
		Random rand = new Random();
		int randNum = rand.nextInt(((int)(Max-Min+1)))+(int)Min;
		return randNum;
	} 
	
	public static Float formatFloat4(Float value,int num){
		BigDecimal db = new BigDecimal(value);
		db.setScale(num, BigDecimal.ROUND_HALF_UP);
		return db.floatValue();
	}

	/**
	 * 一般对象转FormVO对象
	 * 
	 * @param obj
	 * @return
	 */
	public static List<FormVO> changeFormVO(Object obj) {
		List<FormVO> list = Lists.newArrayList();
		if (obj != null) {
			String[] fieldNames = PojoUtil.getfieldNames(obj);
			if (fieldNames != null && fieldNames.length > 0) {
				for (String name : fieldNames) {
					Object value = PojoUtil.getFieldValueByName(name, obj);
					if (value instanceof Date) {
						value = DateUtil.format((Date) value, "yyyy-MM-dd HH:mm:ss");
					}
					FormVO formVO = new FormVO();
					formVO.setName(name);
					formVO.setValue(StringUtil.toTrim(value));
					list.add(formVO);
				}
			}
		}
		return list;
	}
	
	/**
	 * 一般对象转FormVO对象
	 * 
	 * @param obj
	 * @return
	 */
	public static List<FormVO> changeFormVODate(Object obj) {
		List<FormVO> list = Lists.newArrayList();
		if (obj != null) {
			String[] fieldNames = PojoUtil.getfieldNames(obj);
			if (fieldNames != null && fieldNames.length > 0) {
				for (String name : fieldNames) {
					Object value = PojoUtil.getFieldValueByName(name, obj);
					if (value instanceof Date) {
						value = DateUtil.format((Date) value, "yyyy-MM-dd");
					}
					FormVO formVO = new FormVO();
					formVO.setName(name);
					formVO.setValue(StringUtil.toTrim(value));
					list.add(formVO);
				}
			}
		}
		return list;
	}


	/**
	 * 判断两个浮点数是否相等
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static boolean isNumberEq (float f1, float f2){
		if ( (f1>f2) || (f2 > f1)  ){
			return false;
		}else {
			return true;
		}
	}
}
