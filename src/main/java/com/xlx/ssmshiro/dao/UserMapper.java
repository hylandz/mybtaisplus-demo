package com.xlx.ssmshiro.dao;

import com.xlx.ssmshiro.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    User selectUserByUserAccount(@Param("userAccount") String userAccount);

    Set<String> selectPermissionsByUserAccount(@Param("userAccount") String userAccount);

    Set<String> selectRolesByUserAccount(@Param("userAccount") String userAccount);

}