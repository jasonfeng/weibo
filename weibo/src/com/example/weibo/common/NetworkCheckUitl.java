/*
 * 新浪微博系统
 * Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 检查网络设置情况
 * @author  <a href="mailto:fengzhongtian@163.com">Jason Feng</a>
 * @version 1.0
 * @since 2013年9月1日
 */
public final class NetworkCheckUitl {

	private NetworkCheckUitl() {
	}

	/**
	 * 判断设备是否网络在线
	 * @return true:在线,false：不在线
	 */
	public static boolean isOnline() {
		Context context = AppApplication.getApplication();
		if (context != null) {
			ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			return (networkInfo != null && networkInfo.isConnected());
		} else {
			return false;
		}
	}

	/**
	 * 判断设备是否网络在线
	 * @return true:在线,false：不在线
	 */
	public static boolean is3GOnline() {
		Context context = AppApplication.getApplication();
		if (context != null) {
			ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			return (networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
		} else {
			return false;
		}
	}
}
