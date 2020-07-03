package com.liangyuelong.blog.common.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 注册表单
 *
 * @author yuelong.liang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterForm extends LoginForm {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "[\\w-]+", message = "用户名格式错误, 只能为大写、小写、-、下划线组成, 不能含有其他特殊符号")
    private String username;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Length(min = 6, max = 15, message = "密码长度不能小于 6 位且不能大于 15 位")
    private String confirmPassword;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

}
