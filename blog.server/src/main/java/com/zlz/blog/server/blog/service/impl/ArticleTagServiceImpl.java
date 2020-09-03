package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.article.BlogTag;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.blog.mapper.ArticleTagClassesMapper;
import com.zlz.blog.server.blog.mapper.ArticleTagMapper;
import com.zlz.blog.server.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-05-25 16:07
 * @description 标签
 */
@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Resource
    private ArticleTagMapper tagMapper;
    @Resource
    private ArticleTagClassesMapper articleTagClassesMapper;


    @Override
    public ResultSet getTags() {
        //查询有效的标签分类
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "type");
        List<BlogTag> blogTags = tagMapper.selectList(queryWrapper);
        return ResultSet.success(blogTags);
    }
}
