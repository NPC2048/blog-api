package com.liangyuelong.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liangyuelong.blog.common.NoLogException;
import com.liangyuelong.blog.common.Result;
import com.liangyuelong.blog.common.constant.CacheKeyConstants;
import com.liangyuelong.blog.common.constant.GlobalConstants;
import com.liangyuelong.blog.common.constant.SymbolConstants;
import com.liangyuelong.blog.common.enums.MailTemplateEnum;
import com.liangyuelong.blog.common.form.LoginForm;
import com.liangyuelong.blog.common.form.RegisterForm;
import com.liangyuelong.blog.entity.BlogUser;
import com.liangyuelong.blog.service.UserService;
import com.liangyuelong.blog.utils.CommUtils;
import com.liangyuelong.blog.utils.EmailUtils;
import com.liangyuelong.blog.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * 登录相关 controller
 *
 * @author yuelong.liang
 */
@Controller
@CacheConfig(cacheNames = "login")
@Slf4j
public class UserController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;


    /**
     * 注册功能
     * 1.验证基本输入信息
     * 2.向输入的邮箱发送注册验证码
     * - 2.1 邮箱发送队列？
     * 3.注册验证码存入 redis
     * 4.注册验证通过后
     *
     * @param form 邮箱注册表单
     * @return Result
     */
    @PostMapping("/register_for_email")
    @ResponseBody
    public Result registerForEmail(@Valid RegisterForm form) {
        // 判断用户是否已存在
        BlogUser user = this.userService.selectByUsername(form.getUsername());
        if (user != null) {
            return Result.failed("该用户名已存在，请更换用户名");
        }
        // 判断密码
        if (!StringUtils.equals(form.getPassword(), form.getConfirmPassword())) {
            return Result.failed("两次密码输入不一致");
        }
        // 根据邮箱从 redis 取邮箱验证码
        String key = CacheKeyPrefix.simple().compute("email") + form.getEmail();
        String verifyCode = (String) redisTemplate.opsForValue().get(key);
        // 如果 redis 里的验证码或与提交的不一致，返回验证码错误
        if (StringUtils.isEmpty(verifyCode) || !verifyCode.equals(form.getVerifyCode())) {
            return Result.failed("验证码错误");
        }
        // 新增用户
        user = new BlogUser();
        BeanUtils.copyProperties(form, user);
        this.userService.save(user);
        // 从 redis 删除该邮箱验证码
        redisTemplate.delete(key);
        log.info("insert user: " + form.getUsername() + ", " + user.getEmail());
        return Result.SUCCESS;
    }

    /**
     * 给指定邮箱发送验证码
     * 发送后存入 redis
     *
     * @param form 注册表单
     * @return Result
     */
    @PostMapping("/send_mail_verify_code")
    @ResponseBody
    public Result sendMailVerifyCode(@Valid RegisterForm form) {
        // 判断发送时间
        String key = CacheKeyConstants.REGISTER + form.getEmail();
        // 判断邮箱是否存在
        BlogUser blogUser = userService.getOne(new QueryWrapper<BlogUser>().eq("email", form.getEmail()));
        if (blogUser != null) {
            return Result.failed("该邮箱已注册");
        }
        // 随机生成 6 位验证码
        String verifyCode = RandomStringUtils.random(6, false, true);
        // 发送邮件
        boolean result = EmailUtils.sendTemplate(form.getEmail(), MailTemplateEnum.VERIFY_CODE, verifyCode, 6);
        if (!result) {
            return Result.failed("发送失败，请稍后重试");
        }
        // 发送成功，存入 redis
        redisTemplate.opsForValue().set(key, verifyCode, 6, TimeUnit.MINUTES);
        return Result.SUCCESS;
    }

    /**
     * 验证码获取接口
     */
    @PostMapping(value = "/verify_code", produces = MediaType.IMAGE_JPEG_VALUE)
    public BufferedImage verifyCode(String username) {
        if (StringUtils.isBlank(username)) {
            throw new NoLogException("用户名不能为空");
        }
        // 生成验证码
        BufferedImage image = new BufferedImage(200, 68, BufferedImage.TYPE_4BYTE_ABGR);
        String verifyCode = VerifyCodeUtils.drawRandomText(200, 68, image);
        // 存到 redis
        redisTemplate.opsForValue().set("user:verify_code:" + username, verifyCode, 3, TimeUnit.MINUTES);
        return image;
    }


    /***
     * 登录
     *
     * @return String
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(@Valid LoginForm form) {
        // 判断登录次数是否已经超过 3 次
        String loginNumberKey = GlobalConstants.LOGIN_NUM + form.getUsername();
        int loginNumber = CommUtils.null2Int((Integer) redisTemplate.opsForValue().get(loginNumberKey));
        boolean isNeedValid = loginNumber > 3;
        // 登录次数 + 1
        redisTemplate.opsForValue().set(loginNumberKey, loginNumber + 1);
        if (isNeedValid) {
            // 判断验证码
            if (StringUtils.isEmpty(form.getVerifyCode())) {
                return Result.failed("请输入验证码");
            }
            String verifyCode = (String) redisTemplate.opsForValue().get(GlobalConstants.VERIFY_CODE + form.getUsername());
            if (!StringUtils.equals(verifyCode, form.getVerifyCode())) {
                // 这里直接返回错误提示，由前端重新获取新的验证码
                return Result.failed("验证码错误");
            }
        }
        // 正则表达式判断是用户名登录还是邮箱登录
        BlogUser blogUser;
        // AT 即为 @
        if (!form.getUsername().contains(SymbolConstants.AT)) {
            // 邮箱登录
            blogUser = this.userService.selectByUsername(form.getUsername());
        } else {
            // 用户名登录
            blogUser = this.userService.selectByMail(form.getUsername());
        }
        if (blogUser == null) {
            redisTemplate.opsForValue().set(loginNumberKey, loginNumber + 1, 1, TimeUnit.HOURS);
            return Result.failed("该用户不存在");
        }

        // 验证密码
        if (!passwordEncoder.matches(form.getPassword(), blogUser.getPassword())) {
            redisTemplate.opsForValue().set(loginNumberKey, loginNumber + 1, 1, TimeUnit.HOURS);
            return Result.failed("用户名|邮箱|密码错误");
        }
        // 校验通过, 生成 token 存入 redis 并返回
        String token = generateToken(blogUser.getUsername(), form.getPassword());
        // 删除 redis 中的登录次数
        redisTemplate.delete(loginNumberKey);
        return Result.success((Object) token);
    }

    /**
     * 登录与授权
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    private String generateToken(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 持久化的redis
        String token = DigestUtils.md5DigestAsHex(username.getBytes());
        redisTemplate.opsForValue().set(token, userDetails.getUsername(), 30, TimeUnit.MINUTES);
        return token;
    }
}
