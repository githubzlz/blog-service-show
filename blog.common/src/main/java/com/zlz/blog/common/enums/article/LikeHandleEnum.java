package com.zlz.blog.common.enums.article;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-17 10:46
 * @description
 */
public enum LikeHandleEnum {

    /**
     * 点赞
     */
    ADD(0, "点赞"),

    /**
     * 取消点赞
     */
    REMOVE(1, "取消点赞");

    private int code;
    private String name;


    LikeHandleEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
