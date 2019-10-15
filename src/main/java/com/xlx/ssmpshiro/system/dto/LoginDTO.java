package com.xlx.ssmpshiro.system.dto;

import lombok.Data;

/**
 * 前台登录参数
 *
 * @author xielx on 2019/7/13
 */
@Data
public class LoginDTO {

  private String userName;
  private String password;
  private String captcha;
  private Boolean rememberMe;
}
