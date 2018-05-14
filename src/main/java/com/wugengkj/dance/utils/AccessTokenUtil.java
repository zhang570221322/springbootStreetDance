package com.wugengkj.dance.utils;

import com.wugengkj.dance.common.enums.ErrorStatus;
import com.wugengkj.dance.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author leaf
 * <p>date: 2018-05-10 09:29</p>
 * <p>version: 1.0</p>
 */
@Slf4j
public class AccessTokenUtil {
    // private final static String APP_ID = "wxbd29784ded5b18da";
    // private final static String APP_SECRET = "f1385065e67b67a62db4299671692441";

    /**
     * 五更
     */
    private final static String APP_ID = "wxf2249998f36a3819";
    private final static String APP_SECRET = "ca1422b0ea35e49933ac2364ec085fc8";
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

    /**
     * 获取openId
     *
     * @param code
     * @return
     */
    public static String getOpenId(String code) {
        // 方便测试
        // return "o504M0QepCvAgNYErWeRk3MqVEHM";
        if (code != null && !code.isEmpty()) {
            JSONObject json;
            BufferedReader reader;
            HttpURLConnection urlConnection = null;
            String url = "https://api.weixin.qq.com/sns/jscode2session";
            String httpUrl = url + "?appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code
                    + "&grant_type=authorization_code";
            StringBuilder str;
            try {

                urlConnection = (HttpURLConnection) new URL(httpUrl).openConnection();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                str = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } catch (Exception e) {
                throw new GlobalException(ErrorStatus.OPENID_VALID_ERROR);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            String openID;
            try {
                json = new JSONObject(str.toString());
                openID = json.get("openid").toString();
            } catch (JSONException e) {
                throw new GlobalException(ErrorStatus.OPENID_VALID_ERROR);
            }
            if (openID == null) {
                throw new GlobalException(ErrorStatus.OPENID_VALID_ERROR);
            }
            return openID;
        }

        throw new GlobalException(ErrorStatus.OPENID_VALID_ERROR);
    }
}
