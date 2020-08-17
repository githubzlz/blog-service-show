package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.article.BlogPublicInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.server.blog.mapper.ArticlePublicInfoMapper;
import com.zlz.blog.server.blog.service.ArticlePublicInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-05-26 11:59
 * @description 文章信息相关
 */
@Service
public class ArticlePublicInfoServiceImpl implements ArticlePublicInfoService {

    @Resource
    private ArticlePublicInfoMapper publicInfoMapper;

    @Override
    public ResultSet insertPublicInfo(BlogPublicInfo blogPublicInfo, HttpServletRequest request) {

        //数据检查
        if (null == blogPublicInfo || null == blogPublicInfo.getBlogId()) {
            return ResultSet.inputError();
        }
        //补全数据
        blogPublicInfo.setLastModifiedTime(new Date());
        blogPublicInfo.setCreatedTime(new Date());
        blogPublicInfo.setLastModifier("管理系统");
        blogPublicInfo.setCreator("管理系统");

        return SqlResultUtil.isOneRow(publicInfoMapper.insert(blogPublicInfo));
    }

    @Override
    public ResultSet queryPublicInfo(Long blogId) {

        //设置查询条件
        QueryWrapper<BlogPublicInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId)
                .last("limit 1");
        BlogPublicInfo blogPublicInfo = publicInfoMapper.selectOne(queryWrapper);

        //检查并返回数据
        if (null == blogPublicInfo) {
            return ResultSet.outError();
        }
        return ResultSet.success(blogPublicInfo);
    }

    @Override
    public boolean addView(Long blogId) {
        if (null == blogId) {
            return false;
        }
        publicInfoMapper.addView(blogId);
        return true;
    }

    @Override
    public ResultSet addGoods(Long blogId) {
        if (null == blogId) {
            return ResultSet.error("点赞失败");
        }
        int i = publicInfoMapper.addGoods(blogId);
        if (i != 1) {
            return ResultSet.error("点赞失败");
        }
        return ResultSet.success("点赞成功");
    }

    @Override
    public ResultSet removeGoods(Long blogId) {
        if (null == blogId) {
            return ResultSet.error("取消点赞失败");
        }
        int i = publicInfoMapper.removeGoods(blogId);
        if (i != 1) {
            return ResultSet.error("取消点赞失败");
        }
        return ResultSet.success("取消点赞成功");
    }
}
