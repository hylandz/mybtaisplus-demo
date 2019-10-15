package com.xlx.ssmpshiro.system.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * base
 *
 * @author xielx at 2019/10/15 14:37
 */
public class BaseController {


    
    
    
    protected Subject getSubject(){
        return SecurityUtils.getSubject();
    }
}
