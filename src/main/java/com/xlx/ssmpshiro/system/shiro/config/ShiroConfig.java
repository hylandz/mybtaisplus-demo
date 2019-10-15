package com.xlx.ssmpshiro.system.shiro.config;

import com.xlx.ssmpshiro.system.shiro.realm.UserRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置
 *
 * @author xielx on 2019/8/28
 */
@Configuration
public class ShiroConfig {



    @Bean
    public UserRealm userRealm(){
        UserRealm realm = new UserRealm();
        return realm;
    }
}
