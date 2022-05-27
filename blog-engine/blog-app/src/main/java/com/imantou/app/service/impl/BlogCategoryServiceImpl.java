package com.imantou.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imantou.app.dao.BlogCategoryMapper;
import com.imantou.common.domain.BlogCategory;
import com.imantou.app.service.BlogCategoryService;
import org.springframework.stereotype.Service;

/**
 * @author gaobug
 * @description 针对表【t_blog_category(博客类目)】的数据库操作Service实现
 * @createDate 2022-05-23 13:39:11
 */
@Service
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {

}




