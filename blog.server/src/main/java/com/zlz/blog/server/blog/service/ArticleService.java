package com.zlz.blog.server.blog.service;

import com.zlz.blog.common.entity.article.BlogArticle;
import com.zlz.blog.common.response.ResultSet;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 文章操作的service接口
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 17:34
 */
public interface ArticleService {

    /**
     * 查看文章信息
     *
     * @param id      id
     * @param request 请求信息
     * @return ResultSet
     */
    ResultSet queryArticle(Long id, HttpServletRequest request);

    /**
     * 查看所有文章信息
     *
     * @param blogArticle 分页信息
     * @param type        类型
     * @return ResultSet
     */
    ResultSet selectList(BlogArticle blogArticle, Integer type);

    /**
     * 查询根据时间归档
     *
     * @param num
     * @return
     */
    ResultSet filing(Integer num);

    /**
     * 时间轴
     *
     * @param blogArticle
     * @return
     */
    ResultSet timeShaft(@RequestBody BlogArticle blogArticle);

    /**
     * 总博客数量
     *
     * @return
     */
    ResultSet getTotalBlog();

    /**
     * 增加浏览量
     *
     * @param id
     */
    void addViewNumber(Long id);

    /**
     * 点赞
     *
     * @param id
     * @param type
     * @return
     */
    ResultSet addGoods(Long id, Integer type);
}
