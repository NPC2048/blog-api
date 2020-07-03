package com.liangyuelong.blog.thirdparty;


import com.liangyuelong.blog.common.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 使用第三方接口进行文件操作
 *
 * @author yuelong.liang
 */
public interface OSSFile {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 上传结果与链接（成功才有）
     * @throws IOException e
     */
    Result upload(MultipartFile file) throws IOException;

    /**
     * 上传文件
     *
     * @param data        字节数组
     * @param originName  原文件名
     * @param contentType 文件类型
     * @return Result
     */
    Result upload(byte[] data, String originName, String contentType);

    /**
     * 从指定云服务下载文件至输出流
     *
     * @param name   名称
     * @param output 输出流
     */
//    void download(String name, OutputStream output);

}
