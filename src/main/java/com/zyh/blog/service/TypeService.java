package com.zyh.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.entity.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/8/31 15:08
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
public interface TypeService {

    IPage<Type> listType(IPage<Type> page);

    int insert(Type type);

    Type findTypeByName(String name);

    Type findTypeById(Long id);

    int modify(Long id,Type type);

    int deleteById(Long id);

    List<Type> findAll();

    List<Type> listTypeTop(int size);
}
