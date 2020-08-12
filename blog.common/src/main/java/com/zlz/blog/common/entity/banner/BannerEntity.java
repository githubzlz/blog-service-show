package com.zlz.blog.common.entity.banner;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020-08-05 17:00
 * @description
 */
@TableName("banner")
@Data
public class BannerEntity implements Serializable {

    private static final long serialVersionUID = 221362315534135565L;
    /**
     * id
     */
    private Long id;

    /**
     * 描述
     */
    private String description;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件
     */
    private Byte[] bytes;

    /**
     * 0:url 1:文件
     */
    private Integer saveType;

    /**
     * 次级描述
     */
    private String subDescription;
}
