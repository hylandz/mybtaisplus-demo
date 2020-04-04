package com.xlx.mpd.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * index
 *
 * @author xielx at 2020/4/4 17:38
 */
@Controller
public class IndexController {
    
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
