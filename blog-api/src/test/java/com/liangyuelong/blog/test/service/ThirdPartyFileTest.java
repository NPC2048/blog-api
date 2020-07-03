package com.liangyuelong.blog.test.service;

import com.liangyuelong.blog.BlogApiApplication;
import com.liangyuelong.blog.common.Result;
import com.liangyuelong.blog.entity.ThirdPartyFile;
import com.liangyuelong.blog.service.thidparty.ThirdPartyFileService;
import com.liangyuelong.blog.thirdparty.qiniu.QiniuFileTools;
import com.liangyuelong.blog.utils.GsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@SpringBootTest(classes = BlogApiApplication.class)
public class ThirdPartyFileTest {

    @Resource
    private ThirdPartyFileService thirdPartyFileService;

    @Resource
    private QiniuFileTools qiniuFileTools;

    @Test
    public void testInsert() {
        ThirdPartyFile file = new ThirdPartyFile();
        file.setName("abcd");
        file.setContentType("image/png");
        file.setOriginName("test.png");
        file.setExt(FilenameUtils.getExtension("test.png"));
        this.thirdPartyFileService.save(file);
    }

    @Test
    public void uploadTest() throws IOException {
        File file = new File("D:/index.html");
        byte[] data = FileUtils.readFileToByteArray(file);
        Result result = qiniuFileTools.upload(data, "D:/index.html", "html/text");
        System.out.println(GsonUtils.objectToJson(result));
    }
}
