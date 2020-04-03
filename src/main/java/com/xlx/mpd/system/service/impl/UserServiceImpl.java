package com.xlx.mpd.system.service.impl;

import com.xlx.mpd.system.entity.User;
import com.xlx.mpd.system.dao.UserMapper;
import com.xlx.mpd.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xlx
 * @since 2020-04-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
