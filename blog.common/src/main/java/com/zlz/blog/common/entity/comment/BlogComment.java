package com.zlz.blog.common.entity.comment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zlz.blog.common.entity.common.BaseEntity;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-12 16:13
 * @description 评论
 */
@TableName("blog_comment")
public class BlogComment extends BaseEntity {

    private static final long serialVersionUID = -6047188176899466496L;

    private Long id;

    /**
     * 关联id
     */
    private Long relationId;

    /**
     * 评论人
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像地址
     */
    private String headImg;

    /**
     * 内容
     */
    private String comment;

    /**
     * 点赞
     */
    private Long like;

    /**
     * 自关联id
     */
    private Long toId;

    /**
     * 楼层id
     */
    private Long floorId;

    /**
     * 评论类型 0：文章评论 1：网站评论
     */
    private Integer type;

    /**
     * 层级 0：盖楼层 1：回复层
     */
    private Integer level;
}
