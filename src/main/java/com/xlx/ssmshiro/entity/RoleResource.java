package com.xlx.ssmshiro.entity;

import java.io.Serializable;

public class RoleResource implements Serializable {
    private Long id;

    private Long roleId;

    private Long resourceId;

    public RoleResource(Long id, Long roleId, Long resourceId) {
        this.id = id;
        this.roleId = roleId;
        this.resourceId = resourceId;
    }

    public RoleResource() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}