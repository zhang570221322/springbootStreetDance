package com.wugengkj.springboot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author leaf
 * <p>date: 2018-05-08 15:52</p>
 * <p>version: 1.0</p>
 */
@Getter
@AllArgsConstructor
public enum SexType {
    // 性别
    UNKNOWN(-1, "未知"),
    MALE(1, "男"),
    FEMALE(0, "女"),
    ;

    private Integer code;
    private String name;
}
