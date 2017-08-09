package com.kxw.controller;

import com.alibaba.fastjson.JSON;
import com.kxw.model.WeChatAccessToken;
import com.kxw.model.WeChatMenu;
import com.kxw.util.HttpClientUtil;
import com.kxw.util.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by kangxiongwei on 2017/8/8 12:17.
 */
@RestController
public class WxOpenController {

    private static final Logger logger = LoggerFactory.getLogger(WxOpenController.class);

    private static final String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    private static final String TOKEN = "kangxiongwei";

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
        String[] array = {TOKEN, timestamp, nonce};
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

    /**
     * 创建公众号菜单
     */
    @RequestMapping(value = "/menu/create", method = RequestMethod.POST)
    public void createMenu() {
        String url = MENU_CREATE + WeChatAccessToken.getAccessToken();
        //创建一级菜单
        List<WeChatMenu> menuList = new ArrayList<>();
        WeChatMenu menu = new WeChatMenu();
        menu.setType("click");
        menu.setName("今日歌曲");
        menu.setKey("V1001_TODAY_MUSIC");
        menuList.add(menu);

        WeChatMenu menu1 = new WeChatMenu();
        menu1.setName("菜单");
        menuList.add(menu1);
        //创建二级菜单
        List<WeChatMenu> subMenuList = new ArrayList<>();
        WeChatMenu subMenu1 = new WeChatMenu();
        subMenu1.setType("view");
        subMenu1.setName("搜索");
        subMenu1.setUrl("http://www.baidu.com");
        subMenuList.add(subMenu1);

        WeChatMenu subMenu2 = new WeChatMenu();
        subMenu2.setType("click");
        subMenu2.setName("赞一下我们");
        subMenu2.setKey("V1001_GOOD");
        subMenuList.add(subMenu2);

        menu1.setSub_button(subMenuList);
        //把菜单封装
        Map<String, List<WeChatMenu>> map = new HashMap<>();
        map.put("button", menuList);

        String params = JSON.toJSONString(map);

        logger.info("开始创建菜单，请求的url为{}", url);
        String resp = HttpClientUtil.sendPostRequest(url, params);
        logger.info("创建菜单完成，返回的结果为{}", resp);
    }

}
