package com.zlz.blog.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.article.BlogPublicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-05-26 11:57
 * @description 文章信息mapper
 */
@Mapper
public interface ArticlePublicInfoMapper extends BaseMapper<BlogPublicInfo> {

    /**
     * 增加浏览量
     *
     * @param blogId
     * @return
     */
    void addView(@Param("blogId") Long blogId);

    /**
     * 点赞
     *
     * @param blogId
     * @return
     */
    int addGoods(@Param("blogId") Long blogId);

    /**
     * 取消点赞
     *
     * @param blogId
     * @return
     */
    int removeGoods(@Param("blogId") Long blogId);

    int addRecommend(@Param("blogId") Long blogId);

    int removeRecommend(@Param("blogId") Long blogId);
}
