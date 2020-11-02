package com.zyh.blog.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.entity.Blog;
import com.zyh.blog.exception.NotFoundException;
import com.zyh.blog.service.BlogService;
import com.zyh.blog.service.TagService;
import com.zyh.blog.service.TypeService;
import com.zyh.blog.vo.BlogSearchVo;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author ZYH
 * @Date 2020/8/31 11:38
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 测试NotFoundException错误
     */
    @GetMapping("/testError")
    @ResponseBody
    public String testError(){
        String blog = null;
        if (blog == null){
            throw new NotFoundException("博客不存在哟");
        }
        return "haha";
    }

    /**
     * 首页展示
     */
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") Long pageNum, Blog blog, Model model){
        int pageSize = 5; //设置默认页面大小
        Page<Blog> page = new Page<>(pageNum,pageSize); //创建分页对象，并设定当前页码和页面大小

        //1、首页分页显示博客数据
        IPage<Blog> blogByPage = blogService.listBlog(page);
        model.addAttribute("blogByPage",blogByPage);
        //2、首页显示博客类别，按照博客数量倒序排列
        model.addAttribute("types",typeService.listTypeTop(6));
        //3、首页显示博客标签，按照博客数量倒序排列
        model.addAttribute("tags",tagService.listTagTop(10));
        //4、首页显示允许被推荐的博客，按照修改时间倒序排列
        model.addAttribute("blogs",blogService.listBlogByRecommendTop(8));
        return "index";
    }

    /**
     * 首页顶部的查询
     */
    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Long pageNum,String keyword, Model model){
        int pageSize = 3;
        Page<Blog> page = new Page<>(pageNum,pageSize);
        IPage<Blog> blogByPage = blogService.listBlogByquery(page,keyword);
        model.addAttribute("blogByPage",blogByPage);
        model.addAttribute("keyword",keyword);
        return "search";
    }

    /**
     * 首页顶部的查询（elasticsearch搜索）
     */
    @ResponseBody
    @GetMapping("/elasticsearch/{keywords}/{pageNum}/{pageSize}")
    public List<Map<String, Object>> elasticsearch(@PathVariable("keywords") String keyword,
                                                   @PathVariable("pageNum") int pageNum,
                                                   @PathVariable("pageSize") int pageSize) throws IOException {

        //1、设置要搜索的索引
        SearchRequest searchResult = new SearchRequest("blog");
        //2、构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //2.1、精确匹配
        //TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "description", "typeName");
        sourceBuilder.query(multiMatchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //2.2、分页
        sourceBuilder.from(pageNum);
        sourceBuilder.size(pageSize);

        //2.3、高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title"); //那哪字段进行高亮
        highlightBuilder.field("description"); //那哪字段进行高亮
        highlightBuilder.field("typeName"); //那哪字段进行高亮
        highlightBuilder.requireFieldMatch(false); //当一个字段中有多个keywords时，只高亮第一个
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        //3、将数据放入，发送请求，获得相应数据
        searchResult.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchResult, RequestOptions.DEFAULT);
        //4、将获得的数据封装进集合中
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            //原来的所有数据
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //所有的高亮数据
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            HighlightField description = highlightFields.get("description");
            HighlightField typeName = highlightFields.get("typeName");
            //判断
            if (title != null){
                Text[] texts = title.fragments();
                String new_title = "";
                for (Text text : texts) {
                    new_title = new_title + text;
                }
                //将原来的未高亮字段替换成高亮字段
                sourceAsMap.put("title",new_title);
            }
            if (description != null){
                Text[] texts = description.fragments();
                String new_description = "";
                for (Text text : texts) {
                    new_description = new_description + text;
                }
                //将原来的未高亮字段替换成高亮字段
                sourceAsMap.put("description",new_description);
            }
            if (typeName != null){
                Text[] texts = typeName.fragments();
                String new_typeName = "";
                for (Text text : texts) {
                    new_typeName = new_typeName + text;
                }
                //将原来的未高亮字段替换成高亮字段
                sourceAsMap.put("typeName",new_typeName);
            }
            mapList.add(sourceAsMap);
        }
        logger.info("搜索到的es的数据："+mapList);
        return mapList;
    }

    /**
     * 单个博客数据展示（Markdown格式转化为HTML）
     */
    @GetMapping("/blogView/{id}")
    public String blogView(@PathVariable("id") Long id, Model model){
        model.addAttribute("blog",blogService.getBlogAndConvert(id));
        return "blog";
    }

    /**
     * 网站的底部显示最新的三个博客
     */
    @GetMapping("/listNewBlog")
    public String listNewBlog(Model model){
        model.addAttribute("listNewBlog",blogService.listBlogByRecommendTop(3));
        return "common/reception :: newBlogList";
    }
}
