package com.wugengkj.dance.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author leaf
 * <p>date: 2018-05-08 13:32</p>
 * <p>version: 1.0</p>
 */
@Getter
@AllArgsConstructor
public enum SubjectRecordStatus {
    // 答题记录状态
    APPLY(1, "已申请"),
    NO_APPLY(0, "未申请")
    ;
    private Integer code;
    private String name;
}
