package com.zyh.blog.controller;

import com.zyh.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ZYH
 * @Date 2020/9/7 15:52
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Controller
@RequestMapping("/")
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/listArchive")
    public String listArchive(Model model){
        //1、以年为单位，查询出所有的博客
        model.addAttribute("archiveMap",blogService.findBlogGroupByYear());
        //2、查询出博客的总数量
        model.addAttribute("blogCount",blogService.findBlogCount());
        return "archives";
    }
}
