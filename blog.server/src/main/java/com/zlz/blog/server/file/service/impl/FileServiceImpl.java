package com.zlz.blog.server.file.service.impl;

import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.WebUtil;
import com.zlz.blog.server.file.service.FileService;
import com.zlz.fastdfs.util.FastdfsUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/13 13:59
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FastdfsUtil fastdfsUtil;

    @Override
    public ResultSet uploadImage(MultipartFile file) {
        String path = WebUtil.uploadFile(file, fastdfsUtil);
        return StringUtils.isEmpty(path) ? ResultSet.error("上传失败") : ResultSet.success("上传成功", path);
    }

    @Override
    public void downloadFile(String path, HttpServletResponse response) {
        WebUtil.downloadFile(path, response, fastdfsUtil);
    }

}
