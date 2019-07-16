package com.xlx.ssmshiro.mybatisplus;

import com.xlx.ssmshiro.dao.UsersMapper;
import com.xlx.ssmshiro.entity.Users;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * mapper接口测试
 *
 * @author xielx on 2019/7/16
 */
public class UserMapperTest {

  ApplicationContext context = null;

  UsersMapper usersMapper = null;
  @Before
  public void init(){
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    usersMapper = context.getBean("usersMapper",UsersMapper.class);
  }
  @Test
  public void testGetUserByAccount(){
    Users users = new Users();
    users.setUserAccount("admin");
    Users u =usersMapper.selectOne(users);
    System.out.println(u);
  }
}
