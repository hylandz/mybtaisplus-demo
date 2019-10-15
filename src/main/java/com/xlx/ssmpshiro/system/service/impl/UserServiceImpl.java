package com.xlx.ssmpshiro.system.service.impl;

import com.xlx.ssmpshiro.system.entity.User;
import com.xlx.ssmpshiro.system.dao.UserMapper;
import com.xlx.ssmpshiro.system.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xlx
 * @since 2019-07-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	private UserMapper usersMapper;


	/**
	 * 通过账户获取对象
	 * @param userName 登录账号
	 */
	public User getUsersByUserName(String userName){
		User temp = new User();
		temp.setUserName(userName);
		return usersMapper.selectOne(temp);
	}

	/**
	 * 根据id删除账户
	 * @param userId 用户id
	 */
	public boolean removeUser(Long userId){
		return usersMapper.deleteById(userId) != 0;
	}




	@Override
	public Set<String> listRoles(String userAccount) {
		Set<String> roles = usersMapper.selectAllRoles(userAccount);
		return roles;
	}

	@Override
	public Set<String> listPermissions(String userAccount) {
		return usersMapper.selectAllPermissions(userAccount);
	}

}
