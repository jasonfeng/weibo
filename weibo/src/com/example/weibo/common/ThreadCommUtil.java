/*
 * 新浪微博系统
 * Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.common;

import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

/**
 * 线程通讯工具类，通过此工具类向UI发送状态码
 * @author fengzhongtian@163.com
 * @version 1.0
 * @since 2013年8月28日
 */
public final class ThreadCommUtil {
	private ThreadCommUtil() {
	}

	/**
	 * 向UI发送消息
	 * @param handler Handler对象，用于线程间交互
	 * @param msgWhat 消息What值
	 */
	public static void sendMsgToUI(Handler handler, int msgWhat) {
		if (handler != null) {
			Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",threadid:", Thread.currentThread().getId());
			Message message = handler.obtainMessage();
			message.what = msgWhat;
			handler.sendMessage(message);
		}
	}

	/**
	 * 向UI发送消息
	 * @param handler Handler对象，用于线程间交互
	 * @param msgWhat 消息What值
	 * @param msgObj 消息Obj值
	 */
	public static void sendMsgToUI(Handler handler, int msgWhat, Object msgObj) {
		if (handler != null) {
			if (msgObj instanceof String) {
				Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",obj:", msgObj);
			} else if (msgObj instanceof List) {
				Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",list size:", ((List<?>) msgObj).size());
			} else if (msgObj instanceof Map) {
				Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",map size:", ((Map<?, ?>) msgObj).size());
			} else {
				Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",obj:", msgObj);
			}

			Message message = handler.obtainMessage();
			message.what = msgWhat;
			message.obj = msgObj;
			handler.sendMessage(message);
		}
	}
}
