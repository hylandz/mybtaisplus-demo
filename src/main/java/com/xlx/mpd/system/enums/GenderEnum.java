package com.xlx.mpd.system.enums;

import lombok.Getter;

/**
 * 性别枚举类
 *
 * @author xielx at 2020/4/5 18:56
 */
public enum GenderEnum {
    
    SECRET(0,"保密"),
    MALE(1, "男"),
    FEMALE(2, "女");
    
    /**
     * 性别编号
     */
    @Getter
    private Integer genderNum;
    /**
     * 说明
     */
    @Getter
    private String value;
    
    
    GenderEnum(Integer genderNum, String value) {
        this.genderNum = genderNum;
        this.value = value;
    }
    
    
}
