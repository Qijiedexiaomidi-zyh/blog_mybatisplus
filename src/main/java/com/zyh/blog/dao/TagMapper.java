package com.zyh.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/8/31 12:55
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> listTagTop(@Param("size") int size);
}
