package com.zlz.blog.server.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zlz.blog.common.entity.web.IPPV;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.file.controller.FileController;
import com.zlz.blog.server.manage.mapper.IpPvMapper;
import com.zlz.blog.server.manage.service.WebStatisticsService;
import com.zlz.fastdfs.config.FastdfsConfig;
import com.zlz.fastdfs.util.FastdfsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 跨域配置
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/16 10:27
 */
@Slf4j
@Configuration
@AutoConfigureBefore(FileController.class)
public class CommentConfig {

    private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    @Resource
    private WebStatisticsService webStatisticsService;
    @Resource
    private IpPvMapper ipPvMapper;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许通过的域名
        corsConfiguration.addAllowedOrigin("*");
        // 允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    @Bean
    @Primary
    public FastdfsConfig getFastdfsConfig() {
        FastdfsConfig fastdfsConfig = new FastdfsConfig();
        fastdfsConfig.setConfigFile("E:\\本地项目\\前端\\vue\\blog-service-show\\blog.server\\src\\main\\resources\\fdfs_client.conf");
        return fastdfsConfig;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        return paginationInterceptor;
    }

    /**
     * 解决Jackson导致Long型数据精度丢失问题
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean("jackson2ObjectMapperBuilderCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .serializerByType(Long.class, ToStringSerializer.instance)
                .serializerByType(Long.TYPE, ToStringSerializer.instance);
//                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(
//                        DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)))
//                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(
//                        DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)));
    }

    @Bean("timer")
    public ScheduledExecutorService getScheduledExecutorService() throws ParseException {

        //每次程序重启先执行一次handOn
        ResultSet result = webStatisticsService.handOn();
        log.info("首次执行，执行结果:{}", result);

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        handOnWeb(service);
        handOnIp(service);
        return service;
    }

    /**
     * 处理ip记录表的数据
     * 删除一分钟之前的ip访问记录
     * @param service
     */
    private void handOnIp(ScheduledExecutorService service){
        //初始化执行一次
        QueryWrapper<IPPV> queryWrapper = new QueryWrapper<>();
        Date date = new Date(System.currentTimeMillis() - 60 * 1000);
        queryWrapper.lt("created_time", date);
        int delete = ipPvMapper.delete(queryWrapper);
        log.info("成功删除" + delete + "条访问记录");

        //每分钟执行一次
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                QueryWrapper<IPPV> queryWrapper = new QueryWrapper<>();
                Date date = new Date(System.currentTimeMillis() - 60 * 1000);
                queryWrapper.lt("created_time", date);
                int delete = ipPvMapper.delete(queryWrapper);
                log.info("成功删除" + delete + "条访问记录");
            }
        }, 60*1000, 60*1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 处理网站每日转结的数据
     * @param service
     * @throws ParseException
     */
    private void handOnWeb(ScheduledExecutorService service) throws ParseException {
        //设置初始执行的时间
        long oneDay = 24 * 60 * 60 * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        SimpleDateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
        Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + "00:00:59");
        long initDelay = curDate.getTime() - System.currentTimeMillis();
        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
        log.info("距离下次执行时间:[{}]秒", initDelay/1000);

        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(new Runnable() {
            public void run() {
                ResultSet resultSet = null;
                try {
                    resultSet = webStatisticsService.handOn();
                }catch (Exception e){
                    log.error("StackTrace", e);
                }finally {
                    log.info("执行结果{}", resultSet);
                }
            }
        }, initDelay, oneDay, TimeUnit.MILLISECONDS);
    }
}