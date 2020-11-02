package com.zyh.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/8/31 12:56
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> findParentCommentNullByBlogId(@Param("blogId") Long blogId);

    List<Comment> findChildCommentsByBlogIdAndTopCommentId(@Param("topCommentId") Long topCommentId,@Param("blogId")Long blogId);
}
