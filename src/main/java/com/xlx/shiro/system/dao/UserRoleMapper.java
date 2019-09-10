package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
    
    
    
    int insertBatch(@Param("userRoleList") List<UserRole> userRoleList);
    
    int updateByBatch(@Param("userRoleList") List<UserRole> userRoleList);
    int deleteByBatch(@Param("userIdList")List<Long> userIdList);
    int deleteByUserId(@Param("userId") Long userId);
    
    
}