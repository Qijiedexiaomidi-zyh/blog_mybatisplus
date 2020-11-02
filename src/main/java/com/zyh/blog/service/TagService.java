package com.zyh.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.entity.Tag;

import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/8/31 15:32
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
public interface TagService {

    IPage<Tag> listTag(IPage<Tag> page);

    Tag findTagByName(String name);

    int insert(Tag tag);

    Tag findTagById(Long id);

    int update(Long id, Tag tag);

    int deleteById(Long id);

    List<Tag> findAll();

    List<Tag> listTagTop(int size);

    List<Tag> findTagByIds(String tagIds);

}
