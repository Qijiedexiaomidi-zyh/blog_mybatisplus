package com.zyh.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("t_tag")
public class Tag implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "标签名称不能为空")
    private String name;

    @TableField(exist = false)
    private Integer blogCount;

    @TableField(exist = false)
    private List<Blog> blogs;
}
