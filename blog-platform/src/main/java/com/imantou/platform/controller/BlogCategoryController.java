package com.imantou.platform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imantou.advice.response.ResponseWrapped;
import com.imantou.database.wrapped.PageWrapped;
import com.imantou.platform.domain.BlogCategory;
import com.imantou.platform.service.BlogCategoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 博客分类接口
 */
@RestController
@RequestMapping("/category")
public class BlogCategoryController {
    @Resource
    private BlogCategoryService blogCategoryService;

    /**
     * 获取博客列表
     */
    @GetMapping("/page")
    public ResponseWrapped<Object> getBlogCategoryPage(Page<BlogCategory> page, String keywords) {
        return ResponseWrapped.success(new PageWrapped(blogCategoryService.getCategoryPage(page, keywords)));
    }

}
