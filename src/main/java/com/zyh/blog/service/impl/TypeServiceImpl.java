package com.zyh.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.dao.TypeMapper;
import com.zyh.blog.entity.Blog;
import com.zyh.blog.entity.Type;
import com.zyh.blog.service.TypeService;
import com.zyh.blog.vo.BlogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author ZYH
 * @Date 2020/8/31 15:09
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Service
public class TypeServiceImpl implements TypeService {

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public IPage<Type> listType(IPage<Type> page) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return typeMapper.selectPage(page,wrapper);
    }

    @Override
    public int insert(Type type) {
        //删除redis缓存
        redisTemplate.delete("listTypeTop");
        return typeMapper.insert(type);
    }

    @Override
    public Type findTypeByName(String name) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        return typeMapper.selectOne(wrapper);
    }

    @Override
    public Type findTypeById(Long id) {
        return typeMapper.selectById(id);
    }

    @Override
    public int modify(Long id,Type type) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        //删除redis缓存
        redisTemplate.delete("listTypeTop");
        return typeMapper.update(type,wrapper);
    }

    @Override
    public int deleteById(Long id) {
        //删除redis缓存
        redisTemplate.delete("listTypeTop");
        return typeMapper.deleteById(id);
    }

    @Override
    public List<Type> findAll() {
        return typeMapper.selectList(null);
    }

    @Override
    public List<Type> listTypeTop(int size) {
        //1、从缓存中查找数据
        String listTypeTop = redisTemplate.opsForValue().get("listTypeTop");
        //2、判断：redis缓存中是否存在数据
        if (listTypeTop==null || listTypeTop.equals("") || listTypeTop.length()==0){
            List<Type> typeList = typeMapper.listTypeTop(size);
            logger.info("存入redis的数据："+typeList);
            //将集合数据转换成json数组
            listTypeTop = JSON.toJSONString(typeList);
            //将转换后的json数组放入缓存中
            redisTemplate.opsForValue().set("listTypeTop", listTypeTop);
        }
        //因为要将数据赋给HTML页面，所以还要将从redis中取出来的json数据重新转化为Javabean形式的list集合数据，这样HTML页面才能接收
        List<Type> typeListByRedis = JSON.parseArray(listTypeTop,Type.class);
        return typeListByRedis;
    }
}
