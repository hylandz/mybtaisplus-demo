package com.xlx.shiro.annotationdemo;

import com.xlx.shiro.system.entity.User;

/**
 * 注解测试
 *
 * @author xielx at 2019/10/21 22:33
 */
public class AnnotationTest {
    
    
    public void testUserAnnotation(){
    
        Class<User> userClass = User.class;
        userClass.getAnnotations();
    }
}
