package com.zlz.blog.common.entity.web;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Date: 2020/9/8 10:49
 * @Author zlz
 * @Description ip 调用频率
 **/
@TableName("pv_ip")
@Data
public class IPPV implements Serializable {
    private static final long serialVersionUID = 8157441846349991677L;
    @TableId("ip")
    private String ip;
    private String interfaceAddr;
    private Long pv;
    @TableField("`limit`")
    private Integer limit;
    private Date createdTime;
}
