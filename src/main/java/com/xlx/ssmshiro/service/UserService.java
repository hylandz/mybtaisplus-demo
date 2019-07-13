package com.xlx.ssmshiro.service;

import com.xlx.ssmshiro.dao.UserMapper;
import com.xlx.ssmshiro.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * service:user
 *
 * @author xielx on 2019/7/13
 */
@Service
public class UserService {

  @Resource
  private UserMapper userMapper;


  /**
   * 根据帐号获取用户
   * @param account 登录帐号
   * @return user
   */
  public User findUserByUserAccount(String account){
    return userMapper.selectUserByUserAccount(account);
  }




}
