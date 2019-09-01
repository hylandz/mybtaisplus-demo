package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.entity.Menu;

public interface ResourceMapper {
    int deleteByPrimaryKey(Long resourceId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long resourceId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}