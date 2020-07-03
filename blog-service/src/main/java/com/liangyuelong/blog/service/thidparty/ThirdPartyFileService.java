package com.liangyuelong.blog.service.thidparty;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangyuelong.blog.entity.ThirdPartyFile;

/**
 * 第三方文件服务 Service
 *
 * @author yuelong.liang
 */
public interface ThirdPartyFileService extends IService<ThirdPartyFile> {

    /**
     * 根据 name 判断是否已存在该文件信息
     *
     * @param name name
     * @return 是否存在
     */
    boolean exists(String name);

}
