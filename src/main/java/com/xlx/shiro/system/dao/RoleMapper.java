package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.entity.Role;
import com.xlx.shiro.system.entity.RoleWithMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    Set<String> selectRoleKeyByUserName(@Param("userName") String userName);
    
    List<Role> selectAllRoles(@Param("roleName")String roleName);
    
    
    Role selectRoleKey(@Param("roleKey") String roleKey);
    
    int deleteByBatch(@Param("roleArray") Long[] roleArray);
    
    List<RoleWithMenu> selectRole(@Param("roleId") Long roleId);
    
}