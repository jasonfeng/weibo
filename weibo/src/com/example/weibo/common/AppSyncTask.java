/*
 * 新浪微博系统
 * Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.os.Process;

/**
 	自定义异步业务类，遵循官方API定义此类为所有异步任务继承类，内部实现线程池<br>
 	功能：<br>
	实现自定线程池,核心线程池大小为5，最大线程池为128，等待队列大小为10<br>
	使用方法：<br>
	子类在使用时仅需复写doInBackground方法，并在其中实现自有业务逻辑，然后调用execute方法即可有线程池执行具体任务<br>
 * @author  <a href="mailto:fengzhongtian@163.com">Jason Feng</a>
 * @version 1.0
 * @since 2013年8月28日
 */
public abstract class AppSyncTask implements Runnable {
	/** 核心线程数 */
	private static final int CORE_POOL_SIZE = 5;

	/** 最大线程数 */
	private static final int MAXIMUM_POOL_SIZE = 128;

	/** 超时时间，当线程数超过核心线程数时，超过这个时间的空线程就会被销毁，直到线程数等于核心线程 */
	private static final int KEEP_ALIVE = 1;

	/** 用于传输和保持提交的任务。可以使用此队列与池大小进行交互 */
	private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(10);

	/** Executor */
	public static final Executor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
			TimeUnit.SECONDS, sPoolWorkQueue);

	private boolean isCanceled;

	/**
	 * 调用线程池执行线程
	 * @see [类、类#方法、类#成员]
	 */
	public void execute() {
		sExecutor.execute(this);
	}

	/**
	 * 重载方法
	 */
	@Override
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
		doInBackground();
	}

	public void cancel() {
		isCanceled = true;
		Thread.interrupted();
	}

	/**
	 * @return Returns the isCanceled.
	 */
	public boolean isCanceled() {
		return isCanceled;
	}

	/**
	 * 抽象方法，子类实现具体业务逻辑
	 */
	protected abstract void doInBackground();
}
