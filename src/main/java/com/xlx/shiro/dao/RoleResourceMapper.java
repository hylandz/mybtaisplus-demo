package com.xlx.shiro.dao;

import com.xlx.shiro.entity.RoleMenu;

public interface RoleResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);
}