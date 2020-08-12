package com.zlz.blog.server.blog.controller;

import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.blog.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-07-07 11:17
 * @description 文章标签
 */
@RequestMapping("/article/tags")
@RestController
public class ArticleTagController {

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 获取所有的标签
     *
     * @return
     */
    @GetMapping("querytags")
    public ResultSet getTags() {
        return articleTagService.getTags();
    }
}
