package com.xlx.shiro.system.shiro.bind;

import com.xlx.shiro.common.constant.UserConstant;

import java.lang.annotation.*;

/**
 * 注解类
 *
 * @author xielx on 2019/7/24
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {


	/*当前用户在request中的名字,运行时有效*/
	String value() default UserConstant.CURRENT_USER;
}
