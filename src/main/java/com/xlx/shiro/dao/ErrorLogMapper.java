package com.xlx.shiro.dao;

import com.xlx.shiro.entity.ErrorLog;

public interface ErrorLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(ErrorLog record);

    int insertSelective(ErrorLog record);

    ErrorLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(ErrorLog record);

    int updateByPrimaryKey(ErrorLog record);
}