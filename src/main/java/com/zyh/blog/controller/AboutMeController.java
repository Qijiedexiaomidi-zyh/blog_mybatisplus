package com.zyh.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ZYH
 * @Date 2020/9/7 16:34
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Controller
@RequestMapping("/")
public class AboutMeController {

    @GetMapping("/listAboutMe")
    public String listAboutMe(){
        return "about";
    }
}
