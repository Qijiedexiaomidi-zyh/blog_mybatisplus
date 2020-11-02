package com.zyh.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.entity.Blog;
import com.zyh.blog.entity.Tag;
import com.zyh.blog.service.BlogService;
import com.zyh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/9/7 11:09
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Controller
@RequestMapping("/")
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    /**
     * 在首页菜单栏点击标签，把所有标签名称以及博客数量展示出来，并且分页展示该标签下所有的博客
     */
    @GetMapping("/listTag/{id}")
    public String listTag(@RequestParam(defaultValue = "1") Long pageNum, @PathVariable("id") Long id, Model model){
        //1、查询出所有的标签，偷个懒，用了以前的方法
        //TODO 这里的查询语句有毛病
        List<Tag> listTag = tagService.listTagTop(10000);
        if (id == -1){
            id = listTag.get(0).getId();
        }
        model.addAttribute("listTag",listTag);
        //2、根据标签id分页展示博客的数据
        int pageSize = 3;
        Page<Blog> page = new Page<>(pageNum,pageSize);
        IPage<Blog> blogByPage = blogService.listBlogByTagId(page,id);
        model.addAttribute("blogByPage",blogByPage);
        model.addAttribute("activeTagId",id);
        //3、根据标签id查询出所属博客的所有标签的name值
        return "tags";
    }
}
