package com.wugengkj.dance.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author leaf
 * <p>date: 2018-05-08 13:33</p>
 * <p>version: 1.0</p>
 */
@Getter
@AllArgsConstructor
public enum  SubjectResultStatus {
    // 答题结果
    NO_ANSWER(-1, "未作答"),
    ANSWER_FAIL(0, "答题错误"),
    ANSWER_SUCCESS(1, "答题正确"),
    ;
    private Integer code;
    private String name;
}
