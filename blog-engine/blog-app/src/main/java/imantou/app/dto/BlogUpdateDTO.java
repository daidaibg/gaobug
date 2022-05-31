package imantou.app.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import imantou.common.annotation.Verify;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 博客文章
 *
 * @TableName t_blog
 */
@TableName(value = "t_blog")
@Data
public class BlogUpdateDTO implements Serializable {
    /**
     * 数据主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 博客标题
     */
    @Verify(required = true, errorMsg = "帮我起个名吧~")
    private String title;

    /**
     * 博客简介
     */
    @Verify(required = true, errorMsg = "都不介绍一下我吗")
    private String summary;

    /**
     * 博客内容
     */
    @Verify(required = true, errorMsg = "感觉心里空空的~")
    private String content;

    /**
     * 标签标识
     */
    private String tag;

    /**
     * 博客点击数
     */
    private Integer clickCount;

    /**
     * 博客收藏数
     */
    private Integer collectCount;

    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 作者
     */
    @Verify(required = true, errorMsg = "咦~主人去哪了？")
    private String author;

    /**
     * 文章出处
     */
    private String articlesPart;

    /**
     * 博客分类标识
     */
    private Long categoryId;

    /**
     * 是否发布：0：否，1：是
     */
    private Integer publish;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 是否开启评论：0：否 1：是
     */
    private Integer openComment;

    /**
     * 置顶（数字越大越靠前）
     */
    private Integer hot;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}