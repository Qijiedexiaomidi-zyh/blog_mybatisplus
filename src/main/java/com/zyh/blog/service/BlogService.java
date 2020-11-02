package com.zyh.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.entity.Blog;
import com.zyh.blog.vo.BlogVo;

import java.util.List;
import java.util.Map;

/**
 * @Author ZYH
 * @Date 2020/8/31 15:33
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
public interface BlogService {

    IPage<Blog> listBlogByCondition(IPage<Blog> page,Blog blog);

    int saveBlog(Blog blog);

    Blog findBlogById(Long id);

    int updateBlog(Blog blog);

    int deleteById(Long id);

    IPage<Blog> listBlog(IPage<Blog> page);

    List<BlogVo> listBlogByRecommendTop(int size);

    Blog getBlogAndConvert(Long id);

    IPage<Blog> listBlogByquery(IPage<Blog> page,String keyword);

    IPage<Blog> listBlogByTypeId(IPage<Blog> page, Long id);

    IPage<Blog> listBlogByTagId(IPage<Blog> page, Long id);

    Map<String,List<Blog>> findBlogGroupByYear();

    Integer findBlogCount();

    IPage<Blog> listBlogByKeyword(IPage<Blog> page);
}
