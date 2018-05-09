package com.wugengkj.springboot.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author leaf
 * <p>date: 2018-05-08 13:04</p>
 * <p>version: 1.0</p>
 */
@Getter
@AllArgsConstructor
public enum  SubjectStatus {
    // 题目难度
    EASY(1, "简单"),
    MEDIUM(2, "中等"),
    HARD(3, "困难"),
    ;

    private Integer code;
    private String name;
}
