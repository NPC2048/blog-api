package com.liangyuelong.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liangyuelong.blog.common.Result;
import com.liangyuelong.blog.common.form.BlogPageForm;
import com.liangyuelong.blog.common.form.LoginForm;
import com.liangyuelong.blog.entity.Blog;
import com.liangyuelong.blog.service.BlogService;
import com.liangyuelong.blog.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 博客管理 Controller
 *
 * @author yuelong.liang
 */
@RestController
@RequestMapping("/manage")
public class ManageController {

    @Resource
    private UserService userService;

    @Resource
    private BlogService blogService;

    /**
     * 登录
     *
     * @param exchange exchange
     * @param form     登录表单
     * @return Mono
     */
    public Result login(ServerWebExchange exchange, @Valid LoginForm form) {
        // TODO 待处理
        Mono<WebSession> sessionMono = exchange.getSession();
        return Result.SUCCESS;
    }

    /**
     * 管理端博客查询
     *
     * @param form 查询表单
     * @return Mono
     */
    public Result blogList(BlogPageForm form) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        blogService.page(form, queryWrapper);
        return Result.success(blogService.page(form, queryWrapper));
    }

    /**
     * 管理端查看博客
     *
     * @param id id
     * @return Mono
     */
    public Result blogView(Long id) {
        return Result.success(blogService.getById(id));
    }

    /**
     * 管理端编辑博客
     *
     * @param id 博客 id
     * @return Mono
     */
    public Result blogEdit(Long id) {
        return Result.success(blogService.getById(id));
    }

    public Result blogAdd() {
        return Result.SUCCESS;
    }

}
