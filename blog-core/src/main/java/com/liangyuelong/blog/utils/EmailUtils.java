package com.liangyuelong.blog.utils;

import com.liangyuelong.blog.common.enums.MailTemplateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;

/**
 * 邮件工具类
 *
 * @author yuelong.liang
 */
@Component
@Slf4j
public class EmailUtils {

    private static JavaMailSender sender;

    private static MailProperties properties;

    @Autowired
    public void set(JavaMailSender sender, MailProperties properties) {
        EmailUtils.sender = sender;
        EmailUtils.properties = properties;
    }

    /**
     * 从邮件模板中选择模板发送
     *
     * @param receiver 接收者
     * @param template 邮件模板
     * @param val      邮件模板替换的值
     * @return 是否发送成功
     * @see com.liangyuelong.blog.common.enums.MailTemplateEnum 邮件发送模板
     */
    public static boolean sendTemplate(String receiver, MailTemplateEnum template, Object... val) {
        return sendHtml(receiver, template.getSubject(), MessageFormat.format(template.getTemplate(), val));
    }

    /**
     * 发送 html 邮件
     *
     * @param receiver 接收者
     * @param subject  主题
     * @param html     html
     * @return 是否发送成功
     */
    public static boolean sendHtml(String receiver, String subject, String html) {
        log.info("===== send email begin =====");
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(properties.getUsername());
            helper.setSubject(subject);
            helper.setTo(receiver);
            helper.setText(html, true);
            sender.send(message);
            log.info("send email: receiver: " + receiver + ", subject: " + subject + ", content: " + html);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * 发送简单邮件
     *
     * @param receiver 接收者
     * @param subject  主题
     * @param content  内容
     * @return 是否发送成功
     */
    public static boolean sendSimple(String receiver, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(properties.getUsername());
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(content);
        try {
            sender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

}
