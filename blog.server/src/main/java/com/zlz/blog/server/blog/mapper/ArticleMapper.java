package com.zlz.blog.server.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.entity.article.BlogArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文章的持久层
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 17:36
 */
@Mapper
public interface ArticleMapper extends BaseMapper<BlogArticle> {

    /**
     * 文章分页查询sql
     *
     * @param page    page
     * @param article article
     * @return IPage
     */
    IPage<BlogArticle> selectPage(IPage<BlogArticle> page, @Param("article") BlogArticle article, @Param("type") Integer type);

    /**
     * 时间轴查询
     *
     * @param page
     * @param size
     * @return
     */
    List<BlogArticle> timeShaft(@Param("page") Long page, @Param("size") Long size);

    /**
     * 通过id查找文章
     *
     * @param ids
     * @return
     */
    List<BlogArticle> selectListByIds(@Param("ids") List<Long> ids);

    /**
     * 查询各个分类的数量
     *
     * @param names
     * @return
     */
    Map<String, Long> selectCountByType(@Param("names") List<String> names);

    /**
     * 根据热度排序
     *
     * @param num
     * @return
     */
    List<BlogArticle> selectListOrderByHot(@Param("num") Long num);

    List<BlogArticle> selectRecommendBlog();
}
