/*
 * 新浪微博系统 Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.connection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.example.weibo.common.AppApplication;
import com.example.weibo.common.Logger;
import com.example.weibo.common.NetworkCheckUitl;
import com.example.weibo.connection.packet.AbstractReq;

/**
 * HTTP REST消息发送工具类<br>
 * usage:<br>
 * 1.发送post消息HttpMsgSendUtil.sendPostMsg(msg);<br>
 * 2.发送get消息HttpMsgSendUtil.sendGetMsg(msg);<br>
 * 3.发送put消息HttpMsgSendUtil.sendPutMsg(msg);<br>
 * 4.发送delete消息HttpMsgSendUtil.sendDeleteMsg(msg);<br>
 * 
 * @author <a href="mailto:fengzhongtian@163.com">Jason Feng</a>
 * @version 1.0
 * @since 2013年10月16日
 */
public final class HttpMsgSendUtil
{
    
    private HttpMsgSendUtil()
    {
    }
    
    /**
     * 向服务器发送POST消息
     * 
     * @param msg 要发送的消息
     * @param maxSendCount 最大发送次数
     * @return HttpRes HTTP响应对象，其中包含成功失败信息，及成功时返回内容
     */
    public static HttpRes sendPostMsg(AbstractReq msg)
    {
        return sendPostMsg(msg, 1, 1);
    }
    
    /**
     * 向服务器发送消息
     * 
     * @param msg 消息内容
     * @param sendCount 发送次数 第一次为1
     * @param maxSendCount 最大发送次数
     * @return HttpRes
     */
    private static HttpRes sendPostMsg(AbstractReq msg, int sendCount, int maxSendCount)
    {
        HttpRes httpRes = new HttpRes();
        if (NetworkCheckUitl.isOnline())
        {
            HttpClient httpClient = new DefaultHttpClient();
            // 设置连接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                AppApplication.HTTP_CONN_TIMEOUT);
            // 设置接收超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, AppApplication.HTTP_RECV_TIMEOUT);
            
            HttpPost httpPost = null;
            try
            {
                String uri = AppApplication.HTTP_SERVER_URL + msg.getReuqestUri();
                httpPost = new HttpPost(uri);
                String msgContent = msg.getPackage();
                if (msgContent != null)
                {
                    httpPost.setEntity(new StringEntity(msgContent, AppApplication.CHARSET));
                }
                Logger.i(HttpMsgSendUtil.class,
                    "[HTTP REST消息发送工具类]：POST消息第",
                    sendCount,
                    "次发送，Request URI：",
                    uri,
                    "，Token：",
                    msg.getAccessToken(),
                    "，msgContent：",
                    msgContent);
                HttpResponse response = httpClient.execute(httpPost);
                
                httpRes = new HttpRes();
                httpRes.setStatusCode(response.getStatusLine().getStatusCode());
                if (response.getEntity() != null)
                {
                    httpRes.setContent(EntityUtils.toString((response.getEntity()), AppApplication.CHARSET));
                }
                Logger.i(HttpMsgSendUtil.class,
                    "[HTTP REST消息发送工具类]：POST消息响应，statusCode：",
                    httpRes.getStatusCode(),
                    "，content：",
                    httpRes.getContent(),
                    "，requestUri：",
                    msg.getReuqestUri());
                return httpRes;
            }
            catch (Exception e)
            {
                Logger.e(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：发送POST消息异常，详细信息：", e);
                int tempSendCount = sendCount;
                tempSendCount++;
                if (tempSendCount <= maxSendCount)
                {
                    return sendPostMsg(msg, tempSendCount, maxSendCount);
                }
                else
                {
                    httpRes.setException(true);
                    return httpRes;
                }
                
            }
            finally
            {
                if (httpPost != null)
                {
                    httpPost.abort();
                }
                if (httpClient != null)
                {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
        else
        {
            httpRes.setException(true);
            return httpRes;
        }
        
    }
    
    /**
     * 向服务器发送GET消息
     * 
     * @param msg 要发送的消息
     * @return HttpRes HTTP响应对象，其中包含成功失败信息，及成功时返回内容
     */
    public static HttpRes sendGetMsg(AbstractReq msg)
    {
        return sendGetMsg(AppApplication.HTTP_SERVER_URL, msg, 1, AppApplication.HTTP_RESEND_COUNT);
    }
    
    /**
     * 向服务器发送GET消息
     * 
     * @param serverUrl 服务端地址
     * @param msg 消息内容
     * @return HttpRes HTTP响应对象，其中包含成功失败信息，及成功时返回内容
     */
    public static HttpRes sendGetMsg(String serverUrl, AbstractReq msg)
    {
        return sendGetMsg(serverUrl, msg, 1, AppApplication.HTTP_RESEND_COUNT);
    }
    
    /**
     * 向服务器发送GET消息
     * 
     * @param msg 消息内容
     * @param sendCount 发送次数 第一次为1
     * @param maxSendCount 最大发送次数
     * @return HttpRes
     */
    private static HttpRes sendGetMsg(String serverUrl, AbstractReq msg, int sendCount, int maxSendCount)
    {
        HttpRes httpRes = new HttpRes();
        if (NetworkCheckUitl.isOnline())
        {
            HttpClient httpClient = new DefaultHttpClient();
            // 设置连接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                AppApplication.HTTP_CONN_TIMEOUT);
            // 设置接收超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, AppApplication.HTTP_RECV_TIMEOUT);
            
            HttpGet httpGet = null;
            try
            {
                String uri = serverUrl + msg.getReuqestUri();
                httpGet = new HttpGet(uri);
                Logger.i(HttpMsgSendUtil.class,
                    "[HTTP REST消息发送工具类]：GET消息第",
                    sendCount,
                    "次发送，Request URI：",
                    uri,
                    "，Token：",
                    msg.getAccessToken());
                HttpResponse response = httpClient.execute(httpGet);
                
                httpRes.setStatusCode(response.getStatusLine().getStatusCode());
                if (response.getEntity() != null)
                {
                    httpRes.setContent(EntityUtils.toString((response.getEntity()), AppApplication.CHARSET));
                }
                Logger.i(HttpMsgSendUtil.class,
                    "[HTTP REST消息发送工具类]：GET消息响应，statusCode：",
                    httpRes.getStatusCode(),
                    "，content：",
                    httpRes.getContent(),
                    "，requestUri：",
                    msg.getReuqestUri());
                return httpRes;
            }
            catch (Exception e)
            {
                Logger.e(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：发送Get消息异常，详细信息：", e);
                int tempSendCount = sendCount;
                tempSendCount++;
                if (tempSendCount <= maxSendCount)
                {
                    return sendGetMsg(serverUrl, msg, tempSendCount, maxSendCount);
                }
                else
                {
                    httpRes.setException(true);
                    return httpRes;
                }
                
            }
            finally
            {
                if (httpGet != null)
                {
                    httpGet.abort();
                }
                if (httpClient != null)
                {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
        else
        {
            httpRes.setException(true);
            return httpRes;
        }
    }
    
    /**
     * 向服务器发送PUT消息
     * 
     * @param msg 要发送的消息
     * @param maxSendCount 最大发送次数
     * @return HttpRes HTTP响应对象，其中包含成功失败信息，及成功时返回内容
     */
    public static HttpRes sendPutMsg(AbstractReq msg)
    {
        return sendPutMsg(msg, 1, 1);
    }
    
    /**
     * 向服务器发送PUT消息
     * 
     * @param msg 消息内容
     * @param sendCount 发送次数 第一次为1
     * @param maxSendCount 最大发送次数
     * @return HttpRes
     */
    private static HttpRes sendPutMsg(AbstractReq msg, int sendCount, int maxSendCount)
    {
        HttpRes httpRes = new HttpRes();
        if (NetworkCheckUitl.isOnline())
        {
            HttpClient httpClient = new DefaultHttpClient();
            // 设置连接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                AppApplication.HTTP_CONN_TIMEOUT);
            // 设置接收超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, AppApplication.HTTP_RECV_TIMEOUT);
            
            HttpPut httpPut = null;
            try
            {
                String uri = AppApplication.HTTP_SERVER_URL + msg.getReuqestUri();
                httpPut = new HttpPut(uri);
                String msgContent = msg.getPackage();
                httpPut.setEntity(new StringEntity(msgContent, AppApplication.CHARSET));
                Logger.i(HttpMsgSendUtil.class,
                    "[HTTP REST消息发送工具类]：PUT消息第",
                    sendCount,
                    "次发送，Request URI：",
                    uri,
                    "，Token：",
                    msg.getAccessToken(),
                    "，msgContent：",
                    msgContent);
                HttpResponse response = httpClient.execute(httpPut);
                
                httpRes.setStatusCode(response.getStatusLine().getStatusCode());
                if (response.getEntity() != null)
                {
                    httpRes.setContent(EntityUtils.toString((response.getEntity()), AppApplication.CHARSET));
                }
                Logger.i(HttpMsgSendUtil.class,
                    "[HTTP REST消息发送工具类]：PUT消息响应，statusCode：",
                    httpRes.getStatusCode(),
                    "，content：",
                    httpRes.getContent(),
                    "，requestUri：",
                    msg.getReuqestUri());
                return httpRes;
            }
            catch (Exception e)
            {
                Logger.e(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：发送PUT消息异常，详细信息：", e);
                int tempSendCount = sendCount;
                tempSendCount++;
                if (tempSendCount <= maxSendCount)
                {
                    return sendPutMsg(msg, tempSendCount, maxSendCount);
                }
                else
                {
                    httpRes.setException(true);
                    return httpRes;
                }
                
            }
            finally
            {
                if (httpPut != null)
                {
                    httpPut.abort();
                }
                if (httpClient != null)
                {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
        else
        {
            httpRes.setException(true);
            return httpRes;
        }
    }
    
    /**
     * 向服务器发送DELETE消息
     * 
     * @param msg 要发送的消息
     * @param maxSendCount 最大发送次数
     * @return HttpRes HTTP响应对象，其中包含成功失败信息，及成功时返回内容
     */
    public static HttpRes sendDeleteMsg(AbstractReq msg)
    {
        return sendDeleteMsg(msg, 1, AppApplication.HTTP_RESEND_COUNT);
    }
    
    /**
     * 向服务器发送DELETE消息
     * 
     * @param msg 消息内容
     * @param sendCount 发送次数 第一次为1
     * @param maxSendCount 最大发送次数
     * @return HttpRes
     */
    private static HttpRes sendDeleteMsg(AbstractReq msg, int sendCount, int maxSendCount)
    {
        HttpRes httpRes = new HttpRes();
        if (NetworkCheckUitl.isOnline())
        {
            HttpClient httpClient = new DefaultHttpClient();
            // 设置连接超时
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                AppApplication.HTTP_CONN_TIMEOUT);
            // 设置接收超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, AppApplication.HTTP_RECV_TIMEOUT);
            
            HttpDelete httpDelete = null;
            try
            {
                String uri = AppApplication.HTTP_SERVER_URL + msg.getReuqestUri();
                httpDelete = new HttpDelete(uri);
                Logger.i(HttpMsgSendUtil.class,
                    "[HTTP REST消息发送工具类]：DELETE消息第",
                    sendCount,
                    "次发送，Request URI：",
                    uri,
                    "，Token：",
                    msg.getAccessToken());
                HttpResponse response = httpClient.execute(httpDelete);
                
                httpRes.setStatusCode(response.getStatusLine().getStatusCode());
                if (response.getEntity() != null)
                {
                    httpRes.setContent(EntityUtils.toString((response.getEntity()), AppApplication.CHARSET));
                }
                Logger.i(HttpMsgSendUtil.class,
                    "[HTTP REST消息发送工具类]：DELETE消息响应，statusCode：",
                    httpRes.getStatusCode(),
                    "，content：",
                    httpRes.getContent(),
                    "，requestUri：",
                    msg.getReuqestUri());
                return httpRes;
            }
            catch (Exception e)
            {
                Logger.e(HttpMsgSendUtil.class, "[HTTP REST消息发送工具类]：发送DELETE消息异常，详细信息：", e);
                int tempSendCount = sendCount;
                tempSendCount++;
                if (tempSendCount <= maxSendCount)
                {
                    return sendDeleteMsg(msg, tempSendCount, maxSendCount);
                }
                else
                {
                    httpRes.setException(true);
                    return httpRes;
                }
                
            }
            finally
            {
                if (httpDelete != null)
                {
                    httpDelete.abort();
                }
                if (httpClient != null)
                {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }
        else
        {
            httpRes.setException(true);
            return httpRes;
        }
    }
}
