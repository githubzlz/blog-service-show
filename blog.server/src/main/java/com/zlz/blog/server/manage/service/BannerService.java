package com.zlz.blog.server.manage.service;

import com.zlz.blog.common.entity.banner.BannerEntity;
import com.zlz.blog.common.response.ResultSet;

import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-05 16:57
 * @description
 */
public interface BannerService {

    /**
     * 获取轮播图信息
     *
     * @param number
     * @return
     */
    ResultSet<List<BannerEntity>> getBanner(Integer number);
}
