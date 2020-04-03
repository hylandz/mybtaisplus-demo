package com.xlx.mpd.system.controller;


import com.xlx.mpd.system.dto.ResultDTO;
import com.xlx.mpd.system.entity.User;
import com.xlx.mpd.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xlx
 * @since 2020-04-03
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/list")
    @ResponseBody
    public ResultDTO list(){
        List<User> userList = userService.list();
        return ResultDTO.ok().message("用户列表").data("stuList",userList);
    }
    
}

