package com.xlx.ssmpshiro.mybatisplus;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlx.ssmpshiro.system.dao.UserMapper;
import com.xlx.ssmpshiro.system.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * mapper接口测试
 *
 * @author xielx on 2019/7/16
 */
public class UserMapperTest {

  ApplicationContext context = null;

  UserMapper usersMapper = null;
  @Before
  public void init(){
    context = new ClassPathXmlApplicationContext("applicationContext.xml");
    usersMapper = context.getBean("usersMapper", UserMapper.class);
  }
  @Test
  public void testGetUserByAccount(){
    User users = new User();
    users.setUserId(3L);
    users.setUserPassword("123456");
    Integer r = usersMapper.updateById(users);
    System.out.println(r);
  }


  /**
   * 测试分析插件
   * Explain:
   * 带条件操作:Using where
   * 全表操作:Using all
   */

  @Test
  public void testSQLExplain(){
    //全表操作抛异常
    usersMapper.delete(null);

  }

  /**
   * 测试分页插件
   */
  @Test
  public void testPagePagePlugin(){
   // usersMapper.selectPage(new Page<User>(1,2),null).forEach(System.out :: println);

    Page<User> page = new Page<>(1,2);
    Wrapper<User> usersWrapper = new EntityWrapper<>();
    usersWrapper.like("user_account","G");
    List<User> usersList = usersMapper.selectPage(page,usersWrapper);
    page.setRecords(usersList);
    System.out.println("===================Page==============");
    System.out.println("totalCount:" + page.getTotal());
    System.out.println("totalPage:" + page.getPages());
    System.out.println("currPage:" + page.getCurrent());
    System.out.println("pageSize:" + page.getSize());
    System.out.println("hasPrevious:" + page.hasPrevious());
    System.out.println("hasNext:" + page.hasNext());
    System.out.println("分页记录:" + page.getRecords());

  }
}
