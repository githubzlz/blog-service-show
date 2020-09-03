package com.zlz.blog.server.blog.service;

import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-06-24 17:10
 * @description
 */
public interface ArticleTypeService {

    /**
     * 查询文章类型
     *
     * @param request
     * @param operate
     * @return
     */
    ResultSet queryBlogType(HttpServletRequest request, Integer operate, Integer type);

    /**
     * 查询分类与文章数量
     *
     * @return
     */
    ResultSet queryBlogTypeNumber();
}
