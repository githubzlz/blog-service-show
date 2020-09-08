package com.zlz.blog.server.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlz.blog.common.entity.web.IPPV;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Date: 2020/9/8 10:48
 * @Author zlz
 * @Description ip过滤
 **/
@Mapper()
public interface IpPvMapper extends BaseMapper<IPPV> {

}
