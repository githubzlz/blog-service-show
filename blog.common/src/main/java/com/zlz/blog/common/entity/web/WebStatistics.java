package com.zlz.blog.common.entity.web;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("web_statistics")
public class WebStatistics implements Serializable {
    private static final long serialVersionUID = 5717895007533765737L;

    private Long id;
    private Long pvToday;
    private Long readingToday;
    private Long blogToday;
    private Long userToday;
    private Long messageToday;
    private Long pvTotal;
    private Long readingTotal;
    private Long blogTotal;
    private Long userTotal;
    private Long messageTotal;
    private Date date;
    private Date createdTime;
    private Date lastModifiedTime;

}
