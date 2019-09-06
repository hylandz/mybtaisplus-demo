package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.entity.LogError;

public interface LogErrorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogError record);

    int insertSelective(LogError record);

    LogError selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogError record);

    int updateByPrimaryKey(LogError record);
}