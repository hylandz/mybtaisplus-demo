package com.xlx.shiro.system.entity;

import java.io.Serializable;
import java.util.Date;

public class LogLogin implements Serializable {
    private Long id;

    private String account;

    private String ip;

    private String browser;

    private String os;

    private String message;

    private Date gmtCreate;

    public LogLogin(Long id, String account, String ip, String browser, String os, String message, Date gmtCreate) {
        this.id = id;
        this.account = account;
        this.ip = ip;
        this.browser = browser;
        this.os = os;
        this.message = message;
        this.gmtCreate = gmtCreate;
    }

    public LogLogin() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser == null ? null : browser.trim();
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os == null ? null : os.trim();
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