package com.zlz.blog.server.blog.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.constants.article.ArticleConstants;
import com.zlz.blog.common.entity.article.BlogType;
import com.zlz.blog.common.enums.article.OperateTypeEnum;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.response.TreeNode;
import com.zlz.blog.server.blog.mapper.ArticleMapper;
import com.zlz.blog.server.blog.mapper.ArticleTypeMapper;
import com.zlz.blog.server.blog.service.ArticleTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-06-24 17:10
 * @description
 */
@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {

    @Resource
    private ArticleTypeMapper articleTypeMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ResultSet queryBlogType(HttpServletRequest request, Integer operate) {

        //查询所有分类
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        if (OperateTypeEnum.MANAGEMENT.getCode() != operate) {
            queryWrapper.eq("state", ArticleConstants.IN_USE);
        }
        List<BlogType> blogTypes = articleTypeMapper.selectList(queryWrapper);

        //组装分类树返回给前端
        return ResultSet.success("查询成功", getTypeTreeNode(blogTypes));
    }

    @Override
    public ResultSet queryBlogTypeNumber() {
        //查询所有分类
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", ArticleConstants.IN_USE).eq("level", 1);
        List<BlogType> blogTypes = articleTypeMapper.selectList(queryWrapper);
        List<String> names = blogTypes.stream().map(BlogType::getTypeName).collect(Collectors.toList());
        Map<String, Long> stringIntegerMap = articleMapper.selectCountByType(names);
        List<JSONObject> result = new ArrayList<>();
        stringIntegerMap.forEach((key, value) -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("type", key);
            jsonObject.putOnce("number", value);
            result.add(jsonObject);
        });
        return ResultSet.success(result);
    }

    /**
     * 创建分级树
     *
     * @return
     */
    private List<TreeNode<BlogType>> getTypeTreeNode(List<BlogType> blogTypes) {

        Map<Long, TreeNode<BlogType>> parent = new HashMap<>(16);
        Map<Long, TreeNode<BlogType>> children = new HashMap<>(16);

        blogTypes.forEach(item -> {
                    TreeNode<BlogType> typeTreeNode = new TreeNode<>();
                    typeTreeNode.setId(item.getId());
                    typeTreeNode.setName(item.getTypeName());
                    typeTreeNode.setData(item);
                    typeTreeNode.setChildren(new ArrayList<>());
                    typeTreeNode.setPId(item.getPId());
                    if (item.getPId() == -1) {
                        parent.put(item.getId(), typeTreeNode);
                        return;
                    }
                    children.put(item.getId(), typeTreeNode);
                }
        );

        children.forEach((id, item) -> {
            TreeNode<BlogType> typeTreeNode = parent.get(item.getPId());
            if (null != typeTreeNode) {
                typeTreeNode.getChildren().add(item);
            }
        });

        List<TreeNode<BlogType>> list = new ArrayList<>();
        parent.forEach((key, value) -> {
                    list.add(value);
                }
        );

        //排序
        list.sort((o1, o2) -> o2.getData().getLastModifiedTime().compareTo(o1.getData().getLastModifiedTime()));
        for (TreeNode<BlogType> typeTreeNode : list) {
            List<TreeNode<BlogType>> childrenSort = typeTreeNode.getChildren();
            if (null != childrenSort) {
                childrenSort.sort((o1, o2) -> o2.getData().getLastModifiedTime().compareTo(o1.getData().getLastModifiedTime()));
            }
        }
        return list;
    }
}
