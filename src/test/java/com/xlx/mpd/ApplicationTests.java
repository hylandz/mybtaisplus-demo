package com.xlx.mpd;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xlx.mpd.system.dao.UserMapper;
import com.xlx.mpd.system.entity.User;
import com.xlx.mpd.system.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

  @Autowired
  private UserService userService;
  
  
  @Test
  public void testSelect() {
  
    System.out.println("----------selectAll method test---------------");
      User user = userService.getOne(new QueryWrapper<User>().eq("user_name", "admin"));
      Assert.assertNotNull(user);
      user.setAge(18);
      System.out.println(user);
  }

}
