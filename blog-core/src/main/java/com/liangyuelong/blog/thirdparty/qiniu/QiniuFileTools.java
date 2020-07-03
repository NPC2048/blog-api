package com.liangyuelong.blog.thirdparty.qiniu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liangyuelong.blog.common.Result;
import com.liangyuelong.blog.common.constant.DateFormatConstants;
import com.liangyuelong.blog.entity.ThirdPartyFile;
import com.liangyuelong.blog.service.thidparty.ThirdPartyFileService;
import com.liangyuelong.blog.thirdparty.OSSFile;
import com.liangyuelong.blog.utils.GsonUtils;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * 七牛文件上传工具类
 *
 * @author yuelong.liang
 */
@Component
@Slf4j
public class QiniuFileTools implements OSSFile {

    @Resource
    private ThirdPartyFileService thirdPartyFileService;

    @Resource
    private Auth auth;

    @Resource
    private Configuration qiniuConfiguration;

    @Resource
    private QiniuConfig qiniuConfig;

    @Override
    public Result upload(MultipartFile file) throws IOException {
        return upload(file.getBytes(), file.getOriginalFilename(), file.getContentType());
    }

    /**
     * 文件上传到七牛云后，保存文件信息到数据库
     * 之后返回文件名
     * 1.对文件进行 md5 取值，文件名就为 md5 值
     * 2.判断数据空中是否已有，如果已有，直接返回 md5 值
     * 2.将 md5 值作为 key，上传至七牛云
     *
     * @param data        字节数组
     * @param originName  原文件名
     * @param contentType 文件类型
     * @return Result
     */
    @Override
    public Result upload(byte[] data, String originName, String contentType) {
        // 获取文件上传许可
        String uploadToken = auth.uploadToken(qiniuConfig.getBucket());
        UploadManager uploadManager = new UploadManager(qiniuConfiguration);

        try {
            String md5 = DigestUtils.md5DigestAsHex(data);
            ThirdPartyFile file = this.thirdPartyFileService.getOne(new QueryWrapper<ThirdPartyFile>().eq("md5", md5));
            // 如果已存在，直接返回
            if (file != null) {
                return Result.success((Object) file.getName());
            }
            // 根据日期给文件起名
            String name = DateFormatUtils.format(new Date(), DateFormatConstants.YYYYMMDDHHMMSS) + "-" + originName;
            Response response = uploadManager.put(data, name, uploadToken);
            String responseStr = response.bodyString();
            log.info(responseStr);
            DefaultPutRet defaultPutRet = GsonUtils.jsonToObject(responseStr, DefaultPutRet.class);
            // 保存文件信息到数据库
            file = new ThirdPartyFile();
            file.setName(defaultPutRet.key);
            file.setContentType(contentType);
            file.setOriginName(originName);
            file.setExt(FilenameUtils.getExtension(originName));
            file.setMd5(md5);
            this.thirdPartyFileService.save(file);
            // 返回七牛云中的文件名
            return Result.success((Object) defaultPutRet.key);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return Result.failed(e.getMessage());
        }
    }

}
