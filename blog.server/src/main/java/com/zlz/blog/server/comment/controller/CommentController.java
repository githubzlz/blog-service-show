package com.zlz.blog.server.comment.controller;

import com.zlz.blog.common.entity.comment.BlogComment;
import com.zlz.blog.common.entity.comment.WebLeaveWord;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-12 16:32
 * @description 评论
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 创建评论
     *
     * @param blogComment
     * @return
     */
    @PostMapping("/create")
    public ResultSet createComment(@RequestBody BlogComment blogComment) {
        return commentService.createComment(blogComment);
    }

    /**
     * 删除评论
     *
     * @param blogComment
     * @return
     */
    @PostMapping("/delete")
    public ResultSet deleteComment(@RequestBody BlogComment blogComment) {
        return commentService.deleteComment(blogComment);
    }

    /**
     * 查看所有的评论
     *
     * @return
     */
    @GetMapping("/allwebsitecomment")
    public ResultSet getAllWebSiteComment(@RequestParam(required = false) Long blogId) {
        return commentService.getAllWebSiteComment(blogId);
    }

    /**
     * 私信
     *
     * @param webLeaveWord
     * @return
     */
    @PostMapping("/personal/letter")
    public ResultSet personalLetter(@RequestBody @Valid WebLeaveWord webLeaveWord) {
        return commentService.personalLetter(webLeaveWord);
    }

    /**
     * 评论数量
     * @return
     */
    @GetMapping("/info")
    public ResultSet commentNumInfo(){
        return commentService.commentNumInfo();
    }
}
