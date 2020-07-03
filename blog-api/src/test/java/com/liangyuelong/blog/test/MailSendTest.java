package com.liangyuelong.blog.test;

import com.liangyuelong.blog.BlogApiApplication;
import com.liangyuelong.blog.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送测试类
 */
@SpringBootTest(classes = BlogApiApplication.class)
@Slf4j
public class MailSendTest {

    @Resource
    private JavaMailSender sender;

    @Test
    public void testEmailUtils() {
        EmailUtils.sendHtml("npc2048@qq.com", "html测试", "<h2 style='color: #6cf'>hello</h2>");
    }

    @Test
    public void test() throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        // 从谁发送
        message.setFrom("npc2048@163.com");
        // 接收人
        message.setTo("npc2048@qq.com");
        // 邮件主题
        message.setSubject("博客测试邮件");
        // 邮件内容
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("npc2048@163.com");
        helper.setTo("npc2048@qq.com");
        helper.setSubject("博客测试邮件");
        helper.setText("<html><body><h2 style='color: #6cf'>Hello World</h2></body></html>");

        try {
            sender.send(message);
            log.info("邮件发送成功");
        } catch (Exception e) {
            log.error("邮件发送异常", e);
        }
    }

}
