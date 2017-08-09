package com.kxw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * Created by kangxiongwei on 2017/8/8 17:22.
 */
public class SecureUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecureUtil.class);

    /**
     * 使用sha1算法对字符串加密，返回加密结果
     *
     * @param str
     * @return
     */
    public static String sha1(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("sha1");
            md.update(str.getBytes());
            byte[] bytes = md.digest();
            StringBuffer buffer = new StringBuffer();
            for (byte b : bytes) {
                String a = String.format("%02x", b); //转成16进制，保留两位，如果1位，前面用0不全
                buffer.append(a);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("没有改算法，", e);
        }
        return null;
    }
}
