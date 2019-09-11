package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);
    
    
    int insertByBatch(@Param("roleId") Long roleId, @Param("menuIdArray") Long[] menuIdArray);
    
}