package com.zlz.blog.server.manage.service;

import com.zlz.blog.common.entity.web.WebStatistics;
import com.zlz.blog.common.response.ResultSet;

import java.text.ParseException;

/**
 * @Date: 2020/9/7 17:09
 * @Author zlz
 * @Description TODO
 **/
public interface WebStatisticsService {

    ResultSet<WebStatistics> getStatisticsToday(Integer type);

    ResultSet insertStatistics(WebStatistics webStatistics) throws ParseException;

    ResultSet handOn() throws ParseException;

    ResultSet addReadingToday();

    ResultSet addPVToday();

    ResultSet addBlogToday();

    ResultSet addUserToday();

    ResultSet addMessageToday();
}
