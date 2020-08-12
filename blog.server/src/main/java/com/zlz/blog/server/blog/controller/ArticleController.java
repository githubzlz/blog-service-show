package com.zlz.blog.server.blog.controller;

import com.zlz.blog.common.entity.article.BlogArticle;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.blog.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 文章操作的控制器层
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 17:37
 */
@RequestMapping("/blog/article")
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 分页查询,模糊查询
     *
     * @param blogArticle blogArticle
     * @param request     request
     * @return ResultSet
     */
    @PostMapping("/list")
    public ResultSet selectAll(@RequestBody BlogArticle blogArticle, HttpServletRequest request) {
        return articleService.selectList(blogArticle, request);
    }

    /**
     * 时间轴
     *
     * @param blogArticle
     * @return
     */
    @PostMapping("timeshaft")
    public ResultSet timeShaft(@RequestBody BlogArticle blogArticle) {
        return articleService.timeShaft(blogArticle);
    }

    /**
     * 总博客数量
     *
     * @return
     */
    @GetMapping("total")
    public ResultSet getTotalBlog() {
        return articleService.getTotalBlog();
    }

    /**
     * 查询一篇文章
     *
     * @param id      id
     * @param request request
     * @return return
     */
    @GetMapping("/queryarticle/{id}")
    public ResultSet selectArticle(@PathVariable("id") Long id, HttpServletRequest request) {
        return articleService.queryArticle(id, request);
    }
}
