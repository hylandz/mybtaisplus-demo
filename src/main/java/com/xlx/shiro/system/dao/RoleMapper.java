package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface RoleMapper {
    int deleteByPrimaryKey(Long role_id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long role_id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);


    Set<String> selectRoleKeyByUserName(@Param("userName") String userName);
}