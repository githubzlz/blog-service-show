package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.article.BlogContent;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.blog.mapper.ArticleContentMapper;
import com.zlz.blog.server.blog.service.ArticleContentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-05-25 16:00
 * @description
 */
@Service
public class ArticleContentServiceImpl implements ArticleContentService {

    @Resource
    private ArticleContentMapper contentMapper;

    @Override
    public ResultSet queryBody(Long blogId, HttpServletRequest request) {

        //数据检查
        if (StringUtils.isEmpty(blogId)) {
            return ResultSet.inputError();
        }
        //查询数据
        QueryWrapper<BlogContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        BlogContent blogContent = contentMapper.selectOne(queryWrapper);

        if (null == blogContent) {
            return ResultSet.outError("没有该查询到该文章的记录");
        }
        return ResultSet.success(blogContent);
    }
}
