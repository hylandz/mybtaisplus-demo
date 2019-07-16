package com.xlx.ssmshiro.service.impl;

import com.xlx.ssmshiro.entity.Users;
import com.xlx.ssmshiro.dao.UsersMapper;
import com.xlx.ssmshiro.service.UsersService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
