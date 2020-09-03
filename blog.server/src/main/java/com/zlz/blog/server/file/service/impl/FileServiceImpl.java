package com.zlz.blog.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlz.blog.common.entity.file.BlogFile;
import com.zlz.blog.common.exception.BlogException;
import com.zlz.blog.common.response.ResultSet;
import com.zlz.blog.common.util.WebUtil;
import com.zlz.blog.server.file.mapper.FileMapper;
import com.zlz.blog.server.file.service.FileService;
import com.zlz.fastdfs.util.FastdfsUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author zhulinzhong
 * @version 1.0 CreateTime:2020/1/13 13:59
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FastdfsUtil fastdfsUtil;
    @Resource
    private FileMapper fileMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultSet uploadImage(MultipartFile file) throws IOException, NoSuchAlgorithmException {

        MessageDigest md5I = MessageDigest.getInstance("MD5");
        md5I.update(file.getBytes());
        BigInteger bi = new BigInteger(1, md5I.digest());
        String md5 = bi.toString(16);
        String fileName = file.getOriginalFilename();

        QueryWrapper<BlogFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        BlogFile blogFile1 = fileMapper.selectOne(queryWrapper);

        if(null != blogFile1){
            return ResultSet.success("上传成功", blogFile1.getFileUrl());
        }

        Integer base = 200 * 1024;
        float quality = base/file.getSize();
        if(quality > 1){
            quality = 1;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream()).size(800, 600).outputQuality(quality).toOutputStream(out);
        byte[] bytes = out.toByteArray();

        String path = WebUtil.uploadFile(bytes, fileName, fastdfsUtil);
        if(StringUtils.isEmpty(path)){
            throw new BlogException("文件上传失败");
        }
        BlogFile blogFile = new BlogFile();
        blogFile.setFileName(fileName);
        assert fileName != null;
        String[] split = fileName.split(".");
        if(split.length > 1){
            blogFile.setFileType(split[split.length-1]);
        }
        blogFile.setFileUrl(path);
        blogFile.setMd5(md5);
        blogFile.setFileSize(String.valueOf(bytes.length));
        fileMapper.insert(blogFile);
        return StringUtils.isEmpty(path) ? ResultSet.error("上传失败") : ResultSet.success("上传成功", path);
    }

    @Override
    public void downloadFile(String path, HttpServletResponse response) {
        WebUtil.downloadFile(path, response, fastdfsUtil);
    }

}
