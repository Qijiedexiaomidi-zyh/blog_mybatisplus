package com.zyh.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author ZYH
 * @Date 2020/9/12 18:36
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Data
@TableName(value = "t_blog_tags")
public class BlogWithTag {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long blogId;
    private Long tagId;
    private String tagName;
}
