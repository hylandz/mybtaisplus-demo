package com.xlx.shiro.annotationdemo;

import com.xlx.shiro.common.annotation.ExportConfig;
import com.xlx.shiro.system.entity.User;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 注解测试
 *
 * @author xielx at 2019/10/21 22:33
 */
public class AnnotationTest {
    
    
    @Test
    public void testUserAnnotation() {
    
        Class<User> userClass = User.class;
        /*Field field = userClass.getDeclaredField("userName");
        ExportConfig exportConfig = field.getAnnotation(ExportConfig.class);*/
    
        boolean present = userClass.isAnnotationPresent(ExportConfig.class);
    }
}
