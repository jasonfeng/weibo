/*
 * 新浪微博系统
 * Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.common;

import android.app.Application;

/**
 * 应用程序管理类
 * @author fengzhongtian@163.com
 * @version 1.0
 * @since 2013年8月28日
 */
public final class AppApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		AppCrashHandler handler = AppCrashHandler.getInstance();
		handler.init(getApplicationContext());
	}

}
