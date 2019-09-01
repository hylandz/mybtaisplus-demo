package com.xlx.shiro.service;

import com.xlx.shiro.common.util.ShiroUtil;
import com.xlx.shiro.dao.UserMapper;
import com.xlx.shiro.entity.User;
import org.apache.commons.lang3.StringUtils;
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
  public boolean modifyPassword(Long userId,String newPwd){
    String slat = ShiroUtil.getHexRandomNumber();
   return userMapper.updatePassword(userId,slat,newPwd) != 0;
  }

  /**
   * 验证原始密码
   * @param originPwd 原始密码
   * @return 匹配:true
   */
  public boolean verifyPassword(String originPwd){
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
   * 根据帐号获取该用户的权限集
   * @param userName 登录帐号
   */
  public Set<String> getPermissions(String userName){
    return userMapper.selectPermissionsByUserName(userName);
  }

  /**
   * 根据帐号获取该用户的角色集
   * @param userName 登录帐号
   */
  public Set<String> getRoles(String userName){
    return userMapper.selectRolesByUserName(userName);
  }

  /**
   * 登录成功更新登录时间
   * @param userName .
   * @param loginDate .
   */
  public boolean recordLoginDate(String userName, Date loginDate){
    return userMapper.updateLoginDate(userName,loginDate) != 0;
  }

  /**
   * 分页
   * @param offset 偏移量
   * @param size 笔数
   * @return list
   */
  public List<User> listUserPage(Integer offset,Integer size){
    return userMapper.selectUserByPage(offset,size);
  }
}

