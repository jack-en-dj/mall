package com.mmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Author: Jaden
 * CreateTime: 2018/4/23
 */
public class FTPUtil {
    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);
    /*
    用于连接远程服务器的配置
     */
    private static String ftpIp =PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser =PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass =PropertiesUtil.getProperty("ftp.pass");
    public FTPUtil(String ip,int port,String user,String pwd){
        this.ip =ip;
        this.port =port;
        this.pwd =pwd;
        this.user =user;
    }
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil =new FTPUtil(ftpIp,21,ftpUser,ftpPass);
        logger.info("开始连接ftp服务器");
        boolean result =ftpUtil.uploadFile("img",fileList);
        logger.info("开始文件上传，上传结束，上传结果:{}");
        return result;
    }
    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean uploaded =true;
        FileInputStream fileInputStream =null;
        /*
        连接ftp服务器
         */
        logger.info("连接ftp服务器");
        if (connectServer(this.ip,this.port,this.user,this.pwd)){
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);//文件名设置成二进制
                ftpClient.enterLocalPassiveMode();
                for (File fileItem: fileList
                        ) {
                    fileInputStream =new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fileInputStream);
                }
                logger.info("连接成功。。。。");
            }catch (IOException e){
                logger.error("上传文件异常",e);
                uploaded =false;
                e.printStackTrace();
            }finally {
                    fileInputStream.close();
                    ftpClient.disconnect();
            }
        }
        return uploaded;
    }
    private boolean connectServer(String ip,int port,String user,String pwd){
        boolean isSuccess =false;
        FTPClient ftpClient =new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess =ftpClient.login(user,pwd);
            logger.info("测试连接成功啊————————");
        } catch (IOException e) {
            logger.error("连接ftp服务器异常",e);
        }
        return isSuccess;
    }
    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
