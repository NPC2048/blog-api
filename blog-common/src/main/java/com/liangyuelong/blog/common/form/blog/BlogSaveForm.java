package com.liangyuelong.blog.common.form.blog;

import lombok.Data;

/**
 * 博客内容保存表单
 *
 * @author yuelong.liang
 */
@Data
public class BlogSaveForm {

    /**
     * 标题
     */
    private String title;

    /**
     * 子标题
     */
    private String subtitle;

    /**
     * 内容
     */
    private String content;


}
