package com.kxw.model;

/**
 * 维护微信的令牌
 * Created by kangxiongwei on 2017/8/8 18:06.
 */
public class WeChatAccessToken {

    private static String accessToken;

    private WeChatAccessToken() {
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        WeChatAccessToken.accessToken = accessToken;
    }
}
