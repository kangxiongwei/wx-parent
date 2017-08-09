package com.kxw.util;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * HttpClient工具类
 * Created by kangxiongwei on 16/6/9 上午12:40.
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 发送GET请求工具
     *
     * @param url    请求地址
     * @param params 请求参数 如ref=abc&name=zhangsan
     * @return
     * @throws IOException
     */
    public static String sendGetRequest(String url, String params) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        if (params != null && !"".equals(params)) url += "?" + params;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            logger.error("发送get请求失败，", e);
        } finally {
            destroy(httpClient, response);
        }
        return result;
    }

    /**
     * 发送POST请求
     *
     * @param url    请求地址
     * @param params 参数
     * @return
     */
    public static String sendPostRequest(String url, Map<String, String> params) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (String param : params.keySet()) {
            nvps.add(new BasicNameValuePair(param, params.get(param)));
        }
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            logger.error("发送post请求失败，", e);
        } finally {
            destroy(httpClient, response);
        }
        return result;
    }

    /**
     * 以RequestBody的形式发送参数
     *
     * @param url
     * @param params 必须是json格式的字符串
     * @return
     */
    public static String sendPostRequest(String url, String params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            httpPost.addHeader("Cache-Control", "no-cache");
            httpPost.addHeader("Postman-Token", "5c02303e-c7f4-b01d-d178-0203e8eb5787");
            httpPost.setEntity(new StringEntity(params, ContentType.APPLICATION_JSON));
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            logger.error("发送post请求失败，", e);
        } finally {
            destroy(httpClient, response);
        }
        return result;
    }

    private static void destroy(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        try {
            if (response != null) response.close();
            if (httpClient != null) httpClient.close();
        } catch (IOException e) {
            logger.error("关闭资源失败，", e);
        }
    }

}