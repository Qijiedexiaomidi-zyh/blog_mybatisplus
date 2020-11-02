package com.zyh.blog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.dao.BlogMapper;
import com.zyh.blog.entity.*;
import com.zyh.blog.service.BlogService;
import com.zyh.blog.service.BlogWithTagService;
import com.zyh.blog.service.TagService;
import com.zyh.blog.service.TypeService;
import com.zyh.blog.vo.BlogSearchVo;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final Logger logger = LoggerFactory.getLogger(TypeController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogWithTagService blogWithTagService;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 根据query条件（标题、分类、是否被推荐）分页查询博客列表
     */
    @GetMapping("/listBlog")
    public String listBlog(@RequestParam(defaultValue = "1") Long pageNum, Blog blog, Model model){
        int pageSize = 3; //指定页面大小
        Page<Blog> page = new Page<>(pageNum,pageSize); //创建分页对象，并设定当前页码和页面大小
        //TODO 这里的查询语句有毛病（recommend字段）
        IPage<Blog> blogByPage = blogService.listBlogByCondition(page,blog);
        model.addAttribute("blogByPage",blogByPage);
        //获取分类下拉列表
        List<Type> listType = typeService.findAll();
        model.addAttribute("listType",listType);
        logger.info("此时的当前页码pageNum："+pageNum);
        return "admin/blogs";
    }

    /**
     * 根据query条件（标题、分类、是否被推荐）分页查询博客列表，然后返回局部片段
     */
    @PostMapping("/listBlog/search")
    public String search(@RequestParam(defaultValue = "1") Long pageNum, Blog blog, Model model){
        int pageSize = 3; //指定页面大小
        Page<Blog> page = new Page<>(pageNum,pageSize); //创建分页对象，并设定当前页码和页面大小
        IPage<Blog> blogByPage = blogService.listBlogByCondition(page,blog);
        model.addAttribute("blogByPage",blogByPage);
        model.addAttribute("blog",blog);
        //获取分类下拉列表
        List<Type> listType = typeService.findAll();
        model.addAttribute("listType",listType);
        return "admin/blogs";
    }

    /**
     * 去博客新增页面
     */
    @GetMapping("/listBlog/goto_insert")
    public String goto_insert(Model model){
        //获取分类下拉列表
        List<Type> listType = typeService.findAll();
        model.addAttribute("listType",listType);
        //获取标签下拉列表
        List<Tag> listTag = tagService.findAll();
        model.addAttribute("listTag",listTag);
        //TODO 后端校验
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    /**
     * 新增和发布博客
     */
    @PostMapping("/listBlog/insert")
    public String insert(Blog blog, RedirectAttributes attributes, HttpSession session) throws IOException {
        //1、t_blog表的插入操作
        User loginUser = (User) session.getAttribute("loginUser");
        //TODO 此时，可以set对象，也可以set对象的主键id，都能够添加上数据，但是唯独user对象set不进去，必须set UserId
        blog.setUserId(loginUser.getId());
        blog.setType(typeService.findTypeById(blog.getTypeId()));
        //blog.setTypeId(blog.getTypeId());
        blog.setTags(tagService.findTagByIds(blog.getTagIds()));
        //blog.setTagIds(blog.getTagIds());
        //TODO 新增博客时，博文的标记必须点击一下，否则不会自动选择 "原创"
        int row = blogService.saveBlog(blog);
        if (row > 0){
            attributes.addFlashAttribute("message","博客新增成功");
            logger.info("博客新增操作成功");
        }else {
            attributes.addFlashAttribute("message","博客新增失败");
            logger.info("博客新增操作失败");
        }

//        //获取要存入到es中的数据
//        List<Blog> blogs = blogMapper.selectList(null);
//        List<BlogSearchVo> blogSearchVos = blogs.stream().map((item -> {
//            BlogSearchVo searchVo = new BlogSearchVo();
//            searchVo.setTitle(item.getTitle());
//            searchVo.setDescription(item.getDescription());
//            Type type = typeService.findTypeById(item.getTypeId());
//            searchVo.setTypeName(type.getName());
//            return searchVo;
//        })).collect(Collectors.toList());
//        //将数据存入es中
//        BulkRequest bulkRequest = new BulkRequest();
//        bulkRequest.timeout("2m");
//        for (int i = 0; i < blogSearchVos.size(); i++) {
//            bulkRequest.add(
//                    new IndexRequest("blog")
//                            .id(""+(i+1))
//                            .source(JSON.toJSONString(blogSearchVos.get(i)), XContentType.JSON)
//            );
//        }
//        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        //2、t_blog_tags表的插入操作
        String tagIds = blog.getTagIds();
        Long blogId = blog.getId();
        String[] split = tagIds.split(",");
        for (String s : split) {
            Long l = Long.parseLong(s);
            BlogWithTag blogWithTag = new BlogWithTag();
            blogWithTag.setBlogId(blogId);
            blogWithTag.setTagId(l);
            Tag tag = tagService.findTagById(l);
            blogWithTag.setTagName(tag.getName());
            blogWithTagService.saveBlogAndTag(blogWithTag);
        }
        return "redirect:/admin/listBlog";
    }

    /**
     * 去博客修改页面
     */
    @GetMapping("/listBlog/goto_modify")
    public String goto_update(Long id, Model model){
        //获取分类下拉列表
        List<Type> listType = typeService.findAll();
        model.addAttribute("listType",listType);
        //获取标签下拉列表
        List<Tag> listTag = tagService.findAll();
        model.addAttribute("listTag",listTag);
        //将原来的数据查询出来，在编辑框中回显
        Blog blog = blogService.findBlogById(id);
        model.addAttribute("blog",blog);
        //TODO 后端校验
        return "admin/blogs-input";
    }

    /**
     * 修改博客
     */
    @PostMapping("/listBlog/modify")
    public String modify(Blog blog, Long id, RedirectAttributes attributes, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        blog.setUserId(loginUser.getId());
        blog.setTypeId(blog.getTypeId());
        blog.setTagIds(blog.getTagIds());
        int row = blogService.updateBlog(blog);
        if (row > 0){
            attributes.addFlashAttribute("message","博客修改成功");
            logger.info("博客修改操作成功");
        }else {
            attributes.addFlashAttribute("message","博客修改失败");
            logger.info("博客修改操作失败");
        }
        return "redirect:/admin/listBlog";
    }

    /**
     * 博客删除操作
     */
    @GetMapping("/listBlog/delete")
    public String delete(Long id, RedirectAttributes attributes){
        int row = blogService.deleteById(id);
        if (row > 0){
            attributes.addFlashAttribute("message","博客删除成功");
            logger.info("博客删除成功");
        }else {
            attributes.addFlashAttribute("message","博客删除失败");
            logger.info("博客删除失败");
        }
        return "redirect:/admin/listBlog";
    }
}
