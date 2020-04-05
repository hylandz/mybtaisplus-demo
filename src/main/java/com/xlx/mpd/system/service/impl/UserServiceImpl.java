package com.xlx.mpd.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xlx.mpd.system.entity.User;
import com.xlx.mpd.system.dao.UserMapper;
import com.xlx.mpd.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xlx
 * @since 2020-04-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    
    
    @Override
    public User queryUserByName(String userName) {
        return this.baseMapper.selectOne(new QueryWrapper<User>().eq("user_name",userName));
    }
}
