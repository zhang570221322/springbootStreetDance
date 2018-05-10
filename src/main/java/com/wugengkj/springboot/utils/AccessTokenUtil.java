package com.wugengkj.springboot.utils;

import com.wugengkj.springboot.common.enums.ErrorStatus;
import com.wugengkj.springboot.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author leaf
 * <p>date: 2018-05-10 09:29</p>
 * <p>version: 1.0</p>
 */
@Slf4j
public class AccessTokenUtil {
    // 华北水利水电大学学校
    private final static String appId = "wx16c754c507aef3f3"; //小程序的唯一标识
    private final static String appSecret = "8b4cf5088da66575281df5133841e6cb"; //小程序的应用密钥
    private static final long API_TIMEOUT = 1000 * 60 * 10;

    /**
     * token验证工具
     *
     * @param token 时间戳
     * @return 正确/错误
     */
    public boolean valid(String token) {
        long accessTime;
        try {
            accessTime = Long.valueOf(token);
        } catch (Exception e) {
            throw new GlobalException(ErrorStatus.API_ACCESS_VALID_ERROR);
        }

        long currentTime = System.currentTimeMillis();

        return currentTime - accessTime <= API_TIMEOUT;
    }



    public static String getOpenId(String code) {

        if (code == null || "".equals(code)) {
            return "fail";
        }
        JSONObject json;
        BufferedReader reader;
        HttpURLConnection urlConnection;
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String httpUrl = url + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code
                + "&grant_type=authorization_code";
        StringBuilder str = null;
        try {
            urlConnection = (HttpURLConnection) new URL(httpUrl).openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            str = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("找不到微信主机");
        } catch (MalformedURLException e) {
            log.error("URL 格式错误");
        } catch (IOException e) {
            log.error("IO异常");
        }


        String openID;
        try {
            json = new JSONObject(str.toString());
            openID = json.get("openid").toString();
        } catch (JSONException e) {
            return "fail";
        }

        if (openID == null || "".equals(openID)) {
            return "fail";
        }

        return openID;


    }
}
