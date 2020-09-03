package com.zlz.blog.server.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zlz.blog.server.file.controller.FileController;
import com.zlz.fastdfs.config.FastdfsConfig;
import com.zlz.fastdfs.util.FastdfsUtil;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 *
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/16 10:27
 */
@Configuration
@AutoConfigureBefore(FileController.class)
public class CommentConfig {

    private static final String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

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
}