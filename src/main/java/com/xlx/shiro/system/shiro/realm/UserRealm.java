package com.xlx.shiro.system.shiro.realm;

import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.service.MenuService;
import com.xlx.shiro.system.service.RoleService;
import com.xlx.shiro.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * 自定义的Realm
 *
 * @author xielx on 2019/7/13
 */
public class UserRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("===================权限认证===============");
		User user = (User) principals.getPrimaryPrincipal();
		String userName = user.getUserName();
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		// 获取用户角色集
		Set<String> roleSet = roleService.findRoleKey(userName);
		logger.info("USER[{}]的角色集:[{}]", userName, roleSet);
		authorizationInfo.setRoles(roleSet);
		
		// 获取用户权限集
		Set<String> permissionSet = menuService.findPermissions(userName);
		logger.info("USER[{}]的权限集:[{}]", userName, permissionSet);
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
		String username = upToken.getUsername();
		logger.info("username:{}", username);
		User user = userService.findUserByUserName(username);
		logger.info("user:[{}]", user);
		if (user == null) {
			logger.error("occurred an UnauthenticatedException: the account number does not exist");
			/**
			 * 抛出UnauthenticatedException异常为啥捕捉不到?
			 */
			throw new UnknownAccountException("帐号或密码错误");
		} else if (Boolean.TRUE.equals(user.getLocked())) {
			logger.error("occurred an UnauthenticatedException: this account has been locked,please contact the administrator");
			throw new LockedAccountException("帐号被锁定,请联系管理员");
		}
		
		
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配,也可自定义
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,
						user.getUserPassword(),
						ByteSource.Util.bytes(user.getCredentialsSalt()),
						getName());
		
		return simpleAuthenticationInfo;
	}
}
