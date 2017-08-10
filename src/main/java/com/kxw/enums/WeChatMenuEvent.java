package com.kxw.enums;

/**
 * 微信自定义菜单的事件枚举
 * Created by kangxiongwei on 2017/8/9 21:08.
 */
public enum WeChatMenuEvent {

    CLICK("click", "点击菜单"),
    VIEW("view", "跳转链接"),
    SCANCODE_PUSH("scancode_push", "扫码"),
    SCANCODE_WAITMSG("scancode_waitmsg", "扫码带提示"),
    PIC_SYSPHOTO("pic_sysphoto", "拍照发图"),
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album", "拍照或者相册发图"),
    PIC_WEIXIN("pic_weixin", "微信相册发图"),
    LOCATION_SELECT("location_select", "地理位置");

    private String key;
    private String value;

    WeChatMenuEvent(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static WeChatMenuEvent getWeChatEvent(String key) {
        if (key == null || "".equals(key)) return null;
        return WeChatMenuEvent.valueOf(key.toUpperCase());
    }
}
