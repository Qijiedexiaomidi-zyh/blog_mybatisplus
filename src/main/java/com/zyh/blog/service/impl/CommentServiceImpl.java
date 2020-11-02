package com.zyh.blog.service.impl;

import com.zyh.blog.dao.CommentMapper;
import com.zyh.blog.entity.Comment;
import com.zyh.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/8/31 15:34
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findAllCommentsByBlogId(Long blogId) {
        //1、获得顶级的评论
        List<Comment> topComments = commentMapper.findParentCommentNullByBlogId(blogId);
        //2、从所有的顶级评论中遍历出所有子类评论
        for (Comment topComment : topComments) {
            Long topCommentId = topComment.getId();
            List<Comment> childComments = commentMapper.findChildCommentsByBlogIdAndTopCommentId(topCommentId,blogId);
            topComment.setChildComments(childComments);
        }
        return topComments;
    }

    @Override
    public int saveReply(Comment comment) {
        Long parentCommentId = comment.getParentCommentId();
        //1、如果前端传过来的parentCommentId == -1，就将其置为null
        if (parentCommentId != -1){
            //有父级评论
            comment.setParentCommentId(parentCommentId);
        }else {
            //没有父级评论
            comment.setParentCommentId(null);
        }
        //2、如果前端传过来的repliedNickName == ""，就将其置为null
        String repliedNickName = comment.getRepliedNickName();
        if ("".equals(repliedNickName)){
            comment.setRepliedNickName(null);
        }
        //3、设置时间
        comment.setCreateDate(new Date());
        return commentMapper.insert(comment);
    }
}
