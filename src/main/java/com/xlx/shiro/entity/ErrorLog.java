package com.xlx.shiro.entity;

import java.io.Serializable;

public class ErrorLog implements Serializable {
    private Long logId;

    private String uri;

    private String method;

    public ErrorLog(Long logId, String uri, String method) {
        this.logId = logId;
        this.uri = uri;
        this.method = method;
    }

    public ErrorLog() {
        super();
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }
}