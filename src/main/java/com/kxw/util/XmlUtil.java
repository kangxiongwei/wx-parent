package com.kxw.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;


/**
 * 解析XML的工具类
 * Created by kangxiongwei on 2017/8/9 20:22.
 */
public class XmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    /**
     * 把http请求流的xml解析成map格式
     *
     * @param request
     * @return
     */
    public static Map<String, String> reqXml2Map(HttpServletRequest request) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(request.getInputStream());
            StringBuffer buffer = new StringBuffer();
            while (scanner.hasNext()) {
                buffer.append(scanner.nextLine());
            }
            Document document = DocumentHelper.parseText(buffer.toString());
            Element root = document.getRootElement();
            List<Element> list = root.elements();
            Map<String, String> map = new HashMap<>();
            for (Element e : list) {
                map.put(e.getName(), e.getTextTrim());
            }
            return map;
        } catch (Exception e) {
            logger.error("从HttpServletRequest的输入流转XML失败，", e);
        } finally {
            if (scanner != null) scanner.close();
        }
        return null;
    }

    /**
     * 把一个map转为xml
     *
     * @param map
     * @return
     */
    public static String reqMap2Xml(Map<String, String> map) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        Set<String> keys = map.keySet();
        for (String key : keys) {
            root.addElement(key).addText(map.get(key).trim());
        }
        StringWriter writer = null;
        try {
            writer = new StringWriter();
            document.write(writer);
            return writer.toString();
        } catch (IOException e) {
            logger.error("把map转为xml字符串失败，", e);
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                logger.error("关闭字符串写流失败，", e);
            }
        }
        return null;
    }

}
