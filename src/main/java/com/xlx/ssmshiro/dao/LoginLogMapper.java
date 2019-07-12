package com.xlx.ssmshiro.dao;

import com.xlx.ssmshiro.entity.LoginLog;

public interface LoginLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(LoginLog record);

    int insertSelective(LoginLog record);

    LoginLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(LoginLog record);

    int updateByPrimaryKey(LoginLog record);
}