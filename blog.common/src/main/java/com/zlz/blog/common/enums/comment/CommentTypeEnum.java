package com.zlz.blog.common.enums.comment;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-12 19:56
 * @description
 */
public enum CommentTypeEnum {

    /**
     * 文章评论
     */
    BLOG(0, "文章评论"),

    /**
     * 网站留言
     */
    WEBSITE(1, "网站留言");

    private int code;
    private String name;

    CommentTypeEnum(int code, String name) {
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
