package com.zlz.blog.server.blog.controller;

import com.zlz.blog.common.entity.article.BlogRecommend;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.blog.service.BlogRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 热门推荐
     *
     * @return
     */
    @GetMapping("hot/{num}")
    public ResultSet<List<BlogRecommend>> getHotBlog(@PathVariable Long num) {
        return blogRecommendService.getHotBlog(num);
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

    /**
     * 侧栏推荐
     *
     * @param num
     * @return
     */
    @GetMapping("/{num}")
    public ResultSet<List<BlogRecommend>> sideRecommend(@PathVariable Long num) {
        return blogRecommendService.getSideRecommend(num);
    }
}
