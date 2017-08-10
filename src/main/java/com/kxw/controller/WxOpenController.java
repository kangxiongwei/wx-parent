package com.kxw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kxw.enums.WeChatMenuEvent;
import com.kxw.model.WeChatAccessToken;
import com.kxw.model.WeChatMenu;
import com.kxw.util.HttpClientUtil;
import com.kxw.util.SecureUtil;
import com.kxw.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping(value = "/init", method = RequestMethod.GET)
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
    public JSONObject createMenu() {
        String url = MENU_CREATE + WeChatAccessToken.getAccessToken();
        WeChatMenu menu1 = new WeChatMenu("看一看");
        WeChatMenu menu2 = new WeChatMenu("扫一扫");
        WeChatMenu menu3 = new WeChatMenu("晒一晒");
        //创建看一看的二级菜单
        WeChatMenu subMenu11 = new WeChatMenu(WeChatMenuEvent.CLICK.getKey(), WeChatMenuEvent.CLICK.getValue(), "M_1_1");
        WeChatMenu subMenu12 = new WeChatMenu(WeChatMenuEvent.VIEW.getKey(), WeChatMenuEvent.VIEW.getValue(), "M_1_2");
        subMenu12.setUrl("https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013");
        WeChatMenu subMenu13 = new WeChatMenu(WeChatMenuEvent.LOCATION_SELECT.getKey(), WeChatMenuEvent.LOCATION_SELECT.getValue(), "M_1_3");
        menu1.setSub_button(Arrays.asList(subMenu11, subMenu12, subMenu13));
        //创建扫一扫的二级菜单
        WeChatMenu subMenu21 = new WeChatMenu(WeChatMenuEvent.SCANCODE_PUSH.getKey(), WeChatMenuEvent.SCANCODE_PUSH.getValue(), "M_2_1");
        WeChatMenu subMenu22 = new WeChatMenu(WeChatMenuEvent.SCANCODE_WAITMSG.getKey(), WeChatMenuEvent.SCANCODE_WAITMSG.getValue(), "M_2_2");
        menu2.setSub_button(Arrays.asList(subMenu21, subMenu22));
        //创建晒一晒的二级菜单
        WeChatMenu subMenu31 = new WeChatMenu(WeChatMenuEvent.PIC_SYSPHOTO.getKey(), WeChatMenuEvent.PIC_SYSPHOTO.getValue(), "M_3_1");
        WeChatMenu subMenu32 = new WeChatMenu(WeChatMenuEvent.PIC_PHOTO_OR_ALBUM.getKey(), WeChatMenuEvent.PIC_PHOTO_OR_ALBUM.getValue(), "M_3_2");
        WeChatMenu subMenu33 = new WeChatMenu(WeChatMenuEvent.PIC_WEIXIN.getKey(), WeChatMenuEvent.PIC_WEIXIN.getValue(), "M_3_3");
        menu3.setSub_button(Arrays.asList(subMenu31, subMenu32, subMenu33));
        //创建一级菜单
        Map<String, List<WeChatMenu>> map = new HashMap<>();
        map.put("button", Arrays.asList(menu1, menu2, menu3));
        //创建公众号菜单
        String resp = HttpClientUtil.sendPostRequest(url, JSON.toJSONString(map));
        return JSON.parseObject(resp);
    }

    /**
     * 处理事件请求
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public void parseXml(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = XmlUtil.reqXml2Map(request);
        logger.info("接收到的事件参数为{}", map);
        if (map == null) return;
        if ("event".equals(map.get("MsgType"))) {
            //如果是事件类型
            WeChatMenuEvent event = WeChatMenuEvent.getWeChatEvent(map.get("Event"));
            String resp = handleEvent(map, event);
            try {
                response.setContentType("application/xml;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(resp);
            } catch (IOException e) {
                logger.error("写出消息报错", e);
            }
        }
    }

    private String handleEvent(Map<String, String> map, WeChatMenuEvent event) {
        Map<String, String> respMap = new HashMap<>();
        respMap.put("ToUserName", map.get("FromUserName"));
        respMap.put("FromUserName", map.get("ToUserName"));
        respMap.put("CreateTime", Long.toString(System.currentTimeMillis()));
        //处理每个事件
        if ("M_1_1".equalsIgnoreCase(map.get("EventKey"))) {
            respMap.put("MsgType", "text");
            respMap.put("Content", "您点击了点击事件按钮");
        } else if ("M_1_3".equalsIgnoreCase(map.get("EventKey"))) {
            respMap.put("MsgType", "text");
            respMap.put("Content", "您点击了打开地图按钮");
        }
        return XmlUtil.reqMap2Xml(respMap);
    }
}
