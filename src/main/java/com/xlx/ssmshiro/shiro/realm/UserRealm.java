package com.xlx.ssmshiro.shiro.realm;

import com.xlx.ssmshiro.entity.User;
import com.xlx.ssmshiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 自定义的Realm
 *
 * @author xielx on 2019/7/13
 */
public class UserRealm extends AuthorizingRealm {

  private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);
  @Resource
  private UserService userService;

  /**
   * 权限认证
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    logger.info("===================权限认证===============");
    String account = (String) principals.getPrimaryPrincipal();
    Set<String> roleSet = userService.getRoles(account);
    Set<String> permissionSet = userService.getPerminssions(account);
    logger.debug("用户[{}]的角色集[{}]:",account,roleSet);
    logger.debug("用户[{}]的权限集[{}]",account,permissionSet);

    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    authorizationInfo.setRoles(roleSet);
    authorizationInfo.setStringPermissions(permissionSet);
    return authorizationInfo;
  }

  /**
   * 身份认证
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    logger.info("==============身份认证==================");
    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String account = upToken.getUsername();
    logger.info("userAccount:[{}]",account);
    User user = userService.findUserByUserAccount(account);
    if (user == null) {
      throw new UnauthenticatedException("帐号或密码错误");
    }

    if (Boolean.FALSE.equals(user.getLocked())) {
      throw new LockedAccountException("帐号被锁定");
    }


    // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配,也可自定义
    SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUserAccount(),
            user.getUserPassword(),
            ByteSource.Util.bytes(user.getCridentialSalt()),
            getName());

    return simpleAuthenticationInfo;
  }
}
