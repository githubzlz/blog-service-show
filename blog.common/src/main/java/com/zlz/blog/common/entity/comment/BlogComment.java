package com.zlz.blog.common.entity.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zlz.blog.common.entity.common.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-12 16:13
 * @description 评论
 */
@TableName("blog_comment")
@Data
public class BlogComment extends BaseEntity {

    private static final long serialVersionUID = -6047188176899466496L;

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 评论人
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long blogId;

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
    @TableField(value = "likes")
    private Long likes;

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

    /**
     * 下级评论
     */
    @TableField(exist = false)
    private List<BlogComment> children;

    /**
     * 回复数量
     */
    private Long commentNum;
}
