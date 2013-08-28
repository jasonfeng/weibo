/*
 * 新浪微博系统
 * Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 系统配置文件读取工具类
 * @author fengzhongtian@163.com
 * @version 1.0
 * @since 2013年8月28日
 */
public final class SysConfigUtil {
	private static Properties properties = initProperties();

	private SysConfigUtil() {
	}

	/**
	 * 初始化配置文件对象
	 * @return
	 */
	private static Properties initProperties() {
		Properties props = new Properties();
		try {
			InputStream in = SysConfigUtil.class.getResourceAsStream("/assets/sysconfig.properties");
			props.load(in);
		} catch (IOException e) {
		}
		return props;
	}

	/**
	 * 获取参数值
	 * @param propertyName
	 * @return
	 */
	private static String getProperty(String propertyName) {
		if (properties == null) {
			properties = initProperties();
		}
		return properties.getProperty(propertyName);
	}

	/**
	 * 获取参数值
	 * @param propertyName
	 * @param defaultValue
	 * @return
	 */
	private static String getProperty(String propertyName, String defaultValue) {
		if (properties == null) {
			properties = initProperties();
		}
		return properties.getProperty(propertyName, defaultValue);
	}

}
