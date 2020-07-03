package com.liangyuelong.blog.test.service;

import com.liangyuelong.blog.BlogApiApplication;
import com.liangyuelong.blog.entity.BlogUser;
import com.liangyuelong.blog.service.UserService;
import com.liangyuelong.blog.utils.JsonUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 逻辑删除测试
 *
 * @author yuelong.liang
 */
@SpringBootTest(classes = BlogApiApplication.class)
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void test() {
        // 插入
        BlogUser user = new BlogUser();
        user.setEmail("npc2048@qq.com");
        user.setPassword("1234567");
        user.setUsername("npc20497");
        this.userService.save(user);

    }

    @Test
    public void testUpdate() {
        // 测试更新
        BlogUser user = this.userService.getById(1235414632111792130L);
        user.setEmail("1316161154@qq.com");
        userService.updateById(user);
    }

    @Test
    public void testDelete() {
        // 测试逻辑删除
        BlogUser user = this.userService.getById(1235414632111792130L);
        this.userService.removeById(user.getId());
    }

    @Test
    public void testVersion() throws ExecutionException, InterruptedException {
        // 乐观锁
        BlogUser user = this.userService.getById(1235414632111792130L);
        user.setEmail("1259195133@qq.com");
        System.out.println(JsonUtils.toJson(user));
        this.userService.updateById(user);

        List<Boolean> results = new ArrayList<>();

        int size = 30;
        ExecutorService service = Executors.newFixedThreadPool(size);
        CountDownLatch latch = new CountDownLatch(size);
        CountDownLatch waitLatch = new CountDownLatch(size);
        long t = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            service.execute(() -> {
                try {
                    latch.await();
                    user.setUsername(RandomStringUtils.random(4, "abcdefghijklmsopqrstuvwxyz0123456789"));
                    boolean result = userService.updateById(user);
                    results.add(result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                waitLatch.countDown();
            });
            latch.countDown();
        }
        service.shutdown();
        System.out.println("before:" + results);
        waitLatch.await();
        System.out.println(results);
        System.out.println("总耗时:" + (System.currentTimeMillis() - t));

    }

    /**
     * 测试主键冲突
     */
    @Test
    public void idConflict() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        // 开启两个线程测试
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    BlogUser user = new BlogUser();
                    user.setUsername("abcdef");
                    user.setEmail("npc2048@cbc.com");
                    user.setPassword("12333");
                    boolean b = this.userService.save(user);
                    System.out.println(Thread.currentThread().getName() + "执行结果:" + b);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }
        latch.countDown();

        countDownLatch.await();
        System.out.println("执行完毕");

    }

    /**
     * 尝试多线程同时更新删除
     */
    @Test
    public void testDelete2() throws InterruptedException {
        String username = RandomStringUtils.random(8, "abcdefghasdkfjlasdf");
        BlogUser user = new BlogUser();
        user.setUsername(username);
        user.setEmail("npc2048@cbc.com");
        user.setPassword("12333");
        this.userService.save(user);
        System.out.println(JsonUtils.toJson(user));
        CountDownLatch count = new CountDownLatch(2);
        Semaphore semaphore = new Semaphore(0);
        semaphore.release();
//        new Thread(() -> {
//            // 更新
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            BlogUser users = this.userService.selectByUsername(username);
//            users.setPassword(RandomStringUtils.random(7, "0123456789"));
//            System.out.println("before update: " + JsonUtil.toJson(users));
//            boolean b = this.userService.updateById(users);
//            System.out.println("更新结果: " + b);
//            count.countDown();
//            semaphore.release();
//        }).start();
        new Thread(() -> {
            // 删除
            BlogUser blogUser = this.userService.selectByUsername(username);
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("before delte:" + JsonUtils.toJson(blogUser));
            boolean b = this.userService.removeById(blogUser);
            System.out.println("删除结果: " + b);
            count.countDown();
        }).start();

        count.await();
        ;
        System.out.println("执行结束");
    }

}
