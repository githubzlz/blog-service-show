package com.zlz.blog.server.blog.service;

import com.zlz.blog.common.response.ResultSet;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-05-25 16:07
 * @description 标签
 */
public interface ArticleTagService {

    /**
     * 查询标签的类型
     *
     * @return
     */
    ResultSet getTags();
}
