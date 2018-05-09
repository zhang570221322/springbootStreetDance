package com.wugengkj.springboot.common.enums;

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
    GLOBAL_ERROR(0, "0000", "全局错误", "系统全局异常"),
    AUTHOR_LACK_ERROR(1, "1000", "权限错误", "未知系统权限错误!"),
    HTTP_METHOD_ERROR(2, "2001", "HTTP错误", "请求姿势不对，要不换个试试----(｡◕‿◕｡)----"),
    MISS_PARAM_ERROR(2, "2002", "HTTP错误", "缺少请求参数，请填写完整请求参数"),
    PARAM_INVALID_ERROR(2, "2003", "HTTP错误", "参数验证无效，请规范输入"),
    PARAM_NO_MATCH_ERROR(2, "2004", "HTTP错误", "无法匹配请求参数，请检查后重试"),
    SYS_UNKNOWN_ERROR(3, "3001", "系统错误", "检测到系统未知异常，请联系管理员"),
    SYS_INNER_ERROR(3, "3002", "系统错误", "系统内部运行错误，系尚未识别异常"),
    MAIL_SEND_ERROR(3, "3003", "系统错误", "邮件发送失败，请检查确认"),
    SQL_DATA_ERROR(4, "4001", "数据库错误", "数据库数据错误，请检查异常点"),
    SQL_EXECUTE_ERROR(4, "4002", "数据库错误", "SQL语句执行失败，请先排查异常"),
    ;

    private Integer type;
    private String code;
    private String error;
    private String message;
}
