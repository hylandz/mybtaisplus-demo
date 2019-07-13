package com.xlx.ssmshiro.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义的Realm
 *
 * @author xielx on 2019/7/13
 */
public class UserRealm extends AuthorizingRealm {


  /**
   * 权限认证
   *
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }

  /**
   * 身份认证
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String username = upToken.getUsername();

    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();

    return null;
  }
}
