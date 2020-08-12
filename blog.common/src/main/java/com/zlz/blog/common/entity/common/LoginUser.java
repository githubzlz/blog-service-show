package com.zlz.blog.common.entity.common;

import lombok.Data;

import java.util.List;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2019/12/20 18:15
 */
@Data
public class LoginUser {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * token
     */
    private String token;

    /**
     * 登陆的客户端
     */
    private List<String> clients;

    /**
     * 用户权限
     */
    private List<String> authorities;
}
