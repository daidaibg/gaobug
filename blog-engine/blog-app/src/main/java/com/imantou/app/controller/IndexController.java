package com.imantou.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imantou.app.dto.BlogSearchDTO;
import com.imantou.app.service.BlogService;
import com.imantou.common.domain.Blog;
import com.imantou.common.response.ResponseWrapped;
import com.imantou.common.response.PageWrapped;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 博客首页相关接口
 */
@RestController
@RequestMapping("index")
public class IndexController {

    @Resource
    private BlogService blogService;

    /**
     * 获取首页最新的博客
     */
    @GetMapping("/blog/list/{type}")
    public ResponseWrapped<Object> getIndexBlogPage(@PathVariable String type, Page<Blog> page) {
        return ResponseWrapped.success(new PageWrapped(blogService.getIndexBlogPage(type, page)));
    }

    /**
     * 获取首页最新的博客
     */
    @GetMapping("/blog/search")
    public ResponseWrapped<Object> getSearchBlog(Page<Blog> page, BlogSearchDTO searchDTO) {
        return ResponseWrapped.success(new PageWrapped(blogService.getSearchBlogPage(page, searchDTO)));
    }
}
