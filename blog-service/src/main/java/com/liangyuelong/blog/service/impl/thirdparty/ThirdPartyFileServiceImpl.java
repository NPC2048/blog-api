package com.liangyuelong.blog.service.impl.thirdparty;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangyuelong.blog.dao.thirdparty.ThirdPartyFileMapper;
import com.liangyuelong.blog.entity.ThirdPartyFile;
import com.liangyuelong.blog.service.thidparty.ThirdPartyFileService;
import org.springframework.stereotype.Service;

/**
 * 第三封文件 Service 实现
 *
 * @author yuelong.liang
 */
@Service("thirdPartyFileService")
public class ThirdPartyFileServiceImpl extends ServiceImpl<ThirdPartyFileMapper, ThirdPartyFile> implements ThirdPartyFileService {

    @Override
    public boolean save(ThirdPartyFile entity) {
        if (exists(entity.getName())) {
            return false;
        }
        return super.save(entity);
    }


    @Override
    public boolean exists(String name) {
        return this.count(new QueryWrapper<ThirdPartyFile>().eq("name", name)) > 0;
    }
}
