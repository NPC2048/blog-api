package com.liangyuelong.blog.thirdparty.qiniu;

import com.qiniu.common.Zone;
import com.qiniu.util.Auth;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云配置
 *
 * @author yuelong.liang
 */
@Configuration
@ConfigurationProperties(prefix = "qiniu")
@Data
@Slf4j
public class QiniuConfig {

    private String accessKey;

    private String secretKey;

    /**
     * 存储桶名称
     */
    private String bucket;

    /**
     * 访问上传的文件域名
     */
    private String accessDomain;

    @Bean
    public Auth qiniuAuth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public com.qiniu.storage.Configuration qiniuConfiguration() {
        return new com.qiniu.storage.Configuration(Zone.zone2());
    }
}
