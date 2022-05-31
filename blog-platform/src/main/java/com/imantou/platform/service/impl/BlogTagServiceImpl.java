package com.imantou.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imantou.platform.dao.BlogTagMapper;
import com.imantou.platform.service.BlogTagService;

import imantou.common.domain.BlogTag;
import org.springframework.stereotype.Service;

/**
 * @author gaobug
 * @description 针对表【t_blog_tag(博客标签)】的数据库操作Service实现
 * @createDate 2022-05-23 13:39:11
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {

}




