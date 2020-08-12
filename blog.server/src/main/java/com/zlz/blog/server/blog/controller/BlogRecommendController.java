package com.zlz.blog.server.blog.controller;

import com.zlz.blog.common.entity.article.BlogRecommend;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.blog.service.BlogRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-11 14:43
 * @description 推荐列表
 */
@RestController
@RequestMapping("/blog/recommend")
public class BlogRecommendController {

    @Autowired
    private BlogRecommendService blogRecommendService;

    /**
     * 侧栏推荐
     *
     * @return
     */
    @GetMapping("side")
    public ResultSet<List<BlogRecommend>> getSideRecommend() {
        return blogRecommendService.getSideRecommend();
    }

    /**
     * 主页推荐
     *
     * @return
     */
    @GetMapping("homepage")
    public ResultSet<BlogRecommend> getHomePageRecommend() {
        return blogRecommendService.getHomePageRecommend();
    }
}
