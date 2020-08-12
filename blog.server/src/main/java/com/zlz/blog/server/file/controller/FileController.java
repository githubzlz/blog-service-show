package com.zlz.blog.server.file.controller;

import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.server.file.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/13 14:00
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private final Logger log = LoggerFactory.getLogger(FileController.class);

    @Resource
    private FileService fileService;

    /**
     * 上传图片，返回图片地址到前端
     *
     * @param filename 文件
     * @param request  request
     * @throws IOException IOException
     */
    @PostMapping("/image/upload")
    public ResultSet upload(MultipartFile filename, HttpServletRequest request) {
        return fileService.uploadImage(filename);
    }

    /**
     * 下载文件
     *
     * @param path     文件路径
     * @param response response
     */
    @GetMapping("/download")
    public void download(@RequestParam String path, HttpServletResponse response) {
        fileService.downloadFile(path, response);
    }
}
