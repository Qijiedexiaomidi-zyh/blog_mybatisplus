package com.zyh.blog.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyh.blog.entity.Type;
import com.zyh.blog.service.TypeService;
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
import java.util.List;

/**
 * @Author ZYH
 * @Date 2020/8/31 16:23
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    private static final Logger logger = LoggerFactory.getLogger(TypeController.class);

    @Autowired
    private TypeService typeService;

    /**
     * 分页展示分类数据
     */
    @GetMapping("/listType")
    public String listType(@RequestParam(defaultValue = "1") Long pageNum, Model model){
        int pageSize = 3; //设置页面大小
        Page<Type> page = new Page<>(pageNum,pageSize); //创建分页对象，设定当前页码以及每页数量
        IPage<Type> typeByPage = typeService.listType(page); //分页查询
        model.addAttribute("typeByPage",typeByPage);
        return "admin/types";
    }

    /**
     * 去分类新增页面
     */
    @GetMapping("/listType/goto_insert")
    public String goto_insert(Model model){
        //因为要加后端校验，所以要先在types-input.html页面返回一个空的type数据对象
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    /**
     * 分类新增操作
     */
    @PostMapping("/listType/insert")
    public String insert(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //1、先判断输入框中是否为空
        if (result.hasErrors()){
            return "admin/types-input";
        }
        //2、再判断输入框中的值与数据库中是否重复
        if (typeService.findTypeByName(type.getName()) != null){
            //第一个参数：验证的是哪个字段
            //第二个参数：自定义错误key值
            //第三个参数：自定义错误message值
            result.rejectValue("name","nameError","输入的分类名称已存在，请重新输入");
            return "admin/types-input";
        }
        //3、新增操作
        int row = typeService.insert(type);
        if (row > 0){
            attributes.addFlashAttribute("message","新增操作成功");
        }else {
            attributes.addFlashAttribute("message","新增操作失败");
        }
        return "redirect:/admin/listType";
    }

    /**
     * 去分类修改页面
     */
    @GetMapping("/listType/goto_modify")
    public String goto_modify(Long id, Model model){
        Type type = typeService.findTypeById(id);
        model.addAttribute("type",type);
        return "admin/types-input";
    }

    /**
     * 分类修改操作
     */
    @PostMapping("/listType/modify")
    public String modify(Long id, @Valid Type type, BindingResult result, RedirectAttributes attributes){
        //1、先判断输入框中是否为空
        if (result.hasErrors()){
            return "admin/types-input";
        }
        //2、再判断输入框中的值与数据库中是否重复
        if (typeService.findTypeByName(type.getName()) != null){
            //第一个参数：验证的是哪个字段
            //第二个参数：自定义错误key值
            //第三个参数：自定义错误message值
            result.rejectValue("name","nameError","输入的分类名称已存在，请重新输入");
            return "admin/types-input";
        }
        //3、修改操作
        int row = typeService.modify(id,type);
        if (row > 0){
            attributes.addFlashAttribute("message","修改分类操作成功");
            logger.info("修改成功");
        }else {
            attributes.addFlashAttribute("message","修改分类操作失败");
            logger.info("修改失败");
        }
        return "redirect:/admin/listType";
    }

    /**
     * 分类删除
     */
    @GetMapping("/listType/delete")
    public String delete(Long id,RedirectAttributes attributes){
        int row = typeService.deleteById(id);
        if (row > 0){
            attributes.addFlashAttribute("message","删除分类操作成功");
            logger.info("删除成功");
        }else {
            attributes.addFlashAttribute("message","删除分类操作失败");
            logger.info("删除失败");
        }
        return "redirect:/admin/listType";
    }
}
