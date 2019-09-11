package com.xlx.shiro.system.dao;

import com.xlx.shiro.system.dto.ProfileDTO;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.entity.UserWithRole;
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
	
	/****************************
	 *         your method      *
	 * **************************
	 */
	List<User> selectUserByPage(User user);
	
	User selectUserByUserName(@Param("userName") String userName);
	
	int updatePassword(@Param("userId") Long userId, @Param("salt") String salt, @Param("newPwd") String newPwd);
	
	ProfileDTO selectProfileByUserId(Long userId);
	
	int updateAvatarUrl(@Param("userId") Long userId,@Param("avatarUrl") String avatarUrl);
	
	List<UserWithRole> selectUserById(@Param("userId") Long userId);
	
	int deleteByBatch(@Param("ids") Long[] ids);
}