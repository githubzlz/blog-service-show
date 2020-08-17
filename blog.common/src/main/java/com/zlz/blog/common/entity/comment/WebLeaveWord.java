package com.zlz.blog.common.entity.comment;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-14 08:42
 * @description 网站私信
 */
@TableName("blog_leave_words")
@Data
public class WebLeaveWord {

    private Long id;

    /**
     * 联系地址
     */
    @NotNull(message = "联系地址不能为空")
    private String contacts;

    /**
     * 留言内容
     */
    @NotNull(message = "留言内容不能为空")
    private String content;

    /**
     * 联系方式
     */
    @NotNull(message = "联系方式不能为空")
    private String contactsType;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String name;
}
