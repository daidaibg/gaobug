package imantou.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import imantou.app.dto.BlogAddDTO;
import imantou.app.dto.BlogSearchDTO;
import imantou.app.dto.BlogUpdateDTO;
import imantou.common.domain.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author gaobug
 * @description 针对表【t_blog(博客文章)】的数据库操作Service
 * @createDate 2022-05-23 13:39:11
 */
public interface BlogService extends IService<Blog> {
    /**
     * 获取最新博客列表
     *
     * @param type 分页条件
     * @param page 分页条件 {@link Page}
     * @return 最新博客列表
     */
    IPage<?> getIndexBlogPage(String type, Page<Blog> page);

    /**
     * 获取搜索的博客列表
     *
     * @param page      分页条件 {@link Page}
     * @param searchDTO 搜索条件
     * @return 搜索博客列表
     */
    IPage<?> getSearchBlogPage(Page<Blog> page, BlogSearchDTO searchDTO);

    /**
     * 根据用户获取博客列表
     *
     * @param page {@link Page}
     * @param blog 搜索条件
     * @return 个人博客列表
     */
    IPage<?> getBlogPageByUser(Page<Blog> page, Blog blog);

    /**
     * 添加博客
     *
     * @param blog 添加内容 {@link BlogAddDTO}
     */
    void addBlog(BlogAddDTO blog);

    /**
     * 更新博客列表
     *
     * @param blog 更新内容 {@link BlogUpdateDTO}
     */
    void updateBlog(BlogUpdateDTO blog);

    /**
     * 发布博客
     *
     * @param id 博客标识 {@link Blog#getId()}
     */
    void publishBlog(String id);

}
