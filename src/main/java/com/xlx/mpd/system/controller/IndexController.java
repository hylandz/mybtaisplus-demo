package com.xlx.mpd.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * index
 *
 * @author xielx at 2020/4/4 17:38
 */
@Controller
@Api("index管理API")
public class IndexController {
    
    @GetMapping("/")
    @ApiOperation(value = "首页",httpMethod = "GET",notes = "直接访问url回显页面")
    public String index(){
        return "index";
    }
}
