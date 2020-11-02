package com.zyh.blog.service;

import com.zyh.blog.entity.User;

/**
 * @Author ZYH
 * @Date 2020/8/31 15:32
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
public interface UserService {

    User findUserByUsername(String userName);
}
