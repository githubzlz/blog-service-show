package com.zlz.blog.server.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.zlz.blog.common.entity.comment.BlogComment;
import com.zlz.blog.common.entity.comment.WebLeaveWord;
import com.zlz.blog.common.enums.comment.CommentTypeEnum;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.comment.mapper.CommentMapper;
import com.zlz.blog.server.comment.mapper.WebLeaveWordMapper;
import com.zlz.blog.server.comment.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-12 16:33
 * @description
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private WebLeaveWordMapper webLeaveWordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultSet createComment(BlogComment blogComment) {

        if (null == blogComment) {
            return ResultSet.error("输入数据错误");
        }
        if (null == blogComment.getType()) {
            return ResultSet.error("类型错误");
        }

        blogComment.setLikes(0L);
        blogComment.setCreator("未知用户");
        blogComment.setLastModifier("未知用户");
        blogComment.setCreatedTime(new Date());
        blogComment.setLastModifiedTime(new Date());
        if (null == blogComment.getFloorId()) {
            long id = IdWorker.getId();
            blogComment.setId(id);
            blogComment.setFloorId(id);
            blogComment.setLevel(1);
        } else {
            if (null == blogComment.getToId()) {
                return ResultSet.error("评论回复缺少信息");
            }
            blogComment.setLevel(2);
            BlogComment result = commentMapper.selectById(blogComment.getToId());
            if (null == result) {
                return ResultSet.error("无法评论，当前评论已被删除");
            }
            BlogComment update = new BlogComment();
            update.setId(result.getId());
            update.setCommentNum(result.getCommentNum() + 1L);
            int i = commentMapper.updateById(update);
            if (i != 1) {
                return ResultSet.error("评论失败");
            }
        }


        int insert = commentMapper.insert(blogComment);
        if (insert != 0) {
            return ResultSet.success("评论成功", blogComment);
        }
        return ResultSet.error("评论失败");
    }

    @Override
    public ResultSet deleteComment(BlogComment blogComment) {
        return null;
    }

    @Override
    public ResultSet getAllWebSiteComment() {

        //查询楼层
        QueryWrapper<BlogComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", CommentTypeEnum.WEBSITE.getCode())
                .eq("level", 1)
                .orderByAsc("created_time");
        List<BlogComment> floors = commentMapper.selectList(queryWrapper);
        if (floors.isEmpty()) {
            return ResultSet.success("查询成功");
        }

        //查询当前楼层回复
        for (BlogComment floor : floors) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("floor_id", floor.getId())
                    .eq("level", 2)
                    .eq("type", CommentTypeEnum.WEBSITE.getCode())
                    .orderByDesc("comment_num", "likes", "created_time");
            List<BlogComment> currentFloorReply = commentMapper.selectList(queryWrapper);
            floor.setChildren(currentFloorReply);
        }

        return ResultSet.success("查询成功", floors);
    }

    @Override
    public ResultSet getBlogComment(Long blogId) {
        return null;
    }

    @Override
    public ResultSet personalLetter(WebLeaveWord webLeaveWord) {
        int insert = webLeaveWordMapper.insert(webLeaveWord);
        if (insert != 1) {
            return ResultSet.error("私信失败,请重试");
        }
        return ResultSet.success("发送成功,博主看到您的私信后会即刻回复");
    }
}
