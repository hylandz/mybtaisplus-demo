package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface MenuMapper {
    int deleteByPrimaryKey(Long resourceId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long resourceId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);


    Set<String> selectPermissionsByUserName(@Param("userName") String userName);
}