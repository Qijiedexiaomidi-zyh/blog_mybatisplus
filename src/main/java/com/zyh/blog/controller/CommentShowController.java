package com.zyh.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyh.blog.entity.Comment;
import com.zyh.blog.entity.User;
import com.zyh.blog.service.BlogService;
import com.zyh.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/9/7 18:03
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Controller
@RequestMapping("/")
public class CommentShowController {

    private static final Logger logger = LoggerFactory.getLogger(CommentShowController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 根据blogId返回评论的内容
     */
    @GetMapping("/listComment/{blogId}")
    public String listComment(@PathVariable("blogId")Long blogId, Model model){
        //TODO 根据blogId查询出所有的评论信息，代码有问题，只能查询到两层关系的评论
        List<Comment> listComment = commentService.findAllCommentsByBlogId(blogId);
        model.addAttribute("listComment",listComment);
        logger.info("评论的内容：{}",listComment);
        return "blog :: commentList";
    }

    /**
     * 保存回复评论的内容，并且局部刷新评论区
     */
    @PostMapping("/listComment/saveReply")
    public String saveReply(Comment comment, HttpSession session){
        //1、设置博客id
        Long blogId = comment.getBlogId();
        comment.setBlogId(blogId);
        comment.setBlog(blogService.findBlogById(blogId));
        //2、判断评论的用户是否为博主自己
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null){
            comment.setAvatar(loginUser.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }
        //3、保存评论操作
        int row = commentService.saveReply(comment);
        if (row > 0){
            logger.info("回复评论成功");
        }else {
            logger.info("回复评论失败");
        }
        return "redirect:/listComment/" + blogId;
    }

}
