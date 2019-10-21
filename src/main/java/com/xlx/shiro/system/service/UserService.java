package com.xlx.shiro.system.service;

import com.xlx.shiro.common.exception.CustomizeException;
import com.xlx.shiro.common.util.ShiroUtils;
import com.xlx.shiro.system.dao.UserMapper;
import com.xlx.shiro.system.dao.UserRoleMapper;
import com.xlx.shiro.system.dto.ProfileDTO;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.entity.UserRole;
import com.xlx.shiro.system.entity.UserWithRole;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * service:user
 *
 * @author xielx on 2019/7/13
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	/**
	 * 用户新增
	 *
	 * @param user 用户
	 * @return 0:false; !=0:true
	 */
	public boolean createUser(User user) {
		user.setSalt(ShiroUtils.getHexRandomNumber());
		user.setGmtCreate(new Date());
		return userMapper.insert(user) != 0;
	}
	
	
	/**
	 * 修改密码
	 *
	 * @param userId 用户id
	 * @param newPwd 新密码
	 * @return 0:fasle
	 */
	public Boolean modifyPassword(User user, String newPwd) {
		Long userId = user.getUserId();
		String slat = ShiroUtils.getHexRandomNumber();
		user.setSalt(slat);
		final String encryptPassword = ShiroUtils.encryptPassword(newPwd, user.getCredentialsSalt());
		return userMapper.updatePassword(userId, slat, encryptPassword) != 0;
	}
	
	/**
	 * 验证原始密码
	 *
	 * @param originPwd 原始密码
	 * @return 正确:true
	 */
	public Boolean verifyPassword(String originPwd) {
		User currentUser = (User) ShiroUtils.getSubject().getPrincipal();
		if (currentUser == null) {
			return false;
		}
		
		//明文加密后比较
		String encryptPwd = ShiroUtils.encryptPassword(originPwd, currentUser.getCredentialsSalt());
		
		if (encryptPwd.equals(currentUser.getUserPassword())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 根据帐号获取用户
	 *
	 * @param userName 登录帐号
	 */
	@Transactional(readOnly = true)
	public User findUserByUserName(String userName) {
		if (StringUtils.isEmpty(userName)) {
			return null;
		}
		return userMapper.selectUserByUserName(userName);
	}
	
	
	/**
	 * 登录成功更新登录时间
	 *
	 * @param userName  .
	 * @param loginDate .
	 */
	public Boolean recordLoginTime(String userName) {
		return userMapper.updateLoginDate(userName) != 0;
	}
	
	/**
	 * 分页
	 *
	 * @param user User
	 * @return list
	 */
	@Transactional(readOnly = true)
	public List<User> listUserByPage(User user) {
		return userMapper.selectUserByPage(user);
	}
	
	/**
	 * 个人信息
	 *
	 * @param userId 登录用户id
	 * @return
	 */
	@Transactional(readOnly = true)
	public ProfileDTO getProfile(Long userId) {
		return this.userMapper.selectProfileByUserId(userId);
	}
	
	/**
	 * 修改profile
	 *
	 * @param profile
	 * @return
	 */
	@Transactional
	public Boolean editProfile(ProfileDTO profile) {
		User user = new User();
		BeanUtils.copyProperties(profile, user);
		return userMapper.updateByPrimaryKeySelective(user) != 0;
	}
	
	/**
	 * 更改头像
	 *
	 * @param avatarUrl 图片url
	 * @return boolean
	 */
	@Transactional
	public Boolean changAvatar(Long id, String url) {
		return userMapper.updateAvatarUrl(id, url) != 0;
	}
	
	
	/**
	 * 根据用户id查询用户
	 *
	 * @param userId 用户id
	 * @return user
	 */
	public User findUserByPrimaryKey(Long userId) {
		if (userId == null) {
			return new User();
		}
		return userMapper.selectByPrimaryKey(userId);
	}
	
	/**
	 * 用户新增
	 * tip:
	 * 用户新增涉及包含:用户新增+角色新增,应该满足事务的原子性
	 * 不能出现单方面的成功问题
	 *
	 * @param user User
	 * @return boolean
	 */
	@Transactional
	public Boolean saveUser(User user, Long[] roleIds) {
		if (user == null && roleIds.length == 0) {
			return false;
		}
		
		user.setSalt(ShiroUtils.getHexRandomNumber());
		user.setGmtCreate(new Date());
		user.setAvatarName(User.DEFAULT_AVATAR_NAME + new Random().nextInt(10));
		user.setAvatarUrl(User.DEFAULT_AVATAR_URL);
		String encrypt = ShiroUtils.encryptPassword(user.getUserPassword(), user.getCredentialsSalt());
		user.setUserPassword(encrypt);
		//插入到数据库后,user变量数据不能共享?
		this.userMapper.insertSelective(user);
		//事务不起作用?
		//int i = 10 /0;
		
		//再通过用户名查询一遍,获取它的id
		user.setUserId(getUserIdWhenSetUserRole(user.getUserName()));
		//
		List<UserRole> collection = setUserRoles(user, roleIds);
		int result = userRoleMapper.insertBatch(collection);
		return result != 0;
	}
	
	private List<UserRole> setUserRoles(User user, Long[] roleIds) {
		List<UserRole> collect = Arrays.stream(roleIds).map(roleId -> {
			UserRole userRole = new UserRole();
			userRole.setUserId(user.getUserId());
			userRole.setRoleId(roleId);
			return userRole;
		}).collect(Collectors.toList());
		return collect;
	}
	
	private Long getUserIdWhenSetUserRole(String userName){
		final User user = userMapper.selectUserByUserName(userName);
		if (user == null){
			log.info("温馨提示:未找到用户名=[{}]的用户");
			throw new CustomizeException("未找到指定用户");
		}
		
		return user.getUserId();
		
	}
	
	/**
	 * 获取用户
	 * @param userId 用户id
	 * @return userrole
	 */
	@Transactional(readOnly = true)
	public UserWithRole findWithUserRole(Long userId){
		List<UserWithRole> withRoles = userMapper.selectUserById(userId);
		List<Long> roleIdList = withRoles.stream().map(UserWithRole::getRoleId).collect(Collectors.toList());
		if (withRoles == null){
		  return new UserWithRole();
		}
		UserWithRole userWithRole = withRoles.get(0);
		userWithRole.setRoleIdList(roleIdList);
		return userWithRole;
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @param roles
	 * @return
	 */
	public Boolean editUser(User user,Long[] roles){
		if (user == null && roles.length == 0) {
			return false;
		}
		
		//修改用户
		final int i = userMapper.updateByPrimaryKeySelective(user);
		log.info("editUser ->the effectRows of updating user is: {}",i);
		//批量删除原有角色
		final int i1 = userRoleMapper.deleteByUserId(user.getUserId());
		log.info("editUser ->the effectRows of deleting userRole is: {}",i1);
		//新角色插入
		final int i2 = userRoleMapper.insertBatch(setUserRoles(user, roles));
		log.info("editUser ->the effectRows of inserting userRole is: {}",i);
		return (i != 0 && i1!= 0 && i2!= 0);
	}
	
	/**
	 * 批量删除用户
	 * @param ids
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteUserByBatch(Long[] ids){
		if (ids.length == 0){
			return false;
		}
		final int i = userMapper.deleteByBatch(ids);
		log.info("删除用户--> effectRows:{}",i);
		//
		final int i1 = userRoleMapper.deleteByBatch(Arrays.asList(ids));
		log.info("删除用户角色--> effectRows:{}",i1);
		return i != 0 && i1 != 0;
	}
	
	
	/**
	 * 注册用户
	 * @param user 注册帐号
	 * @return true:注册成功
	 */
	public Boolean registerUser(User user){
		if (user != null){
			String salt = ShiroUtils.getHexRandomNumber();
			user.setSalt(salt);
			String password = ShiroUtils.encryptPassword(user.getUserPassword(), user.getCredentialsSalt());
			user.setUserPassword(password);
			user.setGmtModified(new Date());
			user.setLocked(Boolean.FALSE);
			int i = userMapper.insertSelective(user);
			return i != 0;
		}
		return false;
	}
}

