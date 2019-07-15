package com.xlx.ssmshiro.dao;

import com.xlx.ssmshiro.entity.Resource;

public interface ResourceMapper {
    int deleteByPrimaryKey(Long resourceId);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Long resourceId);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
}