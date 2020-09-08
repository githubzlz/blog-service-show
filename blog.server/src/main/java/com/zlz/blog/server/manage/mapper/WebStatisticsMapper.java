package com.zlz.blog.server.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.web.WebStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Date: 2020/9/7 17:07
 * @Author zlz
 * @Description 网站管理mapper
 **/
@Mapper
public interface WebStatisticsMapper extends BaseMapper<WebStatistics> {

    int addWebInfo(@Param("type") Integer type,@Param("date") String date);
}
