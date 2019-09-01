package com.xlx.shiro.entity;

import java.io.Serializable;
import java.util.Date;

public class LoginLog implements Serializable {
    private Long logId;

    private String account;

    private String requestUri;

    private String userAgent;

    private String method;

    private String message;

    private String ip;

    private Date gmtCreate;

    public LoginLog(Long logId, String account, String requestUri, String userAgent, String method, String message, String ip, Date gmtCreate) {
        this.logId = logId;
        this.account = account;
        this.requestUri = requestUri;
        this.userAgent = userAgent;
        this.method = method;
        this.message = message;
        this.ip = ip;
        this.gmtCreate = gmtCreate;
    }

    public LoginLog() {
        super();
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri == null ? null : requestUri.trim();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent == null ? null : userAgent.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}