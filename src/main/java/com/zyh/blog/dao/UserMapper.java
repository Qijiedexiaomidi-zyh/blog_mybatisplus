package com.zyh.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyh.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author ZYH
 * @Date 2020/8/31 12:55
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findUserByUsername(@Param("userName") String userName);
}
