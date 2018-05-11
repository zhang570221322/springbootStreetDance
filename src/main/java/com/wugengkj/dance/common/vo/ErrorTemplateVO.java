package com.wugengkj.dance.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wugengkj.dance.common.enums.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 错误信息模板
 *
 * @author leaf
 * <p>date: 2018-05-07 19:54</p>
 * <p>version: 1.0</p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorTemplateVO {
    private String code;
    private String error;
    private String message;
    private String detail;
    private String exception;
    private String path;

    public ErrorTemplateVO(ErrorStatus errorStatus, String detail, String exception) {
        this(errorStatus.getCode(), errorStatus.getError(), errorStatus.getMessage(), detail, exception, "");
    }

}
