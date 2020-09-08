package com.zlz.blog.server.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.constants.CommonConstants;
import com.zlz.blog.common.entity.web.WebStatistics;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.SqlResultUtil;
import com.zlz.blog.server.manage.mapper.WebStatisticsMapper;
import com.zlz.blog.server.manage.service.WebStatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Date: 2020/9/7 17:09
 * @Author zlz
 * @Description TODO
 **/
@Service
public class WebStatisticsServiceImpl implements WebStatisticsService {

    @Resource
    private WebStatisticsMapper webStatisticsMapper;


    @Override
    public ResultSet<WebStatistics> getStatisticsToday(Integer type) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format;

        if(type == 0){
            //创建时间查询条件为当天，查询数据
            format = simpleDateFormat.format(date);
        }else {
            //查询昨天的数据
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            format = simpleDateFormat.format(calendar.getTime());

        }
        QueryWrapper<WebStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date", format).last("limit 1");
        WebStatistics webStatistics = webStatisticsMapper.selectOne(queryWrapper);

        if(null != webStatistics){
            return ResultSet.success(webStatistics);
        }
        return ResultSet.error("查询失败");
    }

    @Override
    public ResultSet insertStatistics(WebStatistics webStatistics) throws ParseException {

        //创建时间查询条件为当天，查询数据
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date format = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        webStatistics.setLastModifiedTime(new Date());
        webStatistics.setCreatedTime(new Date());
        webStatistics.setDate(format);
        webStatistics.setId(null);
        webStatistics.setPvToday(0L);
        webStatistics.setBlogToday(0L);
        webStatistics.setUserToday(0L);
        webStatistics.setMessageToday(0L);
        webStatistics.setReadingToday(0L);

        int insert = webStatisticsMapper.insert(webStatistics);
        return SqlResultUtil.isOneRow(insert);
    }

    @Override
    public ResultSet handOn() throws ParseException {

        //获取今日的数据
        ResultSet<WebStatistics> statistics = getStatisticsToday(0);

        //若是今日的数据存在则无需处理
        if(ResultSet.isSuccess(statistics)){
            return ResultSet.success("今日已经存在数据,暂无需处理");
        }
        //如是今日数据不存在则根据最后一天的数据创建
        QueryWrapper<WebStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("date").last("limit 1");
        WebStatistics webStatistics = webStatisticsMapper.selectOne(queryWrapper);
        //若是都不存在则创建新的统计数据
        if(null == webStatistics){
            webStatistics = new WebStatistics();
        }
        return insertStatistics(webStatistics);
    }

    @Override
    public ResultSet addReadingToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int insert = webStatisticsMapper.addWebInfo(2, simpleDateFormat.format(new Date()));
        return SqlResultUtil.isOneRow(insert);
    }

    @Override
    public ResultSet addPVToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int insert = webStatisticsMapper.addWebInfo(1, simpleDateFormat.format(new Date()));
        return SqlResultUtil.isOneRow(insert);
    }

    @Override
    public ResultSet addBlogToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int insert = webStatisticsMapper.addWebInfo(3, simpleDateFormat.format(new Date()));
        return SqlResultUtil.isOneRow(insert);
    }

    @Override
    public ResultSet addUserToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int insert = webStatisticsMapper.addWebInfo(4, simpleDateFormat.format(new Date()));
        return SqlResultUtil.isOneRow(insert);
    }

    @Override
    public ResultSet addMessageToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int insert = webStatisticsMapper.addWebInfo(5, simpleDateFormat.format(new Date()));
        return SqlResultUtil.isOneRow(insert);
    }
}
