package com.hpp.utils;

import java.util.ResourceBundle;

public class ConfigUtil {

	private static final ResourceBundle bundle = ResourceBundle
			.getBundle("conf/application");

	/**
	 * 
	 * @Title 函数名称： get
	 * @Description 功能描述： 通过键获取值
	 * @param 参
	 *            数：
	 * @return 返 回 值： String
	 * @author 创 建 者： hpp
	 * @throws
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}

	public static final Integer getIntVal(String key) {
		String val = bundle.getString(key);
		return Integer.valueOf(val);
	}

	public static final String getProp(String key) {
		return System.getProperties().getProperty(key);
	}

	public static final void setProp(String key, String value) {
		System.getProperties().setProperty(key, value);
	}
}
