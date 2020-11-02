package com.zyh.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("t_comment")
public class Comment {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String nickName;
    private String email;
    private String content;
    private String avatar;

    private Date createDate;

    @TableField(exist = false)
    private Blog blog;
    private Long blogId;

    private boolean adminComment; //判断是否为博主的字段

    @TableField(exist = false)
    private Comment parentComment; //父类的回复
    private Long parentCommentId; //当前评论的父级评论id
    private String repliedNickName; //被回复人的昵称

    @TableField(exist = false)
    private List<Comment> childComments; //子类的回复

}
