package com.wugengkj.dance.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by zwl on 2018/5/12.
 * May god bless me
 * 票的种类
 * 名称    位置         价格         票目
 * 至尊票 VIP内场       1288         300
 * 会员票 看台中央      888          700
 * 普通票 看台两侧      588          2000
 */
@Getter
@AllArgsConstructor
public enum  ticketType {
    // 票种类
    _NONE(0, "无票"),
    ORDINARY (1, "普通"),
    MEDIUM(2, "中等"),
    SENIOR(3, "高级"),;
    private Integer code;
    private String name;
}
