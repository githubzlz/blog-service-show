package com.zlz.blog.server.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.web.IPPV;
import com.zlz.blog.server.manage.mapper.IpPvMapper;
import com.zlz.blog.server.manage.service.WebStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/17 10:13
 */
@Slf4j
@Component
public class BlogInterceptor extends HandlerInterceptorAdapter {

    //每秒访问次数限制
    private static final Long PV_LIMIT = 50L;

    private IpPvMapper ipPvMapper;
    private WebStatisticsService webStatisticsService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        setBean(request);
        String realIp = getRealIp(request);
        Date date = new Date();
        QueryWrapper<IPPV> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip", realIp).eq("interface_addr", request.getRequestURI());
        IPPV ippv = ipPvMapper.selectOne(queryWrapper);

        // ip不存在记录
        if(ippv == null){
            //插入访问记录
            IPPV in = new IPPV();
            in.setIp(realIp);
            in.setCreatedTime(date);
            in.setInterfaceAddr(request.getRequestURI());
            ipPvMapper.insert(in);
            //增加浏览量
            if("/manage/visit".equals(request.getRequestURI())){
                webStatisticsService.addPVToday();
            }
            return true;
        }

        //检查ip是否被限制
        if(ippv.getLimit() == 1){
            return false;
        }

        long startTime = ippv.getCreatedTime().getTime();
        long now = date.getTime();
        long pv = ippv.getPv();
        //访问次数超过限制(30秒前有访问记录并且总访问次数超过阀值)
        if (now - startTime <= 30 * 1000 || pv < PV_LIMIT){
            //增加浏览量
            IPPV update = new IPPV();
            update.setPv(ippv.getPv() + 1);
            ipPvMapper.update(update,queryWrapper);

            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取真实IP
     * @param request 请求体
     * @return 真实IP
     */
    private static String getRealIp(HttpServletRequest request) {
        // 这个一般是Nginx反向代理设置的参数
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP的情况（只取第一个IP）
        if (ip != null && ip.contains(",")) {
            String[] ipArray = ip.split(",");
            ip = ipArray[0];
        }
        return ip;
    }

    private void setBean(HttpServletRequest request){
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        this.ipPvMapper = factory.getBean(IpPvMapper.class);
        this.webStatisticsService = factory.getBean(WebStatisticsService.class);
    }
}
