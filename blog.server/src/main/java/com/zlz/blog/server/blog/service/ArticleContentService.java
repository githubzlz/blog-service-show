package com.zlz.blog.server.blog.service;

import com.zlz.blog.common.response.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-05-25 15:59
 * @description 文章正文的操作
 */
public interface ArticleContentService {

    /**
     * 查询文章正文
     *
     * @param blogId  blogId
     * @param request request
     * @return ResultSet
     */
    ResultSet queryBody(Long blogId, HttpServletRequest request);
}
