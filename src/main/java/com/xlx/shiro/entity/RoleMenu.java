package com.xlx.shiro.entity;

import java.io.Serializable;

public class RoleMenu implements Serializable {
    private Long id;

    private Long roleId;

    private Long menuId;

    public RoleMenu(Long id, Long roleId, Long menuId) {
        this.id = id;
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public RoleMenu() {
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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}