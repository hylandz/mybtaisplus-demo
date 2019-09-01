package com.xlx.shiro.dao;

import com.xlx.shiro.entity.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Long role_id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long role_id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}