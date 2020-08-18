package com.zlz.blog.server.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zlz.blog.common.entity.article.BlogArticle;
import com.zlz.blog.common.entity.article.BlogContent;
import com.zlz.blog.common.entity.article.BlogPublicInfo;
import com.zlz.blog.common.entity.common.ExcludeItem;
import com.zlz.blog.common.enums.article.LikeHandleEnum;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.PageInfo;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.DateUtil;
import com.zlz.blog.common.util.ListUtil;
import com.zlz.blog.common.util.PageUtil;
import com.zlz.blog.server.blog.mapper.ArticleMapper;
import com.zlz.blog.server.blog.service.ArticleContentService;
import com.zlz.blog.server.blog.service.ArticlePublicInfoService;
import com.zlz.blog.server.blog.service.ArticleService;
import com.zlz.blog.server.blog.service.ArticleTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文章操作service实现类
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 17:35
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleContentService contentService;
    @Resource
    private ArticlePublicInfoService publicInfoService;
    @Resource
    private ArticleTagService tagService;

    @Override
    public ResultSet queryArticle(Long id, HttpServletRequest request) {

        //设置查询条件
        QueryWrapper<BlogArticle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id)
                .last("limit 1");

        BlogArticle blogArticle = articleMapper.selectOne(queryWrapper);
        if (null == blogArticle) {
            return ResultSet.outError();
        }

        //设置标签数组
        blogArticle.setTypes(ListUtil.toList(blogArticle.getType()));
        blogArticle.setTags(ListUtil.toList(blogArticle.getTag()));

        ResultSet resultSet = contentService.queryBody(id, request);
        BlogContent blogContent = (BlogContent) resultSet.getEntity();
        blogArticle.setBlogContent(blogContent);

        ResultSet resultSet1 = publicInfoService.queryPublicInfo(id);
        BlogPublicInfo blogPublicInfo = (BlogPublicInfo) resultSet1.getEntity();
        blogArticle.setBlogPublicInfos(blogPublicInfo);

        return ResultSet.success(blogArticle);
    }

    @Override
    public ResultSet selectList(BlogArticle blogArticle, Integer type) {

        //获取并设置筛选条件
        PageInfo pageInfo = blogArticle.getPageInfo();
        excludeColumn(pageInfo, blogArticle);

        //返回结果转换并返回消息
        IPage<BlogArticle> iPage = articleMapper.selectPage(PageUtil.getIPage(pageInfo), blogArticle, type);
        return ResultSet.success(PageUtil.setPageInfo(iPage, blogArticle.getPageInfo()));
    }

    @Override
    public ResultSet filing(Integer num) {
        QueryWrapper<BlogArticle> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "created_time").orderByAsc("created_time");
        List<BlogArticle> blogArticles = articleMapper.selectList(queryWrapper);
        if (blogArticles.isEmpty()) {
            return ResultSet.success();
        }
        try {
            List<Map> list = new ArrayList<>();
            Date lastStartTime;

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = calendar.getTime();
            String dateS = format.format(date);
            Date newDate = format.parse(dateS);
            Long startT = newDate.getTime() + 86399000L;
            newDate.setTime(startT);
            calendar.setTime(newDate);
            for (int i = 0; i < num; i++) {
                Date startTime = calendar.getTime();
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date endTime = calendar.getTime();
                int n = 0;
                Iterator<BlogArticle> sListIterator = blogArticles.iterator();
                while (sListIterator.hasNext()) {
                    BlogArticle blog = sListIterator.next();
                    if (blog.getCreatedTime().compareTo(startTime) <= 0 && blog.getCreatedTime().compareTo(endTime) > 0) {
                        n++;
                        sListIterator.remove();
                    }
                }
                Map<String, String> map = new HashMap<>();
                map.put("date", format1.format(startTime));
                map.put("num", String.valueOf(n));
                list.add(map);
                lastStartTime = startTime;
                if (blogArticles.isEmpty()) {
                    break;
                }
            }

            Map<String, String> map = new HashMap<>();
            map.put("date", "更早");
            map.put("num", String.valueOf(blogArticles.size()));
            list.add(map);
            return ResultSet.success("查询成功", list);
        } catch (Exception e) {
            log.debug("printStackTrace", e);
            return ResultSet.error("查询异常退出,请重试");
        }
    }

    @Override
    public ResultSet timeShaft(BlogArticle blogArticle) {
        PageInfo pageInfo = blogArticle.getPageInfo();
        long start = (pageInfo.getPageNum() - 1) * pageInfo.getPageSize();
        long end = pageInfo.getPageNum() * pageInfo.getPageSize();

        List<BlogArticle> blogArticles = articleMapper.timeShaft(start, end);
        if (blogArticles.isEmpty()) {
            return ResultSet.success("查询成功");
        }
        long time = DateUtil.getZeroDate(blogArticles.get(0).getCreatedTime()).getTime() + 86400000L;
        int direction = 0;
        for (BlogArticle article : blogArticles) {
            //小于开始时间的设置为左侧
            if (time >= article.getCreatedTime().getTime()) {
                article.setDirection(direction);
            } else {
                time = DateUtil.getZeroDate(article.getCreatedTime()).getTime() + 86400000L;
                direction = direction == 1 ? 0 : 1;
                article.setDirection(direction);
            }
        }
        return ResultSet.success("查询成功", blogArticles);
    }

    @Override
    public ResultSet getTotalBlog() {
        Integer count = articleMapper.selectCount(null);
        return ResultSet.success("查询成功", count);
    }

    @Override
    public void addViewNumber(Long id) {
        publicInfoService.addView(id);
    }

    @Override
    public ResultSet addGoods(Long id, Integer type) {
        if (LikeHandleEnum.ADD.getCode() == type) {
            return publicInfoService.addGoods(id);
        }
        if (LikeHandleEnum.REMOVE.getCode() == type) {
            return publicInfoService.removeGoods(id);
        }
        return ResultSet.error("错误的类型");
    }

    /**
     * 设置查询筛选条件
     *
     * @param pageInfo    pageInfo
     * @param blogArticle blogArticle
     */
    private void excludeColumn(PageInfo pageInfo, BlogArticle blogArticle) {
        if (pageInfo.getExclude() != null && !pageInfo.getExclude().isEmpty()) {
            List<ExcludeItem> exclude = pageInfo.getExclude();
            List<ExcludeItem> needExclude = new ArrayList<>();
            PageInfo newPageInfo = new PageInfo();
            exclude.forEach(item -> {
                if ("isDeleted".equals(item.getColumn()) && !StringUtils.isEmpty(item.getValue())) {
                    ExcludeItem excludeItem = new ExcludeItem();
                    excludeItem.setColumn("is_deleted");
                    excludeItem.setValue(item.getValue());
                    needExclude.add(excludeItem);
                } else if ("isShow".equals(item.getColumn()) && !StringUtils.isEmpty(item.getValue())) {
                    ExcludeItem excludeItem = new ExcludeItem();
                    excludeItem.setColumn("is_show");
                    excludeItem.setValue(item.getValue());
                    needExclude.add(excludeItem);
                }
            });
            newPageInfo.setExclude(needExclude);
            blogArticle.setPageInfo(newPageInfo);
        }
    }

    /**
     * 插入文章非用户控制相关信息（浏览量等）
     *
     * @param article article
     */
    private void insertBlogInfos(BlogArticle article, HttpServletRequest request) {

        BlogPublicInfo blogPublicInfo = new BlogPublicInfo();
        blogPublicInfo.setBlogId(article.getId());
        blogPublicInfo.setStars(0L);
        blogPublicInfo.setComments(0L);
        blogPublicInfo.setGoods(0L);
        blogPublicInfo.setCollect(0L);
        blogPublicInfo.setReadings(0L);

        ResultSet resultSet = publicInfoService.insertPublicInfo(blogPublicInfo, request);
        if (!ResultSet.isSuccess(resultSet)) {
            throw new BlogException("文章浏览信息插入失败");
        }
    }
}
