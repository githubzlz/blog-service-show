package com.zlz.blog.server.manage.controller;

import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.manage.service.WebStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date 2020/9/7 17:04
 * @Author zlz
 * @Description 网站数据统计
 **/
@RestController
@RequestMapping("/statistics")
public class WebStatisticsController {

    @Autowired
    private WebStatisticsService webStatisticsService;

    @GetMapping("/now")
    public ResultSet getStatistics(){
        return webStatisticsService.getStatisticsToday(0);
    }

    @GetMapping("/increasepv")
    public ResultSet increasePv(){
        return webStatisticsService.addPVToday();
    }

    @GetMapping("/increaseblog")
    public ResultSet increaseBlog(){
        return webStatisticsService.addBlogToday();
    }
}
