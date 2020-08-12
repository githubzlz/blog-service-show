package com.zlz.blog.server.blog.controller;

import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.blog.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-06-24 17:09
 * @description 文章类型操作
 */
@RestController
@RequestMapping("/article/type")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    /**
     * 查询分类树
     *
     * @param request
     * @return
     */
    @GetMapping("/queryblogtype/{operate}")
    public ResultSet queryBlogType(@PathVariable("operate") Integer operate, HttpServletRequest request) {
        return articleTypeService.queryBlogType(request, operate);
    }

    /**
     * 查询分类及数量
     *
     * @return
     */
    @GetMapping("/queryblogtypenumber")
    public ResultSet queryBlogType() {
        return articleTypeService.queryBlogTypeNumber();
    }
}
