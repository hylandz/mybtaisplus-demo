package com.xlx.shiro.system.entity;

import java.io.Serializable;
import java.util.Date;

public class LogError implements Serializable {
    private Long id;

    private String requestUri;

    private String method;

    private String userAgent;

    private String ip;

    private String ipLocation;

    private String message;

    private Date gmtCreate;

    public LogError(Long id, String requestUri, String method, String userAgent, String ip, String ipLocation, String message, Date gmtCreate) {
        this.id = id;
        this.requestUri = requestUri;
        this.method = method;
        this.userAgent = userAgent;
        this.ip = ip;
        this.ipLocation = ipLocation;
        this.message = message;
        this.gmtCreate = gmtCreate;
    }

    public LogError() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri == null ? null : requestUri.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent == null ? null : userAgent.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getIpLocation() {
        return ipLocation;
    }

    public void setIpLocation(String ipLocation) {
        this.ipLocation = ipLocation == null ? null : ipLocation.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}