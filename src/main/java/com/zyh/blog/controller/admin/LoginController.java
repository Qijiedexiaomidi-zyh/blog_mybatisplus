package com.zyh.blog.controller.admin;

import com.zyh.blog.entity.User;
import com.zyh.blog.service.UserService;
import com.zyh.blog.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @Author ZYH
 * @Date 2020/8/31 12:37
 * @Blog https://qijiedexiaomidi-zyh.github.io
 * @Faith 干就完了，不多哔哔
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 去登录页面
     */
    @GetMapping
    public String admin(){
        return "admin/login";
    }

    /**
     * 登录操作
     */
    @PostMapping("/login")
    public String login(User user, HttpSession session, Model model){
        //1、获取当前登录用户（主体）
        Subject subject = SecurityUtils.getSubject();
        //2、创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), MD5Utils.code(user.getPassword()));
        try {
            //登录
            subject.login(token);
            //保存当前用户信息
            User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
            session.setAttribute("loginUser",loginUser);
            //验证成功，跳转到后台首页
            return "admin/index";
        } catch (UnknownAccountException e) {
            //登陆失败，用户名不存在
            model.addAttribute("message","用户名不存在");
            return "admin/login";
        }catch (IncorrectCredentialsException e){
            //登陆失败，密码错误
            //重定向的话，model里面的数据显示不出来
            model.addAttribute("message","密码错误");
            return "admin/login";
        }
    }

    /**
     * 注销登录
     */
    @GetMapping("/logout")
    public String logout(HttpSession session,Model model){
        session.removeAttribute("loginUser");
        model.addAttribute("message","您已注销成功");
        return "admin/login";
    }
}
