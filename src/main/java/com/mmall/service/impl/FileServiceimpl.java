package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Jaden
 * CreateTime: 2018/4/23
 */
@Service("iFileService")
public class FileServiceimpl implements IFileService{
    private Logger logger = LoggerFactory.getLogger(FileServiceimpl.class);
    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);
        File fileDir =new File(path);
        /*
        判断服务器是否有文件夹的路径，没有  则会创建
         */
        if (!fileDir.exists()){
            //赋予文件操作的权限
            fileDir.setWritable(true);
            fileDir.mkdirs();
            //path:untils/apache-tomcat-8.0.53/webapps/ROOT/upload路径下创建文件路径
        }
        File targetFile =new File(path,uploadFileName);
        System.out.println(targetFile.toString());
        try {
            file.transferTo(targetFile);
            /*
            将targetFile上传到我们的FTP服务器上
            文件上传成功，上传成功删除upload下载的文件
             */
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }
}
