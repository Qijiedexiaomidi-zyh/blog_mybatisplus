package com.zyh.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author ZYH
 * @Date 2020/9/17 18:23
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Data
public class BlogVo implements Serializable {

    private Long id;
    private String title;
    private Date createDate;
}
