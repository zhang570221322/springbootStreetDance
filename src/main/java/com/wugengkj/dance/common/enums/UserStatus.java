package com.wugengkj.dance.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author leaf
 * <p>date: 2018-05-08 12:58</p>
 * <p>version: 1.0</p>
 */
@Getter
@AllArgsConstructor
public enum  UserStatus {
    // 用户状态
    USER_NOT_FOUND(-1, "未找到"),
    USER_NO_ANSWER(0, "未答题"),
    USER_ANSWERING(1, "答题中"),
    USER_ANSWERED(2, "已答题"),
    ;

    private Integer code;
    private String name;
}
