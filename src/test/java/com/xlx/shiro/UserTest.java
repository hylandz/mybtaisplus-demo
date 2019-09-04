package com.xlx.shiro;

import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * user测试
 *
 * @author xielx on 2019/7/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {


  @Autowired
  private UserService userService;
  @Test
  public void testBase64(){
     String str = "西红柿炒鸡蛋";
    String SECRET = Base64.encodeBase64String(str.getBytes());
    System.out.println("编码" + SECRET);
    System.out.println("解码:" + new String(Base64.decodeBase64(SECRET)));
    byte[] encodeKey = Base64.decodeBase64(SECRET);
    SecretKey secretKey = new SecretKeySpec(encodeKey,0,encodeKey.length,"AES");
    System.out.println("SecretKey:" + secretKey);

  }

  @Test
  public void testUserService(){
    User user = userService.findUserByUserName("admin");
    System.out.println(user);
  }
  
  
}
