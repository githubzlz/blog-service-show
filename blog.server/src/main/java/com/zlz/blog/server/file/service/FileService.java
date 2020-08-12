package com.zlz.blog.server.file.service;

import com.zlz.blog.common.response.ResultSet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/13 13:59
 */
public interface FileService {

    /**
     * 上传图片
     *
     * @param file 文件
     * @return 上传文件返回消息封装的对象
     * @throws IOException io异常
     */
    ResultSet uploadImage(MultipartFile file);

    /**
     * 下载文件
     *
     * @param path     路径
     * @param response 响应
     */
    void downloadFile(String path, HttpServletResponse response);
}
