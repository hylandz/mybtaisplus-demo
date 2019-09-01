package com.xlx.shiro.dao;

import com.xlx.shiro.entity.Menu;

public interface ResourceMapper {
    int deleteByPrimaryKey(Long resourceId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long resourceId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}