package com.wugengkj.dance.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误类型
 *
 * @author leaf
 * <p>date: 2018-05-07 19:53</p>
 * <p>version: 1.0</p>
 */
@Getter
@AllArgsConstructor
public enum ErrorStatus {
    // 全局错误
    GLOBAL_ERROR(500, "500", "系统错误", "系统全局异常"),
    HTTP_METHOD_ERROR(500, "500", "系统错误", "请求姿势不对，要不换个试试----(｡◕‿◕｡)----"),
    MISS_PARAM_ERROR(500, "500", "系统错误", "缺少请求参数，请填写完整请求参数"),
    PARAM_INVALID_ERROR(500, "500", "系统错误", "参数验证无效，请规范输入"),
    PARAM_NO_MATCH_ERROR(500, "500", "系统错误", "无法匹配请求参数，请检查后重试"),
    API_ACCESS_VALID_ERROR(500, "500", "系统错误", "无API访问权限"),
    OPENID_VALID_ERROR(500, "500", "系统错误", "用户openid验证失败"),
    USER_PHONE_EXIST_ERROR(500, "500", "系统错误", "手机号已存在"),
    USER_PHONE_VALID_ERROR(500, "500", "系统错误", "手机号验证无效"),
    USER_QQ_EXIST_ERROR(500, "500", "系统错误", "QQ号已存在"),
    USER_NO_POST_INFO_ERROR(500, "500", "系统错误", "用户尚未提交信息"),
    TICKET_LACK_ERROR(500, "500", "系统错误", "票不足"),
    ;

    private Integer type;
    private String code;
    private String error;
    private String message;
}
