package com.xlx.mpd.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xlx.mpd.system.dto.ResultDTO;
import com.xlx.mpd.system.entity.User;
import com.xlx.mpd.system.enums.ResultCodeEnum;
import com.xlx.mpd.system.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.awt.image.RescaleOp;
import java.util.List;

/**
 * <p>
 * 前端控制器
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
    public ResultDTO listAll() {
        Page<User> page = userService.page(new Page<>());
        List<User> records = page.getRecords();
        
        return ResultDTO.ok().message("用户列表").data("records", records);
    }
    
    
    @ApiOperation(value = "查询用户")
    @ApiParam(value = "用户名", name = "userName")
    @GetMapping("/query")
    public ResultDTO search(@RequestParam String userName) {
        User one = userService.getOne(new QueryWrapper<User>().eq("user_name", userName));
        
        return one != null ? ResultDTO.ok().message("用户查询").data("user", one) : ResultDTO.setResult(ResultCodeEnum.DATA_NOT_FOUND);
    }
    
   
    @ApiOperation("手写查询sql方法")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String")
    @GetMapping("/get")
    public ResultDTO getUser(String userName) {
        User user = userService.queryUserByName(userName);
        return ResultDTO.ok().data("user", user);
    }
    
    
    @ApiOperation("新增用户")
    @PostMapping("/create")
    public ResultDTO addUser(@RequestBody @ApiParam("USer对象") User user) {
        boolean save = userService.save(user);
        log.info("新增用户:" + save);
        return save ? ResultDTO.error().message("新增失败") : ResultDTO.ok().message("新增成功");
    }
    
    @ApiOperation(value = "修改用户", httpMethod = "PUT", notes = "修改用户资料")
    @PutMapping("/modify")
    public ResultDTO updateUser(@RequestBody @ApiParam("USer对象") User user) {
        boolean update = userService.updateById(user);
        log.info("修改用户:" + update);
        return update ? ResultDTO.ok().message("修改成功") : ResultDTO.error().message("修改失败");
    }
    
    @ApiOperation(value = "删除用户", httpMethod = "DELETE", notes = "删除用户")
    @ApiParam(name = "userId", value = "用户id")
    @DeleteMapping("del/{userId}")
    public ResultDTO deleteUser(@PathVariable Long userId) {
        boolean remove = userService.removeById(userId);
        log.info("删除用户:" + remove);
        return remove ? ResultDTO.ok().message("删除成功") : ResultDTO.error().message("删除失败");
    }
    
    @ApiOperation(value = "很多测试", httpMethod = "GET", response = ResultDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "openId", value = "系统openId", required = true, dataType = "String"),
            @ApiImplicitParam(name = "age", value = "用户年龄", required = true, dataTypeClass = Integer.class)
    })
    @ApiResponses({
            @ApiResponse(code = 1, message = "返回调用成功"),
            @ApiResponse(code = 0, message = "接口调用失败")
        
    })
    @GetMapping("/test")
    public ResultDTO test(String name, String openId, Integer age) {
        return ResultDTO.ok().code(1);
    }
}

