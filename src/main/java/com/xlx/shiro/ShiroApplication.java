package com.xlx.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xlx.shiro.system.dao")
public class ShiroApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShiroApplication.class, args);
  }

}
