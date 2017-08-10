package com.kxw.model;

import java.util.List;

/**
 * 微信公众号菜单对象
 * Created by kangxiongwei on 2017/8/9 12:12.
 */
public class WeChatMenu {

    private String type;
    private String name;
    private String key;
    private String url;
    private String media_id;

    private List<WeChatMenu> sub_button; //子菜单

    public WeChatMenu() {
    }

    public WeChatMenu(String name) {
        this.name = name;
    }

    public WeChatMenu(String type, String name, String key) {
        this.type = type;
        this.name = name;
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public List<WeChatMenu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<WeChatMenu> sub_button) {
        this.sub_button = sub_button;
    }
}
