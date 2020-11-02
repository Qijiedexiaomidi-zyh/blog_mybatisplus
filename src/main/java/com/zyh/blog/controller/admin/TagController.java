package com.zyh.blog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.entity.Tag;
import com.zyh.blog.entity.Type;
import com.zyh.blog.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagService tagService;

    /**
     * 分页展示标签列表
     */
    @GetMapping("/listTag")
    public String listTag(@RequestParam(defaultValue = "1") Long pageNum, Model model){
        int pageSize = 3; //设置页面大小
        Page<Tag> page = new Page<>(pageNum,pageSize); //创建分页对象，设定当前页码以及每页数量
        IPage<Tag> tagByPage = tagService.listTag(page); //分页查询
        model.addAttribute("tagByPage",tagByPage);
        return "admin/tags";
    }

    /**
     * 去标签新增页面
     */
    @GetMapping("/listTag/goto_insert")
    public String goto_insert(Model model){
        //因为要实现后端校验，所以要给tags-input.html页面传一个空的Tag对象
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    /**
     * 标签新增操作
     */
    @PostMapping("/listTag/insert")
    public String insert(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //1、先判断新增输入框中的值是否为空
        if (result.hasErrors()){
            return "admin/tags-input";
        }

        //2、再判断输入的值数据库中是否已经存在
        if (tagService.findTagByName(tag.getName()) != null){
            result.rejectValue("name","nameError","该标签名称已存在，请重新输入");
            return "admin/tags-input";
        }

        //3、标签新增操作
        int row = tagService.insert(tag);
        if (row > 0){
            attributes.addFlashAttribute("message","新增标签操作成功");
            logger.info("标签名称新增成功");
        }else {
            attributes.addFlashAttribute("message","新增标签操作失败");
            logger.info("标签名称新增失败");
        }
        return "redirect:/admin/listTag";
    }

    /**
     * 去标签修改页面
     */
    @GetMapping("/listTag/goto_modify")
    public String goto_modify(Long id, Model model){
        Tag tag = tagService.findTagById(id);
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }

    /**
     * 修改标签名称操作
     */
    @PostMapping("/listTag/modify")
    public String modify(@Valid Tag tag, BindingResult result, Long id, RedirectAttributes attributes){
        //1、先判断修改输入框中值是否为空
        if (result.hasErrors()){
            return "admin/tags-input";
        }

        //2、再判断修改输入框中的值数据库中是否已存在
        if (tagService.findTagByName(tag.getName()) != null){
            result.rejectValue("name","nameError","该标签名称已存在，请重新输入");
            return "admin/tags-input";
        }

        //3、标签名称修改操作
        int row = tagService.update(id, tag);
        if (row > 0){
            attributes.addFlashAttribute("message","标签名称修改成功");
            logger.info("标签名称修改成功");
        }else {
            attributes.addFlashAttribute("message","标签名称修改失败");
            logger.info("标签名称修改失败");
        }
        return "redirect:/admin/listTag";
    }

    /**
     * 删除标签
     */
    @GetMapping("/listTag/delete")
    public String delete(Long id, RedirectAttributes attributes){
        int row = tagService.deleteById(id);
        if (row > 0){
            attributes.addFlashAttribute("message","删除标签成功");
            logger.info("删除标签成功");
        }else {
            attributes.addFlashAttribute("message","删除标签失败");
            logger.info("删除标签失败");
        }
        return "redirect:/admin/listTag";
    }

}
