package com.xlx.shiro.system.service;

import com.xlx.shiro.common.util.ShiroUtil;
import com.xlx.shiro.system.dao.UserMapper;
import com.xlx.shiro.system.dto.ProfileDTO;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.entity.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * service:user
 *
 * @author xielx on 2019/7/13
 */
@Service
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);
  
  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserRoleService userRoleService;
  /**
   * 用户新增
   * @param user 用户
   * @return 0:false; !=0:true
   */
  public boolean createUser(User user){
    user.setSalt(ShiroUtil.getHexRandomNumber());
    user.setGmtCreate(new Date());
    return userMapper.insert(user) != 0;
  }


  /**
   * 修改密码
   * @param userId 用户id
   * @param newPwd 新密码
   * @return 0:fasle
   */
  public Boolean modifyPassword(User user,String newPwd){
    Long userId = user.getUserId();
    String slat = ShiroUtil.getHexRandomNumber();
    user.setSalt(slat);
    final String encryptPassword = ShiroUtil.encryptPassword(newPwd, user.getCredentialsSalt());
    return userMapper.updatePassword(userId,slat,encryptPassword) != 0;
  }

  /**
   * 验证原始密码
   * @param originPwd 原始密码
   * @return 正确:true
   */
  public Boolean verifyPassword(String originPwd){
    User currentUser = (User) ShiroUtil.getSubject().getPrincipal();
    if (currentUser == null){
      return false;
    }

    //明文加密后比较
    String encryptPwd = ShiroUtil.encryptPassword(originPwd,currentUser.getCredentialsSalt());
    
    if (encryptPwd.equals(currentUser.getUserPassword())){
      return true;
    }
  
    return false;
  }

  /**
   * 根据帐号获取用户
   * @param userName 登录帐号
   */
  @Transactional(readOnly = true)
  public User findUserByUserName(String userName){
    if (StringUtils.isEmpty(userName)){
      return null;
    }
    return userMapper.selectUserByUserName(userName);
  }

  
  /**
   * 登录成功更新登录时间
   * @param userName .
   * @param loginDate .
   */
  public Boolean recordLoginTime(String userName){
    return userMapper.updateLoginDate(userName) != 0;
  }
  
  /**
   * 分页
   * @param user User
   * @return list
   */
  @Transactional(readOnly = true)
  public List<User> listUserByPage(User user){
    return userMapper.selectUserByPage(user);
  }
  
  /**
   * 个人信息
   * @param userId 登录用户id
   * @return
   */
  @Transactional(readOnly = true)
  public ProfileDTO getProfile(Long userId){
    return this.userMapper.selectProfileByUserId(userId);
  }
  
  /**
   * 修改profile
   * @param profile
   * @return
   */
  public Boolean editProfile(ProfileDTO profile){
    User user = new User();
    BeanUtils.copyProperties(profile,user);
    return userMapper.updateByPrimaryKeySelective(user) != 0;
  }
  
  /**
   * 更改头像
   * @param avatarUrl 图片url
   * @return boolean
   */
  public Boolean changAvatar(Long id,String url){
    return userMapper.updateAvatarUrl(id,url) != 0;
  }
  
  
  /**
   * 根据用户id查询用户
   * @param userId 用户id
   * @return user
   */
  public User findUserByPrimaryKey(Long userId){
    if (userId == null){
      return new User();
    }
    return userMapper.selectByPrimaryKey(userId);
  }
  
  /**
   * 用户新增
   * tip:
   *   用户新增涉及包含:用户新增+角色新增,应该满足事务的原子性
   *   不能出现单方面的成功问题
   * @param user User
   * @return boolean
   */
  @Transactional(rollbackFor = Exception.class)
  public Boolean saveUser(User user,Long[] roleIds){
    if (user == null && roleIds.length == 0){
      return false;
    }
    
    user.setSalt(ShiroUtil.getHexRandomNumber());
    user.setGmtCreate(new Date());
    user.setAvatarName(User.DEFAULT_AVATAR_NAME + new Random().nextInt(10));
    user.setAvatarUrl(User.DEFAULT_AVATAR_URL);
    String encrypt = ShiroUtil.encryptPassword(user.getUserPassword(),user.getCredentialsSalt());
    user.setUserPassword(encrypt);
    this.userMapper.insertSelective(user);
      //
      List<UserRole> collection = setUserRoles(user, roleIds);
      Boolean result = userRoleService.addByBatch(collection);
      return result;
  }
  
  private List<UserRole> setUserRoles(User user, Long[] roleIds){
    long start = System.currentTimeMillis();
    List<UserRole> collect = Arrays.stream(roleIds).map(roleId -> {
      UserRole userRole = new UserRole();
      userRole.setUserId(user.getUserId());
      userRole.setRoleId(roleId);
      return userRole;
    }).collect(Collectors.toList());
    long end = System.currentTimeMillis() - start;
    log.info("stram cost time is : {}",end);
    return  collect;
  }
}

