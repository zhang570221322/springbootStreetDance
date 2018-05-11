package com.wugengkj.springboot.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 响应信息类
 *
 * @author leaf
 * <p>date: 2018-05-07 17:17</p>
 * <p>version: 1.0</p>
 */
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseInfoVO<T> {
    private String ret;
    private String msg;
    private T data;

    /**
     * 请求成功
     *
     * @param data 请求数据
     * @param <T> 泛型
     * @return 响应信息
     */
    public static <T> ResponseInfoVO success(T data) {
        return ResponseInfoVO.builder().data(data).ret("200").msg("success").build();
    }

    /**
     * 请求失败
     *
     * @param data 请求数据
     * @param <T> 泛型
     * @return 响应信息
     */
    public static <T> ResponseInfoVO fail(T data) {
        return ResponseInfoVO.builder().data(data).ret("500").msg("fail").build();
    }

    /**
     * 默认请求失败
     *
     * @return 响应信息
     */
    public static ResponseInfoVO fail() {
        return ResponseInfoVO.fail(null);
    }
}
