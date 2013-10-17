/*
 * 新浪微博系统 Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.common;

import android.app.Application;

/**
 * 应用程序管理类
 * 
 * @author fengzhongtian@163.com
 * @version 1.0
 * @since 2013年8月28日
 */
public final class AppApplication extends Application {

	private static Application application;

	/** HTTP链接URL */
	public static final String HTTP_SERVER_URL = SysConfigUtil.getHttpServerUrl();

	/** HTTP链接超时时间 */
	public static final int HTTP_CONN_TIMEOUT = SysConfigUtil.getHttpConnTimeout();

	/** HTTP链接接收超时时间 */
	public static final int HTTP_RECV_TIMEOUT = SysConfigUtil.getHttpRecvTimeout();

	/** HTTP信息最大重发次数 */
	public static final int HTTP_RESEND_COUNT = SysConfigUtil.getHttpMaxResendCount();

	/** 消息编码类型 */
	public static final String CHARSET = SysConfigUtil.getCharset();

	/** 是否能写日志 */
	private static boolean isCanWriteLog = true;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		AppCrashHandler handler = AppCrashHandler.getInstance();
		handler.init(getApplicationContext());
	}

	/**
	 * 获取Application对象
	 * 
	 * @return
	 */
	public static Application getApplication() {
		return application;
	}

	public static boolean isCanWriteLog() {
		return isCanWriteLog;
	}

	public static void setCanWriteLog(boolean isCanWriteLog) {
		AppApplication.isCanWriteLog = isCanWriteLog;
	}

}
