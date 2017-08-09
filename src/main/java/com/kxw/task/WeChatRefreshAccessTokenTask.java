package com.kxw.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kxw.model.WeChatAccessToken;
import com.kxw.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 微信刷新令牌的定时任务
 * Created by kangxiongwei on 2017/8/8 18:08.
 */
@Component
public class WeChatRefreshAccessTokenTask {

    private static final Logger logger = LoggerFactory.getLogger(WeChatRefreshAccessTokenTask.class);

    private static final String appId = "wx9909f1952c65a3ba"; //"wxf83ee44c66efede1";
    private static final String appsecret = "d888b50fe6f9141965849200b2615732";  //"c5a72baae818a0c90b2c439d366749de";
    private static final String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appsecret;

    /**
     * 每隔一段时间刷新微信的AccessToken
     */
    public void refresh() {
        logger.info("开始刷新微信的token");
        String resp = HttpClientUtil.sendGetRequest(url, null);
        try {
            JSONObject obj = JSON.parseObject(resp);
            if (obj.keySet().contains("access_token")) {
                WeChatAccessToken.setAccessToken(obj.getString("access_token"));
            } else {
                logger.info("获取微信的token失败，错误原因{}", obj.getString("errmsg"));
            }
        } catch (Exception e) {
            logger.error("刷新微信的token失败，重新尝试");
            refresh();
        }
    }



}
