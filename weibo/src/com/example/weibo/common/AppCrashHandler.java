/*
 * 新浪微博系统
 * Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.common;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * 应用崩溃Hanlder
 * 
 * @author fengzhongtian@163.com
 * @version 1.0
 * @since 2013年8月28日
 */
public final class AppCrashHandler implements UncaughtExceptionHandler
{
    /** 单例对象 */
    private static AppCrashHandler appCrashHandler = new AppCrashHandler();
    
    /** 上下文对象 */
    private Context context;
    
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    
    private Thread.UncaughtExceptionHandler threadEx;
    
    /** 私有化构造方法 */
    private AppCrashHandler()
    {
    }
    
    /**
     * 获取单例对象
     * 
     * @return
     */
    public static AppCrashHandler getInstance()
    {
        if (appCrashHandler == null)
            appCrashHandler = new AppCrashHandler();
        
        return appCrashHandler;
        
    }
    
    /**
     * @param ctx
     */
    public void init(Context ctx)
    {
        this.context = ctx;
        threadEx = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    
    @Override
    public void uncaughtException(Thread arg0, Throwable arg1)
    {
        if (!handleException(arg1) && null != threadEx)
            threadEx.uncaughtException(arg0, arg1);
    }
    
    private boolean handleException(final Throwable ex)
    {
        if (ex == null)
        {
            return true;
        }
        // 1.获取当前程序的版本号. 版本的id
        String versioninfo = getVersionInfo();
        // 2.获取手机的硬件信息.
        String mobileInfo = getMobileInfo();
        // 4.把所有的信息 还有信息对应的时间 提交到服务器
        Logger.e(AppCrashHandler.class,
            "[自定义异常类]:日期：",
            dataFormat.format(new Date()),
            "程序版本号：",
            versioninfo,
            "手机硬件信息：",
            mobileInfo,
            "未捕获的异常信息：",
            ex);
        return true;
    }
    
    /**
     * 获取手机的硬件信息
     * 
     * @return
     */
    private String getMobileInfo()
    {
        StringBuffer sb = new StringBuffer();
        // 通过反射获取系统的硬件信息
        try
        {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields)
            {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        }
        catch (Exception e)
        {
            Logger.e(AppCrashHandler.class, e);
        }
        return sb.toString();
    }
    
    /**
     * 获取手机的版本信息
     * 
     * @return
     */
    private String getVersionInfo()
    {
        try
        {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        }
        catch (Exception e)
        {
            return "版本号未知";
        }
    }
}
