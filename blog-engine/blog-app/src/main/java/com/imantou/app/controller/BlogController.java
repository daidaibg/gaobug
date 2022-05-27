package com.imantou.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imantou.app.dto.BlogAddDTO;
import com.imantou.app.dto.BlogUpdateDTO;
import com.imantou.app.service.BlogService;
import com.imantou.common.domain.Blog;
import com.imantou.common.response.ResponseWrapped;
import com.imantou.common.utils.PageUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 博客首页相关接口
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    /**
     * 获取首页最新的博客
     */
    @GetMapping("/info/{id}")
    public ResponseWrapped<Object> getBlogInfo(@PathVariable String id) {
        return ResponseWrapped.success(blogService.getById(id));
    }

    /**
     * 获取博客列表
     */
    @GetMapping("/page")
    public ResponseWrapped<Object> getBlogPage(Page<Blog> page, Blog blog) {
        return ResponseWrapped.success(new PageUtils(blogService.getBlogPageByUser(page, blog)));
    }

    /**
     * 添加博客
     */
    @PostMapping
    public ResponseWrapped<Object> addBlog(@RequestBody BlogAddDTO blog) {
        blogService.addBlog(blog);
        return ResponseWrapped.success();
    }

    /**
     * 更新博客
     */
    @PutMapping
    public ResponseWrapped<Object> updateBlog(@RequestBody BlogUpdateDTO blog) {
        blogService.updateBlog(blog);
        return ResponseWrapped.success();
    }

    /**
     * 发布博客
     */
    @PutMapping("/publish/{id}")
    public ResponseWrapped<Object> publishBlog(@PathVariable String id) {
        blogService.publishBlog(id);
        return ResponseWrapped.success();
    }
}
