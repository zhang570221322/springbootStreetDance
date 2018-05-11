package com.wugengkj.dance.common.exception;

import com.wugengkj.dance.common.enums.ErrorStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 全局异常
 *
 * @author leaf
 * <p>date: 2018-05-07 19:53</p>
 * <p>version: 1.0</p>
 */
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    @Setter @Getter private ErrorStatus status;

    public GlobalException(ErrorStatus status){
        super(status.getMessage());
        this.status = status;
    }
}
