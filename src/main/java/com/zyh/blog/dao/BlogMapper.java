package com.zyh.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyh.blog.entity.Blog;
import com.zyh.blog.vo.BlogVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/8/31 12:56
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    IPage<Blog> listBlogByCondition(@Param("page") IPage<Blog> page, @Param("blog") Blog blog);

    IPage<Blog> listBlog(IPage<Blog> page);

    void updateBlogViews(Long id);

    IPage<Blog> listBlogByquery(@Param("page") IPage<Blog> page, @Param("keyword") String keyword);

    Blog findBlogById(Long id);

    IPage<Blog> listBlogByTypeId(@Param("page") IPage<Blog> page, @Param("id") Long id);

    List<String> findYearByBlog();

    List<Blog> findBlogByYear(String year);

    List<String> findTagIds();

    IPage<Blog> listBlogByTagId(@Param("page") IPage<Blog> page, @Param("id") Long id);

}
