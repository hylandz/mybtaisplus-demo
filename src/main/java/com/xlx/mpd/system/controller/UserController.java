package com.xlx.mpd.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlx.mpd.system.dto.ResultDTO;
import com.xlx.mpd.system.entity.User;
import com.xlx.mpd.system.enums.ResultCodeEnum;
import com.xlx.mpd.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.awt.image.RescaleOp;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xlx
 * @since 2020-04-05
 */
@Api(value = "用户管理API")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    
    @Autowired
    private UserService userService;
    
    @ApiOperation(value = "查询所有用户")
    @GetMapping("/list")
    public ResultDTO listAll(){
        Page<User> page = userService.page(new Page<>());
        List<User> records = page.getRecords();
        
        return ResultDTO.ok().message("用户列表").data("records",records);
    }
    
    @ApiOperation(value = "查询用户")
    @GetMapping("/query")
    public ResultDTO search(@RequestParam  @ApiParam("用户名") String userName){
        User one = userService.getOne(new QueryWrapper<User>().eq("user_name", userName));
        
        return one != null ? ResultDTO.ok().message("用户查询").data("user",one) : ResultDTO.setResult(ResultCodeEnum.DATA_NOT_FOUND);
    }
    
    @GetMapping("/get")
    public ResultDTO getUser(@RequestParam  String userName){
        User user = userService.queryUserByName(userName);
        return ResultDTO.ok().data("user",user);
    }
    
    @PostMapping("/create")
    public ResultDTO addUser(@RequestBody User user){
        boolean save = userService.save(user);
        log.info("新增用户:" + save);
        return save ? ResultDTO.error().message("新增失败") : ResultDTO.ok().message("新增成功");
    }
    
    @PutMapping("/modify")
    public ResultDTO updateUser(@RequestBody User user){
        boolean update = userService.updateById(user);
        log.info("修改用户:" + update);
        return update ? ResultDTO.ok().message("修改成功") :ResultDTO.error().message("修改失败");
    }
    
    @DeleteMapping("del/{userId}")
    public ResultDTO deleteUser(@PathVariable Long userId){
        boolean remove = userService.removeById(userId);
        log.info("删除用户:" + remove);
        return remove ? ResultDTO.ok().message("删除成功") :ResultDTO.error().message("删除失败");
    }
    
}

