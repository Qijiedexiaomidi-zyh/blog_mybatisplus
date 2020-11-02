package com.zyh.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.entity.Blog;
import com.zyh.blog.entity.Type;
import com.zyh.blog.service.BlogService;
import com.zyh.blog.service.TypeService;
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
 * @Date 2020/9/7 10:34
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Controller
@RequestMapping("/")
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    /**
     * 在首页菜单栏点击分类，把所有分类名称以及博客数量展示出来，并且分页展示该分类下所有的博客
     */
    @GetMapping("/listType/{id}")
    public String listType(@RequestParam(defaultValue = "1") Long pageNum, @PathVariable("id") Long id, Model model){
        //1、查询出所有的分类，偷个懒，用了以前的方法
        List<Type> listType = typeService.listTypeTop(10000);
        if (id == -1){
            id = listType.get(0).getId();
        }
        model.addAttribute("listType",listType);
        //2、根据分类id分页展示所属的所有博客的数据
        int pageSize = 3;
        Page<Blog> page = new Page<>(pageNum,pageSize);
        IPage<Blog> blogByPage = blogService.listBlogByTypeId(page,id);
        model.addAttribute("blogByPage",blogByPage);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
