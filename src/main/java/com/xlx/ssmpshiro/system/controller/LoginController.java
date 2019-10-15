package com.xlx.ssmpshiro.system.controller;

import com.xlx.ssmpshiro.system.dto.LoginDTO;
import com.xlx.ssmpshiro.system.dto.ResultDTO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * login控制层
 *
 * @author xielx at 2019/10/15 14:32
 */
@Controller
public class LoginController extends BaseController{
    
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    
    
    /**
     * 登录操作
     * @param login 登录参数
     * @return ResultDTO
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultDTO login(LoginDTO login){
        if (login == null || login.getCaptcha() == null) {
            return ResultDTO.failed("用户名或密码不能为空");
        }
    
        UsernamePasswordToken token = new UsernamePasswordToken(login.getUserName(),login.getPassword(),login.getRememberMe());
        Subject subject = super.getSubject();
        try{
            subject.login(token);
        }catch (UnknownAccountException | LockedAccountException e){
            log.error("登录异常:{}",e.getMessage());
            return ResultDTO.failed(e.getMessage());
        }catch (AuthenticationException au){
            log.error("其他登录异常:{}",au.getMessage());
            return ResultDTO.failed("用户名或密码错误");
        }
        
        return ResultDTO.success("登录成功");
    }
}
