package com.imantou.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import imantou.common.domain.BlogCategory;

/**
* @author gaobug
* @description 针对表【t_blog_category(博客类目)】的数据库操作Service
* @createDate 2022-05-23 13:39:11
*/
public interface BlogCategoryService extends IService<BlogCategory> {

    IPage<?> getCategoryPage(Page<BlogCategory> page, String keywords);
}
