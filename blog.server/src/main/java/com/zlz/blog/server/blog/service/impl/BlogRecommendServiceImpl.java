package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.article.BlogArticle;
import com.zlz.blog.common.entity.article.BlogRecommend;
import com.zlz.blog.common.enums.article.RecommendTypeEnum;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.server.blog.mapper.ArticleMapper;
import com.zlz.blog.server.blog.mapper.BlogRecommendMapper;
import com.zlz.blog.server.blog.service.BlogRecommendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-11 14:44
 * @description
 */
@Service
public class BlogRecommendServiceImpl implements BlogRecommendService {

    @Resource
    private BlogRecommendMapper blogRecommendMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ResultSet<List<BlogRecommend>> getHotBlog(Long num) {
        return ResultSet.success("查询成功", articleMapper.selectListOrderByHot(num));
    }

    @Override
    public ResultSet<List<BlogRecommend>> getSideRecommend(Long num) {
        QueryWrapper<BlogRecommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recommend_type", RecommendTypeEnum.SIDE.getCode());
        List<BlogRecommend> blogRecommends = blogRecommendMapper.selectList(queryWrapper);
        List<Long> ids = blogRecommends.stream().map(BlogRecommend::getBlogId).collect(Collectors.toList());

        if(ids.isEmpty()){
            return ResultSet.success("查询为空");
        }

        QueryWrapper<BlogArticle> blogArticleQueryWrapper = new QueryWrapper<>();
        blogArticleQueryWrapper.in("id", ids).select("title", "id").last("limit " + num);
        List<BlogArticle> blogArticles = articleMapper.selectList(blogArticleQueryWrapper);
        return ResultSet.success(blogArticles);
    }

    @Override
    public ResultSet<BlogRecommend> getHomePageRecommend() {
        List<BlogArticle> blogArticles = articleMapper.selectRecommendBlog();
        return ResultSet.success(blogArticles);
    }

}
