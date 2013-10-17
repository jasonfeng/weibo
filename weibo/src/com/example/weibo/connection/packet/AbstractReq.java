package com.example.weibo.connection.packet;

/**
 * 请求消息抽象类<br>
 * usage:<br>
 * 1.所有消息交互实体均需实现该抽象类，提供抽象方法packetMsgBody()，对消息内容进行打包<br>
 * 2.对于delete请求和get请求可根据实际情况实现setReuqestUri()方法和空的packetMsgBody()方法;<br>
 * 3.对于post和put请求，实现方式一致，需实现setReuqestUri()和packetMsgBody()；<br>
 * 4.实现setReuqestUri方法设置访问资源变量reuqestUri
 * 
 * @author <a href="mailto:fengzhongtian@163.com">Jason Feng</a>
 * @version 1.0
 * @since 2013年10月16日
 */
public abstract class AbstractReq
{
    
    /** 标志请求的资源 */
    String reuqestUri;
    
    /** 服务端分配终端用户的最新access_token信息，在HTTP协议的Header中出现 */
    String accessToken;
    
    /** 采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。 */
    String source;
    
    /**
     * 获取请求的资源
     * 
     * @return Returns the reuqestUri.
     */
    public String getReuqestUri()
    {
        return ("/" + reuqestUri + "/").replace("//", "/");
    }
    
    /**
     * 获取source
     * 
     * @return the source
     */
    public String getSource()
    {
        return source;
    }
    
    /**
     * 设置source
     * 
     * @param source the source to set
     */
    public void setSource(String source)
    {
        this.source = source;
    }
    
    /**
     * 设置最新access_token信息
     * 
     * @return Returns the accessToken.
     */
    public String getAccessToken()
    {
        return accessToken;
    }
    
    /**
     * 获取最新access_token信息
     * 
     * @param accessToken The accessToken to set.
     */
    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }
    
    /**
     * 获取打包消息内容
     * 
     * @return
     */
    public String getPackage()
    {
        setReuqestUri();
        return packetMsgBody();
    }
    
    /**
     * 打包请求消息体
     * 
     * @return
     */
    abstract String packetMsgBody();
    
    /**
     * 设置请求的资源
     */
    abstract void setReuqestUri();
}
