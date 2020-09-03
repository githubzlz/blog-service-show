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
     * @param type        type
     * @return ResultSet
     */
    @PostMapping("/list/{type}")
    public ResultSet selectAll(@RequestBody BlogArticle blogArticle, @PathVariable Integer type) {
        return articleService.selectList(blogArticle, type);
    }

    /**
     * 时间归档查询
     *
     * @param num
     * @return
     */
    @GetMapping("/timefiling/{num}/{type}")
    public ResultSet filing(@PathVariable("num") Integer num, @PathVariable("type") Integer type) {
        return articleService.filing(num, type);
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

    /**
     * 增加浏览量
     *
     * @param id
     */
    @GetMapping("/addviewnumber/{id}")
    public void addViewNumber(@PathVariable Long id) {
        articleService.addViewNumber(id);
    }

    /**
     * 点赞
     *
     * @param id
     * @param type
     * @return
     */
    @GetMapping("/recommend/{id}/{type}")
    public ResultSet addGoods(@PathVariable Long id, @PathVariable Integer type) {
        return articleService.addGoods(id, type);
    }
}
