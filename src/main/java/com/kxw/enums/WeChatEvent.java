package com.kxw.enums;

/**
 * Created by kangxiongwei on 2017/8/9 21:08.
 */
public enum WeChatEvent {

    CLICK("click", "点击事件");

    private String key;
    private String value;

    WeChatEvent(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static WeChatEvent getWeChatEvent(String key) {
        WeChatEvent[] eles = WeChatEvent.class.getEnumConstants();
        for (WeChatEvent event: eles) {
            if (event.name().equalsIgnoreCase(key)) return event;
        }
        return null;
    }
}
