package com.gaobug.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaobug.platform.dao.BlogTagMapper;
import com.gaobug.platform.domain.BlogTag;
import com.gaobug.platform.service.BlogTagService;

import org.springframework.stereotype.Service;

/**
 * @author gaobug
 * @description 针对表【t_blog_tag(博客标签)】的数据库操作Service实现
 * @createDate 2022-05-23 13:39:11
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {

}




