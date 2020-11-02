package com.zyh.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_blog")
public class Blog implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;//博客内容：使用大对象数据类型
    private String firstPicture;//首图
    private String description;//博客描述：使用大对象数据类型
    private String flag;//标记（是否原创、转载、翻译）
    private Integer views;//标记浏览次数
    private boolean appreciation;//赞赏开启
    private boolean shareStatement;//转载声明开启
    private boolean commentabled;//评论开启
    private boolean published;//发布开启
    private boolean recommend;//是否推荐

    private Date createDate;
    private Date updateDate;
    @TableField(exist = false)
    private String createDateByYear;

    @TableField(exist = false)
    private Type type;
    private Long typeId;

    @TableField(exist = false)
    private User user;
    private Long userId;

    @TableField(exist = false)
    private List<Tag> tags;

    private String tagIds;//标签id的集合

    @TableField(exist = false)
    private List<Comment> comments = new ArrayList<>();
}
