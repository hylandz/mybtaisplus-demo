package com.xlx.ssmshiro.service.impl;

import com.xlx.ssmshiro.entity.Users;
import com.xlx.ssmshiro.dao.UsersMapper;
import com.xlx.ssmshiro.service.UsersService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.catalina.User;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

	@Resource
	private UsersMapper usersMapper;


	/**
	 * 通过账户获取对象
	 * @param userName 登录账号
	 */
	public Users getUsersByUserName(String userName){
		Users temp = new Users();
		temp.setUserAccount(userName);
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
