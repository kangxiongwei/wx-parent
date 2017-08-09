package com.kxw.controller;

import com.kxw.util.SecureUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by kangxiongwei on 2017/8/8 12:17.
 */
@RestController
public class WxOpenController {

    private static final String token = "kangxiongwei";

    /**
     * 配置测试账号
     *
     * @param request
     * @return
     */
    @RequestMapping("/init")
    public void initWeChat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        String[] array = {token, timestamp, nonce};
        Arrays.sort(array);
        StringBuffer buffer = new StringBuffer();
        for (String a : array) {
            buffer.append(a);
        }
        String str = SecureUtil.sha1(buffer.toString());
        if (signature.equals(str)) {
            response.getWriter().println(echostr);
        } else {
            response.getWriter().println("wrong");
        }
    }
}
