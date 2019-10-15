package com.xlx.ssmpshiro.system.dao;

import com.xlx.ssmpshiro.system.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xlx
 * @since 2019-07-16
 */
public interface UserMapper extends BaseMapper<User> {
  /**
   * *******************
   * 基本的CRUD不用写
   * *******************
   */


  Set<String> selectAllRoles(String userAccount);
  Set<String> selectAllPermissions(String userAccount);


}
