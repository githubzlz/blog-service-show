package com.zlz.blog.server.comment.service;

import com.zlz.blog.common.entity.comment.BlogComment;
import com.zlz.blog.common.entity.comment.WebLeaveWord;
import com.zlz.blog.common.response.ResultSet;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-12 16:33
 * @description
 */
public interface CommentService {

    /**
     * 创建评论
     *
     * @param blogComment
     * @return
     */
    ResultSet createComment(BlogComment blogComment);

    /**
     * 删除评论
     *
     * @param blogComment
     * @return
     */
    ResultSet deleteComment(BlogComment blogComment);

    /**
     * 查看所有的评论
     *
     * @return
     */
    ResultSet getAllWebSiteComment();

    /**
     * 查看文章评论
     *
     * @param blogId
     * @return
     */
    ResultSet getBlogComment(Long blogId);

    /**
     * 网站私信
     *
     * @param webLeaveWord
     * @return
     */
    ResultSet personalLetter(WebLeaveWord webLeaveWord);
}
