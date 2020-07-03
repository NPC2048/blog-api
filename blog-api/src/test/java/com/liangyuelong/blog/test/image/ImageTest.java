package com.liangyuelong.blog.test.image;

import com.liangyuelong.blog.utils.UrlDecoder;
import com.liangyuelong.blog.utils.UrlEncoder;
import com.liangyuelong.blog.utils.VerifyCodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageTest {

    @Test
    public void test() throws IOException {
        BufferedImage verifyImage = new BufferedImage(200, 68, BufferedImage.TYPE_4BYTE_ABGR);
        String verifyCode = VerifyCodeUtils.drawRandomText(200, 68, verifyImage);
        System.out.println(verifyCode);
        ImageIO.write(verifyImage, "png", new File("D:/test.png"));

    }

    private Set<LinkedHashSet<UUID>> linkedHashSet = Collections.synchronizedSet(new LinkedHashSet<>());

    @Test
    public void test2() throws InterruptedException {
        int size = 20;
        ExecutorService service = Executors.newFixedThreadPool(size);
        CountDownLatch latch = new CountDownLatch(size);
        CountDownLatch currentLatch = new CountDownLatch(size);

        for (int i = 0; i < size; i++) {
            service.execute(() -> {
                try {
                    // 等待其他线程就绪，再并发执行
                    currentLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LinkedHashSet<UUID> set = new LinkedHashSet<>();
                for (int j = 0; j < 1000000; j++) {
                    set.add(UUID.randomUUID());
                }
                linkedHashSet.add(set);
                latch.countDown();
            });
            currentLatch.countDown();
        }
        // 等待线程执行完毕，避免提前结束
        latch.await();
        System.out.println("set 数据");
        System.out.println(linkedHashSet.size());
        LinkedHashSet<UUID> uuidLinkedHashSet = new LinkedHashSet<>();
        System.out.println("天机");
        for (LinkedHashSet<UUID> uuids : linkedHashSet) {
            uuidLinkedHashSet.addAll(uuids);
            uuids.clear();
        }
        System.out.println("泄露:" + uuidLinkedHashSet.size());
    }

    public int[] randomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        return arr;
    }

    @Test
    public void tet() {
        String encode = UrlEncoder.encode("http://asdf.vip?abcd=123&name=是的覅");
        System.out.println(encode);
        String decode = UrlDecoder.decode(encode, StandardCharsets.UTF_8);
        System.out.println(decode);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        encode = passwordEncoder.encode("123456");
        System.out.println(encode);
        boolean matches = passwordEncoder.matches("123456", encode);
        System.out.println(matches);
    }

}
