package com.iswater.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

public class StringUtil extends StringUtils {

	private static DecimalFormat format = new DecimalFormat("#.00");

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 验证数字输入
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isNumber(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	/**
	 * 验证输入身份证号
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isIDcard(String str) {
		String regex = "(^\\d{18}$)|(^\\d{15}$)";
		return match(regex, str);
	}

	/**
	 * 验证输入手机号码
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isMobilePhone(String str) {
		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		return match(regex, str);
	}

	/**
	 * 验证邮箱
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isEmail(String str) {
		String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, str);
	}

	/**
	 * 验证字符串不能包含中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotChinese(String str) {
		String regex = "^[^\u4e00-\u9fa5]{0,}$";
		return match(regex, str);
	}

	/**
	 * 验证电话号码
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 true,否则为 false
	 */
	public static boolean isTelephone(String str) {
		String regex = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
		return match(regex, str);
	}

	/**
	 * String等于NULL返回空
	 * 
	 * @param value
	 * @return
	 */
	public static String toTrim(String value) {
		return value == null ? "" : value;
	}

	/**
	 * Object等于NULL返回空
	 * 
	 * @param value
	 * @return
	 */
	public static String toTrim(Object value) {
		return value == null ? "" : String.valueOf(value);
	}

	public static Double toInitial(Double value) {
		return value == null ? 0d : value;
	}

	/**
	 * 判断集合不为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		if (collection != null && !collection.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断Map不为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		if (map != null && !map.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Double保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static double toFormatDouble(Double value) {
		if (value == null) {
			value = 0d;
		}
		BigDecimal bigDecimal = new BigDecimal(value);
		double formatValue = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return formatValue;
	}
	
	/**
	 * Double保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static double toFormatDouble(String value) {
		double newval = 0;
		if(StringUtil.isNotBlank(value)){
			newval = Double.valueOf(value);
		}
		BigDecimal bigDecimal = new BigDecimal(newval);
		double formatValue = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return formatValue;
	}

	/**
	 * String转Double
	 * 
	 * @param value
	 * @return
	 */
	public static double toDouble(String value) {
		double newvalue = 0;
		if (StringUtil.isNotBlank(value)) {
			newvalue = Double.valueOf(value);
		}
		return newvalue;
	}

	/**
	 * List转String
	 * 
	 * @param list
	 * @return
	 */
	public static String toStr(List<?> list) {
		StringBuilder sb = new StringBuilder();
		if (isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append("'" + list.get(i) + "'").append(",");
				} else {
					sb.append("'" + list.get(i) + "'");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * String转Array
	 * 
	 * @param str
	 * @return
	 */
	public static String[] toArray(String str) {
		if (StringUtil.isNotBlank(str)) {
			String[] strarray = str.split(",");
			return strarray;
		}
		return null;
	}

	/**
	 * 数组分割
	 * 
	 * @param ary
	 * @param subSize
	 *            分割后的数组大小
	 * @return
	 */
	public static List<Object[]> splitArray(Object[] ary, int subSize) {
		int count = ary.length % subSize == 0 ? ary.length / subSize : ary.length / subSize + 1;
		List<List<Object>> subAryList = new ArrayList<List<Object>>();
		for (int i = 0; i < count; i++) {
			int index = i * subSize;
			List<Object> list = new ArrayList<Object>();
			int j = 0;
			while (j < subSize && index < ary.length) {
				list.add(ary[index++]);
				j++;
			}
			subAryList.add(list);
		}
		List<Object[]> list = Lists.newArrayList();
		for (int i = 0; i < subAryList.size(); i++) {
			List<Object> subList = subAryList.get(i);
			Object[] subAryItem = new Object[subList.size()];
			for (int j = 0; j < subList.size(); j++) {
				subAryItem[j] = subList.get(j);
			}
			list.add(subAryItem);
		}
		return list;
	}

	/**
	 * Integer为NULL返回NULL，不为NULL转为String
	 * 
	 * @param value
	 * @return
	 */
	public static String intToStr(Integer value) {
		return value == null ? null : String.valueOf(value);
	}

	/**
	 * String为NULL返回NULL，不为NULL转为Integer
	 * 
	 * @param value
	 * @return
	 */
	public static Integer strToInt(String value) {
		Integer newval = null;
		if (StringUtil.isNotBlank(value)) {
			newval = Integer.valueOf(value);
		}
		return newval;
	}
}
