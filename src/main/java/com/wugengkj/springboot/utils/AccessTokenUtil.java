package com.wugengkj.springboot.utils;

import com.wugengkj.springboot.common.enums.ErrorStatus;
import com.wugengkj.springboot.common.exception.GlobalException;

/**
 * @author leaf
 * <p>date: 2018-05-10 09:29</p>
 * <p>version: 1.0</p>
 */
public class AccessTokenUtil {
    private static final long API_TIMEOUT = 1000 * 60 * 10;

    /**
     * token验证工具
     *
     * @param token 时间戳
     * @return 正确/错误
     */
    public static boolean valid(String token) {
        long accessTime;
        try {
            accessTime = Long.valueOf(token);
        } catch (Exception e) {
            throw new GlobalException(ErrorStatus.API_ACCESS_VALID_ERROR);
        }

        long currentTime = System.currentTimeMillis();

        return currentTime - accessTime <= API_TIMEOUT;
    }

}
