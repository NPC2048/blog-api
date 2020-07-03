package com.liangyuelong.blog.controller;

import com.liangyuelong.blog.common.Result;
import com.liangyuelong.blog.thirdparty.qiniu.QiniuFileTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 文件上传 controller
 *
 * @author yuelong.liang
 */
@RestController
@RequestMapping("/manage")
@Slf4j
public class FileUploadController {

    @Resource
    private QiniuFileTools qiniuFileTools;

    /**
     * 上传单个文件
     *
     * @param file 文件对象
     * @return Result
     */
    @RequestMapping(value = "/qiniuUpload", method = RequestMethod.POST)
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        if (file == null) {
            return Result.failed("文件不能为空");
        }
        return qiniuFileTools.upload(file);
    }

}
