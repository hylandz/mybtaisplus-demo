package com.xlx.shiro.system.service;

import com.xlx.shiro.common.util.ShiroUtil;
import com.xlx.shiro.system.dao.UserMapper;
import com.xlx.shiro.system.dto.ProfileDTO;
import com.xlx.shiro.system.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * service:user
 *
 * @author xielx on 2019/7/13
 */
@Service
public class UserService {

  @Autowired
  private UserMapper userMapper;


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
   * @return 匹配:true
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
  public User findUserByUserName(String userName){
    if (StringUtils.isEmpty(userName)){
      return null;
    }
    return userMapper.selectUserByUserName(userName);
  }


  /**
   * 用户id查询用户
   * @param userId 用户id
   * @return User
   */
  public User findUserByUserName(Long userId){
    return userMapper.selectByPrimaryKey(userId);
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
  public List<User> listUserByPage(User user){
    return userMapper.selectUserByPage(user);
  }
  
  /**
   * 个人信息
   * @param userId 登录用户id
   * @return
   */
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
}

