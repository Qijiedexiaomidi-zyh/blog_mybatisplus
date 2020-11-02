package com.zyh.blog.service;

import com.zyh.blog.entity.Comment;

import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/8/31 15:33
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
public interface CommentService {

    List<Comment> findAllCommentsByBlogId(Long blogId);

    int saveReply(Comment comment);
}
