package com.xlx.ssmpshiro.system.shiro.realm;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xlx.ssmpshiro.system.entity.User;
import com.xlx.ssmpshiro.system.service.UserService;
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
 * 自定义realm
 *
 * @author xielx on 2019/7/22
 */
public class UserRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);
    @Resource
    private UserService usersService;
    
    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("===================权限认证===============");
        User user = (User) principals.getPrimaryPrincipal();
        String account = user.getUserName();
        Set<String> roles = usersService.listRoles(account);
        Set<String> permissions = usersService.listPermissions(account);
        logger.debug("用户[{}]的角色集[{}]:", account, roles);
        logger.debug("用户[{}]的权限集[{}]", account, permissions);
        
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
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
        logger.info("userAccount:[{}]", account);
        User user;
        // 邮箱登录
        if (account.contains("@")){
            user = usersService.selectOne(new EntityWrapper<User>().where("mail=",account));
        }else {
            // 其他
            user = usersService.selectOne(new EntityWrapper<User>().eq("user_account", account));
        }
        
        if (user == null) {
            throw new UnknownAccountException("帐号或密码错误");
        }
        
        if (Boolean.TRUE.equals(user.isLocked())) {
            throw new LockedAccountException("帐号被锁定");
        }
        
        
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配,也可自定义
        return new SimpleAuthenticationInfo(user,
                user.getUserPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                getName());
    }
    
    /**
     * 清除缓存信息
     */
    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }
    
    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
    
    @Override
    protected void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
    
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }
    
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }
    
    
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
