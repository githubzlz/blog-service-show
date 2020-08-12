package com.zlz.blog.server.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.banner.BannerEntity;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.manage.mapper.BannerMapper;
import com.zlz.blog.server.manage.service.BannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-05 16:57
 * @description
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerMapper bannerMapper;

    @Override
    public ResultSet<List<BannerEntity>> getBanner(Integer number) {
        QueryWrapper<BannerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit " + number);
        List<BannerEntity> bannerEntities = bannerMapper.selectList(queryWrapper);
        return ResultSet.success("查询成功", bannerEntities);
    }
}
