package com.zlz.blog.server.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.comment.BlogComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-12 16:32
 * @description
 */
@Mapper
public interface CommentMapper extends BaseMapper<BlogComment> {
}
