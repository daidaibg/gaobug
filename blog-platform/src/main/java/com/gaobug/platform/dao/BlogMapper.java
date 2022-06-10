package com.gaobug.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.gaobug.platform.domain.Blog;
import com.gaobug.platform.vo.BlogPageVO;

/**
 * @author gaobug
 * @description 针对表【t_blog(博客文章)】的数据库操作Mapper
 * @createDate 2022-05-23 13:39:11
 * @Entity com.gaobug.common.domain.Blog
 */
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 查询推荐博客列表
     *
     * @param page 分页条件 {@link Page}
     * @return 分页列表
     */
    IPage<BlogPageVO> selectRecommendBlogPage(Page<Blog> page);

    /**
     * 查询最新博客列表
     *
     * @param page 分页条件 {@link Page}
     * @return 分页列表
     */
    IPage<BlogPageVO> selectNewestBlogPage(Page<Blog> page);

    /**
     * 查询最热博客列表
     *
     * @param page 分页条件 {@link Page}
     * @return 分页列表
     */
    IPage<BlogPageVO> selectHotBlogPage(Page<Blog> page);
}




