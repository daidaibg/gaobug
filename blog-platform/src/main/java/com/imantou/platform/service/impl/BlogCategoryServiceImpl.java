package com.imantou.platform.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.imantou.platform.dao.BlogCategoryMapper;
import com.imantou.platform.service.BlogCategoryService;
import imantou.common.domain.BlogCategory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author gaobug
 * @description 针对表【t_blog_category(博客类目)】的数据库操作Service实现
 * @createDate 2022-05-23 13:39:11
 */
@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {
    @Resource
    private BlogCategoryMapper blogCategoryMapper;

    @Override
    public IPage<?> getCategoryPage(Page<BlogCategory> page, String keywords) {
        return lambdaQuery().likeRight(StringUtils.hasText(keywords), BlogCategory::getCategoryName, keywords).page(page);
    }
}




