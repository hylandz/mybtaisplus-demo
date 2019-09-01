package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.entity.ErrorLog;

public interface ErrorLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(ErrorLog record);

    int insertSelective(ErrorLog record);

    ErrorLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(ErrorLog record);

    int updateByPrimaryKey(ErrorLog record);
}