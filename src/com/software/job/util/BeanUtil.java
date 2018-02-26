package com.software.job.util;

public class BeanUtil {
	/**
	 * 这里是解析数据库的字段
	 * @param fieldName
	 * @return
	 */
	public static String parseSetMethod(String fieldName) {
		if("JOINTIME".equals(fieldName)) {
			return "setJoinTime";
		}else {
			return "set"+fieldName.substring(0, 1)+fieldName.substring(1).toLowerCase();
		}
	}
	/**
	 * 这里针对设置类的set方法
	 * @param classField
	 * @return
	 */
	public static String parseSetMethod2(String classField) {
		return "set"+classField.toUpperCase().substring(0,1)+classField.substring(1);
	}
	/**
	 * 这里时解析类的属性名
	 * @param fieldName
	 * @return
	 */
	public static String parseGetMethod(String classField) {
		return "get"+classField.toUpperCase().substring(0,1)+classField.substring(1);
	}
}
