package com.liangyuelong.blog.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 邮件模板
 *
 * @author yuelong.liang
 */
public enum MailTemplateEnum {
    // 验证码
    VERIFY_CODE("梁跃珑的个人博客",
            "<html><body>您正在注册梁跃珑个人博客的账号， 这是您的验证码：<span style='color: #6cf'>{0}</span><br>有效期 {1} 分钟</body></html>");

    /**
     * 主题
     */
    @Getter
    @Setter
    private String subject;

    /**
     * 模板
     */
    @Getter
    @Setter
    private String template;

    MailTemplateEnum(String subject, String template) {
        this.subject = subject;
        this.template = template;
    }

}
