package com.liangyuelong.blog.test.thirdparty.qiniu;

import com.liangyuelong.blog.utils.GsonUtils;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 七牛云接口测试
 *
 * @author yuelong.liang
 */
public class QiniuTest {


    @Test
    public void test() throws IOException {
        String domain = "http://q6cmjst9i.bkt.clouddn.com";
        String bucket = "npc2048-file-server";
        Auth auth = Auth.create("MiQ2n4QaOO2CSzCPO7-p6FITpOwH92sAM8MpM77p", "I9J7QQbfaRjPh593Dyhqy7ottiP-JhwHyUaQiSas");
        String up = auth.uploadToken("npc2048-file-server");
        System.out.println(up);
        Configuration configuration = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(configuration);

        byte[] data = FileUtils.readFileToByteArray(new File("D:/test.png"));
        Response response = uploadManager.put(data, null, up);
        // 解析结果
        String responseString = response.bodyString();
        System.out.println(response.address);
        System.out.println(responseString);
        DefaultPutRet putRet = GsonUtils.jsonToObject(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key);
        System.out.println(putRet.hash);

        String host = domain + "/" + putRet.hash;

        URL url = new URL(host);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "image/png");

        connection.connect();

        IOUtils.copy(connection.getInputStream(), new FileOutputStream("D:/testss.png"));
        System.out.println("写入成功");
        connection.disconnect();

    }

}
