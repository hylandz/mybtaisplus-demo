package com.xlx.mpd.system.enums;

import lombok.Getter;

/**
 * 用户状态枚举类
 *
 * @author xielx at 2020/4/5 19:16
 */
public enum UserStatusEnum {
    
    NORMAL(1,"正常"),
    ONLINE(2,"在线"),
    OFFLINE(-2,"下线"),
    LOCKED(3,"锁定"),
    
    ;
    /**
     * 编号
     */
    @Getter
    private Integer stateNum;

    /**
     * 说明
     */
    @Getter
    private String state;

    
    UserStatusEnum(Integer stateNum,String state){
        this.stateNum = stateNum;
        this.state = state;
    }
}
