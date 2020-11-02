package com.zyh.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zyh.blog.dao.BlogMapper;
import com.zyh.blog.entity.Blog;
import com.zyh.blog.service.BlogService;
import com.zyh.blog.utils.MarkdownUtils;
import com.zyh.blog.vo.BlogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author ZYH
 * @Date 2020/8/31 15:33
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Service
public class BlogServiceImpl implements BlogService {

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 根据query条件（标题、分类、是否被推荐）分页查询博客列表
     */
    @Override
    public IPage<Blog> listBlogByCondition(IPage<Blog> page, Blog blog) {
        return blogMapper.listBlogByCondition(page, blog);
    }

    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateDate(new Date());
        blog.setUpdateDate(new Date());
        blog.setViews(0);
        //删除redis缓存
        redisTemplate.delete("listBlogByRecommendTop");
        redisTemplate.delete("listTypeTop");
        redisTemplate.delete("listTagTop");
        return blogMapper.insert(blog);
    }

    @Override
    public Blog findBlogById(Long id) {
        return blogMapper.selectById(id);
    }

    @Override
    public int updateBlog(Blog blog) {
        blog.setUpdateDate(new Date());
        //删除redis缓存
        redisTemplate.delete("listBlogByRecommendTop");
        redisTemplate.delete("listTypeTop");
        redisTemplate.delete("listTagTop");
        return blogMapper.updateById(blog);
    }

    @Override
    public int deleteById(Long id) {
        //删除redis缓存
        redisTemplate.delete("listBlogByRecommendTop");
        redisTemplate.delete("listTypeTop");
        redisTemplate.delete("listTagTop");
        return blogMapper.deleteById(id);
    }

    @Override
    public IPage<Blog> listBlog(IPage<Blog> page) {
        return blogMapper.listBlog(page);
    }

    @Override
    public List<BlogVo> listBlogByRecommendTop(int size) {
        //1、从缓存中查找数据
        String listBlogByRecommendTop = redisTemplate.opsForValue().get("listBlogByRecommendTop");
        //2、判断：redis缓存中是否存在数据
        if (listBlogByRecommendTop==null || listBlogByRecommendTop.equals("") || listBlogByRecommendTop.length()==0){
            QueryWrapper<Blog> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("create_date").last("limit " + size);
            List<Blog> blogList = blogMapper.selectList(wrapper);
            List<BlogVo> blogVos = blogList.stream().map((item) -> {
                BlogVo blogVo = new BlogVo();
                blogVo.setId(item.getId());
                blogVo.setTitle(item.getTitle());
                blogVo.setCreateDate(item.getCreateDate());
                return blogVo;
            }).collect(Collectors.toList());
            logger.info("存入redis的数据："+blogVos);
            //将集合数据转换成json数组
            listBlogByRecommendTop = JSON.toJSONString(blogVos);
            //将转换后的json数组放入缓存中
            redisTemplate.opsForValue().set("listBlogByRecommendTop", listBlogByRecommendTop);
        }
        //因为要将数据赋给HTML页面，所以还要将从redis中取出来的json数据重新转化为Javabean形式的list集合数据，这样HTML页面才能接收
        List<BlogVo> blogVosByRedis = JSON.parseArray(listBlogByRecommendTop,BlogVo.class);
        return blogVosByRedis;
    }

    @Override
    public Blog getBlogAndConvert(Long id) {
        Blog blog = blogMapper.findBlogById(id);
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogMapper.updateBlogViews(id);
        return blog;
    }

    @Override
    public IPage<Blog> listBlogByquery(IPage<Blog> page,String keyword) {
        return blogMapper.listBlogByquery(page,keyword);
    }

    @Override
    public IPage<Blog> listBlogByTypeId(IPage<Blog> page, Long id) {
        return blogMapper.listBlogByTypeId(page, id);
    }

    @Override
    public IPage<Blog> listBlogByTagId(IPage<Blog> page, Long id) {
        return blogMapper.listBlogByTagId(page, id);
    }

    @Override
    public Map<String, List<Blog>> findBlogGroupByYear() {
        //1、根据blog来查询出所有的年份
        List<String> years = blogMapper.findYearByBlog();
        //2、将每个年份遍历，并求出每一年对应的所有博客数据
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogMapper.findBlogByYear(year));
        }
        return map;
    }

    @Override
    public Integer findBlogCount() {
        return blogMapper.selectCount(null);
    }

    @Override
    public IPage<Blog> listBlogByKeyword(IPage<Blog> page) {
        return null;
    }
}
