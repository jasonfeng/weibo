/*
 * 新浪微博系统 Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.connection;

/**
 * http 响应包装类
 * 
 * @author <a href="mailto:fengzhongtian@163.com">Jason Feng</a>
 * @version 1.0
 * @since 2013年10月16日
 */
public class HttpRes {

	/** 忘了异常 */
	public static final String NETWORK_EXCEPTION_PROPMT = "网络连接异常，请检查您的网络";

	/** 状态码 */
	private int statusCode;

	/** 返回内容 */
	private String content;

	/** 是否异常 */
	private boolean isException = false;

	/**
	 * 是否返回成功<br>
	 * 条件：<br>
	 * 1.没有异常；<br>
	 * 2.返回状态码为200或204或304或content不为空<br>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public boolean isSuccess() {
		if (!isException && statusCode >= 200 && statusCode <= 300 && content.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取 statusCode
	 * 
	 * @return 返回 statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * 获取失败信息
	 * 
	 * @return 失败信息
	 */
	public String getFailInfo() {
		return content;
	}

	/**
	 * 设置 statusCode
	 * 
	 * @param 对statusCode进行赋值
	 */
	void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 获取 content
	 * 
	 * @return 返回 content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置 content
	 * 
	 * @param 对content进行赋值
	 */
	void setContent(String content) {
		this.content = content;
	}

	/**
	 * 设置 isException
	 * 
	 * @param 对isException进行赋值
	 */
	void setException(boolean isException) {
		this.isException = isException;
	}

	/**
	 * 获取 isException
	 * 
	 * @return 返回 isException
	 */
	public boolean isException() {
		return isException;
	}

}
