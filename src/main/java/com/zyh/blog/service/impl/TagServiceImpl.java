package com.zyh.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.dao.BlogMapper;
import com.zyh.blog.dao.TagMapper;
import com.zyh.blog.entity.Tag;
import com.zyh.blog.entity.Type;
import com.zyh.blog.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author ZYH
 * @Date 2020/8/31 15:33
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Service
public class TagServiceImpl implements TagService {

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public IPage<Tag> listTag(IPage<Tag> page) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return tagMapper.selectPage(page,wrapper);
    }

    @Override
    public Tag findTagByName(String name) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return tagMapper.selectOne(wrapper);
    }

    @Override
    public int insert(Tag tag) {
        //删除redis缓存
        redisTemplate.delete("listTagTop");
        return tagMapper.insert(tag);
    }

    @Override
    public Tag findTagById(Long id) {
        return tagMapper.selectById(id);
    }

    @Override
    public int update(Long id, Tag tag) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        //删除redis缓存
        redisTemplate.delete("listTagTop");
        return tagMapper.update(tag,wrapper);
    }

    @Override
    public int deleteById(Long id) {
        //删除redis缓存
        redisTemplate.delete("listTagTop");
        return tagMapper.deleteById(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagMapper.selectList(null);
    }

    @Override
    public List<Tag> listTagTop(int size) {
        //1、从缓存中查找数据
        String listTagTop = redisTemplate.opsForValue().get("listTagTop");
        //2、判断：redis缓存中是否存在数据
        if (listTagTop==null || listTagTop.equals("") || listTagTop.length()==0){
            List<Tag> tagList = tagMapper.listTagTop(size);
            logger.info("存入redis的数据："+tagList);
            //将集合数据转换成json数组
            listTagTop = JSON.toJSONString(tagList);
            //将转换后的json数组放入缓存中
            redisTemplate.opsForValue().set("listTagTop", listTagTop);
        }
        //因为要将数据赋给HTML页面，所以还要将从redis中取出来的json数据重新转化为Javabean形式的list集合数据，这样HTML页面才能接收
        List<Tag> tagListByRedis = JSON.parseArray(listTagTop,Tag.class);
        return tagListByRedis;
    }

    @Override
    public List<Tag> findTagByIds(String tagIds) {
        return tagMapper.selectBatchIds(Collections.singleton(tagIds));
    }

}
