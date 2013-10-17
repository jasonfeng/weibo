/*
 * 新浪微博系统 Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.common;

/**
 * 全局长常量类
 * 
 * @author <a href="mailto:fengzhongtian@163.com">Jason Feng</a>
 * @version 1.0
 * @since 2013年9月1日
 */
public final class GlobalConstant
{
    
    /** 应用的key 请到官方申请正式的appkey替换APP_KEY */
    public static final String APP_KEY = "266282488";
    
    /** 替换为开发者REDIRECT_URL */
    public static final String REDIRECT_URL = "http://weibo.com";
    
    /** 新支持scope 支持传入多个scope权限，用逗号分隔 */
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
        + "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog,"
        + "invitation_write";
}
