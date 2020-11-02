package com.zyh.blog.service.impl;

import com.zyh.blog.dao.BlogWithTagMapper;
import com.zyh.blog.entity.BlogWithTag;
import com.zyh.blog.service.BlogWithTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ZYH
 * @Date 2020/9/12 19:03
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Service
public class BlogWithTagServiceImpl implements BlogWithTagService {

    @Autowired
    private BlogWithTagMapper blogWithTagMapper;

    @Override
    public void saveBlogAndTag(BlogWithTag blogWithTag) {
        blogWithTagMapper.insert(blogWithTag);
    }
}
