package com.gaobug.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaobug.base.context.JwtContext;
import com.gaobug.response.exception.BusinessException;
import com.gaobug.platform.dao.BlogMapper;
import com.gaobug.platform.domain.Blog;
import com.gaobug.platform.dto.BlogAddDTO;
import com.gaobug.platform.dto.BlogSearchDTO;
import com.gaobug.platform.dto.BlogUpdateDTO;
import com.gaobug.platform.service.BlogService;
import com.gaobug.platform.vo.BlogPageVO;
import com.gaobug.utils.UserContextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author gaobug
 * @description 针对表【t_blog(博客文章)】的数据库操作Service实现
 * @createDate 2022-05-23 13:39:11
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Resource
    private BlogMapper blogMapper;

    @Override
    public IPage<?> getIndexBlogPage(String type, Page<Blog> page) {
        IPage<BlogPageVO> blogPageVOIPage;
        switch (type) {
            case "1":
                // 推荐
                // TODO: 2022/5/25 根据用户行为推荐列表
                blogPageVOIPage = blogMapper.selectRecommendBlogPage(page);
                break;
//            case "2":
//                // 最新
//                return blogMapper.selectNewestBlogPage(page);
            case "3":
                // 最热
                blogPageVOIPage = blogMapper.selectHotBlogPage(page);
                break;
            default:
                blogPageVOIPage = blogMapper.selectNewestBlogPage(page);
                break;
        }
        if (0 == blogPageVOIPage.getTotal()) {
            return blogPageVOIPage;
        }
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        blogPageVOIPage.getRecords().forEach(b -> {
            Date createTime = b.getCreateTime();
            long reset = createTime.getTime(); // 获取指定时间的毫秒数
            long dateDiff = nowTime - reset;
            if (dateDiff > 0) {
                long dateTemp1 = dateDiff / 1000; // 秒
                long dateTemp2 = dateTemp1 / 60; // 分钟
                long dateTemp3 = dateTemp2 / 60; // 小时
                long dateTemp4 = dateTemp3 / 24; // 天数
                long dateTemp5 = dateTemp4 / 30; // 月数
                long dateTemp6 = dateTemp5 / 12; // 年数
                if (dateTemp6 > 0) {
                    b.setPublishTime(dateTemp6 + "年前");
                } else if (dateTemp5 > 0) {
                    b.setPublishTime(dateTemp5 + "个月前");
                } else if (dateTemp4 > 0) {
                    b.setPublishTime(dateTemp4 + "天前");
                } else if (dateTemp3 > 0) {
                    b.setPublishTime(dateTemp3 + "小时前");
                } else if (dateTemp2 > 0) {
                    b.setPublishTime(dateTemp2 + "分钟前");
                } else if (dateTemp1 > 0) {
                    b.setPublishTime("刚刚");
                }
            } else {
                b.setPublishTime("穿越来的");
            }
        });
        return blogPageVOIPage;
    }

    @Override
    public IPage<?> getSearchBlogPage(Page<Blog> page, BlogSearchDTO searchDTO) {
        return null;
    }

    @Override
    public IPage<?> getBlogPageByUser(Page<Blog> page, Blog blog) {
        return blogMapper.selectPage(page, new QueryWrapper<>(blog));
    }

    @Override
    public void addBlog(BlogAddDTO blog) {
        Blog blogForSave = new Blog();
        blogForSave.setAuthor(JwtContext.getUserId());
        BeanUtils.copyProperties(blog, blogForSave);
        blogMapper.insert(blogForSave);
    }

    @Override
    public void updateBlog(BlogUpdateDTO blog) {
        Blog blogForUpdate = new Blog();
        Blog blogExist = blogMapper.selectById(blog.getId());
        if (null == blogExist) {
            throw new BusinessException("文章信息不存在");
        }
        if (Objects.equals(JwtContext.getUserId(),blogExist.getAuthor())){
            throw new BusinessException("不是文章作者，不可编辑");
        }
        BeanUtils.copyProperties(blog, blogForUpdate);
        blogMapper.updateById(blogForUpdate);
    }

    @Override
    public void publishBlog(String id) {
        Blog blogExist = blogMapper.selectById(id);
        if (null == blogExist) {
            throw new BusinessException("文章信息不存在");
        }
        if (Objects.equals(blogExist.getPublish(), 1)) {
            throw new BusinessException("文章已发布状态");
        }
        if (Objects.equals(blogExist.getAuthor(), JwtContext.getUserId())) {
            throw new BusinessException("不是文章作者，不可编辑");
        }
        Blog blogForUpdate = new Blog();
        blogForUpdate.setId(blogExist.getId());
        blogForUpdate.setPublish(1);
        blogForUpdate.setUpdateTime(new Date());
        blogMapper.updateById(blogForUpdate);
    }
}




