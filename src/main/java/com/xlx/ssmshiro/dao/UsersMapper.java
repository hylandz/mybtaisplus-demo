package com.xlx.ssmshiro.dao;

import com.xlx.ssmshiro.entity.Users;
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
public interface UsersMapper extends BaseMapper<Users> {
  /**
   * *******************
   * 基本的CRUD不用写
   * *******************
   */


  Set<String> selectAllRoles(String userAccount);
  Set<String> selectAllPermissions(String userAccount);


}
