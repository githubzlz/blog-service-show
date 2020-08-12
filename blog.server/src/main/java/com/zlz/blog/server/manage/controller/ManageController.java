package com.zlz.blog.server.manage.controller;

import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.manage.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-05 16:55
 * @description 轮播图控制
 */
@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private BannerService bannerService;

    /**
     * 获取轮播图
     *
     * @param number
     * @return
     */
    @GetMapping("banner/{number}")
    public ResultSet getBanner(@PathVariable("number") Integer number) {
        return bannerService.getBanner(number);
    }

    /**
     * 获取服务器时间
     *
     * @return
     */
    @GetMapping("thistime")
    public ResultSet getTime() {
        return ResultSet.success("查询成功", new Date());
    }
}
