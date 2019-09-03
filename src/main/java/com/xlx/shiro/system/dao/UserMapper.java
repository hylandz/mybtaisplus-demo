package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);



    int updateLoginDate(@Param("userName") String userName);

    List<User> selectUserByPage(@Param("offset") Integer offset,@Param("size") Integer size);


    User selectUserByUserName(@Param("userName") String userName);

    int updatePassword(@Param("userId") Long userId,@Param("salt") String salt,@Param("newPwd") String newPwd);
}